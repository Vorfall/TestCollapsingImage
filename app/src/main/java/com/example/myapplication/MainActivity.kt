package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CollapsingEffectScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingEffectScreen() {

    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    Text(
        text = "this is header ",
        Modifier
            .background(Color.White)
            .height(56.dp)
            .background(Color.Red)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            Modifier.fillMaxWidth().height(13.dp)
            Image(
                painter = painterResource(id = R.drawable.product_img_template),
                alignment = Alignment.Center,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY*0.9f - scrolledY
                        scaleX = 1/((scrolledY * 0.002f) + 1f)
                        scaleY = 1/((scrolledY * 0.002f) + 1f)
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .padding(top = 60.dp)
                    .fillMaxSize()

            )
        }

        items(1) {
            Text(
                text = "this is body text  ".repeat(200),
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(35.dp),
                lineHeight = 20.sp,
                style = MaterialTheme.typography.bodyMedium

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}