package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bluromatic.IMAGE_MANIPULATION_WORK_NAME
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.TAG_OUTPUT
import com.example.bluromatic.getImageUri
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    private var imageUri: Uri = context.getImageUri()
    private val workManager = WorkManager.getInstance(context)

    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override fun applyBlur(blurLevel: Int) {
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Companion.from(CleanupWorker::class.java)
            )

        var constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        var blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))

        blurBuilder.setConstraints(constraints)

        continuation = continuation.then(blurBuilder.build())

        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        continuation.enqueue()
    }

    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri) : Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}