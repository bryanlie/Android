package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.WorkInfo
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {
    override val outputWorkInfo: Flow<WorkInfo?> = MutableStateFlow(null)

    override fun applyBlur(blurLevel: Int) {
//        TODO("Not yet implemented")
    }

    override fun cancelWork() {
//        TODO("Not yet implemented")
    }

    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri) : Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}