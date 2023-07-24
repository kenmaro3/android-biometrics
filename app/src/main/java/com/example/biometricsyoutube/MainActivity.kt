package com.example.biometricsyoutube

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.biometricsyoutube.ui.theme.BiometricsYoutubeTheme
import kotlinx.coroutines.delay

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricsYoutubeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(activity)


    var promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("title")
        .setSubtitle("subtitle")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor,
        object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    context,
                    "error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(context, "succeeded!", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    context, "failed", Toast.LENGTH_LONG
                ).show()
            }
        }
    )

    LaunchedEffect(Unit){
        delay(500)
        biometricPrompt.authenticate(promptInfo)
    }

    Text(text = "hello, $name!")


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiometricsYoutubeTheme {
        Greeting("Android")
    }
}