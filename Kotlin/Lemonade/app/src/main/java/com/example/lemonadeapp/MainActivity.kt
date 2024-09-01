package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeAppTheme {
                LemonApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonApp() {
    var currentStep by remember { mutableStateOf(1)}

    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->

        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStep) {
                1 ->
                    LemonTextAndImage(
                        textLabelResId = R.string.lemon_select,
                        drawableResId = R.drawable.lemon_tree,
                        contentDescrResId = R.string.lemon_tree_content_description,
                        onImageClick = {
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        }
                    )
                2 ->
                    LemonTextAndImage(
                        textLabelResId = R.string.lemon_squeeze,
                        drawableResId = R.drawable.lemon_squeeze,
                        contentDescrResId = R.string.lemon_content_description,
                        onImageClick = {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        }
                    )
                3 ->
                    LemonTextAndImage(
                        textLabelResId = R.string.lemon_drink,
                        drawableResId = R.drawable.lemon_drink,
                        contentDescrResId = R.string.lemonade_content_description,
                        onImageClick = { currentStep = 4 }
                    )
                4 -> LemonTextAndImage(
                    textLabelResId = R.string.lemon_empty_glass,
                    drawableResId = R.drawable.lemon_restart,
                    contentDescrResId = R.string.empty_glass_content_description,
                    onImageClick = { currentStep = 1 }
                )
            }
        }
    }


}

@Composable
fun LemonTextAndImage(
    textLabelResId: Int,
    drawableResId: Int,
    contentDescrResId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box( modifier = modifier ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Image(
                    painter = painterResource(drawableResId),
                    contentDescription = stringResource(contentDescrResId),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.button_image_width))
                        .height(dimensionResource(id = R.dimen.button_image_height))
                        .padding(dimensionResource(id = R.dimen.button_interior_padding))

                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
            Text(
                text = stringResource(textLabelResId),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeAppTheme {
        LemonApp()
    }
}