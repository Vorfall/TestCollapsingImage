package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
                    nestedScroll()
                }
            }
        }
    }
}

@Composable
fun nestedScroll() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {

//        var textText by remember { mutableStateOf("") }

        var maxHeight = 328.dp
        val minHeight = 0f
        var heightImage by remember { mutableStateOf(maxHeight) }

        val d = LocalDensity.current.density
        val toolbarHeightPx = with(LocalDensity.current) {
            maxHeight.roundToPx().toFloat()
        }
        val toolbarMinHeightPx = with(LocalDensity.current) {
            minHeight.dp.roundToPx().toFloat()
        }
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value =
                        newOffset.coerceIn(toolbarMinHeightPx - toolbarHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }


        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = heightImage),
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
            )
            {
                item() {
                    repeat(120) {
                        Text(
                            "textText", modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxHeight+ toolbarOffsetHeightPx.value.dp)
            ) {
                heightImage = ((toolbarHeightPx + toolbarOffsetHeightPx.value) / d).dp

                Log.e("heightImage", "toolbarHeightPx: " + toolbarHeightPx )
                Log.d("heightImage", "toolbarOffsetHeightPx.value: "+ toolbarOffsetHeightPx.value)
                Log.i("heightImage", "heightImage: "+ heightImage.value.toString())
              //  textText = heightImage.value.toString()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.product_img_template),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingEffectScreen() {
    val scrollState = rememberScrollState()
    var scrolledY = 0f
    var previousOffset = 0

    Column() {
        Text(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()

                .background(Color.Red),

            text = scrollState.value.toString(),
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            Modifier.fillMaxWidth()
            Image(
                painter = painterResource(id = R.drawable.product_img_template),
                alignment = Alignment.Center,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += scrollState.value - previousOffset
                        translationY = scrolledY * 0.5f
                        scaleX = 1 / ((scrolledY * 0.002f) + 1f)
                        scaleY = 1 / ((scrolledY * 0.002f) + 1f)
                        previousOffset = scrollState.value
                    }

                    .fillMaxWidth()
                    .clip(CircleShape)
            )
            Text(
                text = "this is body text  ".repeat(20),
                Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(),
                lineHeight = 20.sp,
                style = MaterialTheme.typography.bodyLarge

            )
        }


    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {

    }
}