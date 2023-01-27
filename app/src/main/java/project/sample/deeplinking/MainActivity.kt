package project.sample.deeplinking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import project.sample.deeplinking.data.model.Response
import project.sample.deeplinking.ui.theme.Teal200


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent()
        }

        // Get the deep link URI from the Intent
        // Get the deep link URI from the Intent
        val deepLinkUri = intent.data

        if (deepLinkUri != null) {
            println(deepLinkUri)
            // Extract the query parameter values
            val payment = deepLinkUri.getQueryParameter("payload")
            // Do something with the extracted parameters
            // Base64 encoded string
            val base64EncodedString = payment.toString()
            println(deepLinkUri)
            // Decode the base64 encoded string
            val decodedBytes = Base64.decode(base64EncodedString, Base64.DEFAULT)

            // Convert the decoded bytes to a string
            val decodedString = String(decodedBytes)
            val response = Gson().fromJson(decodedString, Response::class.java)

            Toast.makeText(applicationContext, response.payload?.message, Toast.LENGTH_LONG).show()
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
    val mContext = LocalContext.current
    OutlinedButton(onClick = {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(mContext, R.color.white))
            .setShowTitle(true)
            .build()
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabsIntent.launchUrl(
            mContext, Uri.parse(
                "https://test-pay.dnapayments.com/components/google-pay/component.html?token=CjtXln4aeRlumKDGEJOI180PnZgj6GsuHSKY51vU0MK*FdGEFsFc1hL-M1*j-0SN&paymentData=eyJhdXRoIjp7ImFjY2Vzc190b2tlbiI6IkNqdFhsbjRhZVJsdW1LREdFSk9JMTgwUG5aZ2o2R3N1SFNLWTUxdlUwTUsqRmRHRUZzRmMxaEwtTTEqai0wU04iLCJleHBpcmVzX2luIjo3MjAwLCJyZWZyZXNoX3Rva2VuIjoiIiwic2NvcGUiOiJwYXltZW50IGludGVncmF0aW9uX2hvc3RlZCBpbnRlZ3JhdGlvbl9lbWJlZGRlZCBpbnRlZ3JhdGlvbl9zZWFtbGVzcyIsInRva2VuX3R5cGUiOiJCZWFyZXIifSwiY3VycmVuY3kiOiJHQlAiLCJkZXNjcmlwdGlvbiI6IkNhciBTZXJ2aWNlIiwicGF5bWVudFNldHRpbmdzIjp7InRlcm1pbmFsSWQiOiI4OTExYTE0Zi02MWEzLTQ0NDktYTFjMS03YTMxNGVlNTc3NGMiLCJyZXR1cm5VcmwiOiJodHRwczovL3Rlc3QiLCJmYWlsdXJlUmV0dXJuVXJsIjoiaHR0cHM6Ly90ZXN0IiwiY2FsbGJhY2tVcmwiOiJodHRwczovL3BheS5kbmFwYXltZW50cy5jb20vY2hlY2tvdXQiLCJmYWlsdXJlQ2FsbGJhY2tVcmwiOiJodHRwczovL3Rlc3RtZXJjaGFudC9vcmRlci8xMTIzL2ZhaWwifSwiY3VzdG9tZXJEZXRhaWxzIjp7ImFjY291bnREZXRhaWxzIjp7ImFjY291bnRJZCI6InV1aWQwMDAwMDEifSwiYmlsbGluZ0FkZHJlc3MiOnsiZmlyc3ROYW1lIjoiSm9obiIsImxhc3ROYW1lIjoiRG9lIiwiYWRkcmVzc0xpbmUxIjoiRnVsaGFtIFJkIiwicG9zdGFsQ29kZSI6IlNXNiAxSFMiLCJjaXR5IjoiTG9uZG9uIiwiY291bnRyeSI6IkdCIn0sImRlbGl2ZXJ5RGV0YWlscyI6eyJkZWxpdmVyeUFkZHJlc3MiOnsiZmlyc3ROYW1lIjoiSm9obiIsImxhc3ROYW1lIjoiRG9lIiwiYWRkcmVzc0xpbmUxIjoiRnVsaGFtIFJkIiwiYWRkcmVzc0xpbmUyIjoiRnVsaGFtIiwicG9zdGFsQ29kZSI6IlNXNiAxSFMiLCJjaXR5IjoiTG9uZG9uIiwicGhvbmUiOiIwNDc1NjYyODM0IiwiY291bnRyeSI6IkdCIn19LCJlbWFpbCI6InRlc3RAZG5hcGF5bWVudHMuY29tIiwibW9iaWxlUGhvbmUiOiI0NDEyMzQ1Njc4OTAifSwib3JkZXJMaW5lcyI6W3sibmFtZSI6IlJ1bm5pbmcgc2hvZSIsInF1YW50aXR5IjoxLCJ1bml0UHJpY2UiOjI0LCJ0YXhSYXRlIjoyMCwidG90YWxBbW91bnQiOjI0LCJ0b3RhbFRheEFtb3VudCI6NCwiaW1hZ2VVcmwiOiJodHRwczovL3d3dy5leGFtcGxlb2JqZWN0cy5jb20vbG9nby5wbmciLCJwcm9kdWN0VXJsIjoiaHR0cHM6Ly8uLi4vQUQ2NjU0NDEyLmh0bWwifV0sImRlbGl2ZXJ5VHlwZSI6InNlcnZpY2UiLCJpbnZvaWNlSWQiOiIxNjc0NzM1ODU4MjMzIiwiYW1vdW50IjoyNH0="
            )
        )
    }) {
        Text(text = "Google Pay Button")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
}

