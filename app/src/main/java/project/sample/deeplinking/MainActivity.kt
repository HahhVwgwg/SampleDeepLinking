package project.sample.deeplinking

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import project.sample.deeplinking.ui.theme.Teal200
import project.sample.deeplinking.utils.PushSimulationReceiver.Companion.DATA_BODY
import project.sample.deeplinking.utils.findActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WebView", color = Color.White) },
                backgroundColor = Teal200
            )
        },
        content = { MyContent() }
    )
}

@Composable
fun MyContent() {

    val context = LocalContext.current
    val deeplinkUrl = remember {
        context.findActivity()?.intent?.getStringExtra(DATA_BODY).orEmpty()
    }

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(deeplinkUrl)
        }
    }, update = {
        it.loadUrl(deeplinkUrl)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
}

