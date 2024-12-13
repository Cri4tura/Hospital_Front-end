package com.example.hospital_front_end.ui.screens.login

import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.components.EmailInput
import com.example.hospital_front_end.ui.components.FingerPrintAuth
import com.example.hospital_front_end.ui.components.PasswordInput
import com.example.hospital_front_end.ui.components.VideoPlayer
import com.example.hospital_front_end.utils.Constants

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginView(
    viewModel: LoginViewModel, onNavigateToHome: () -> Unit, navigateToSignIn: () -> Unit
) {
    val context = LocalContext.current as FragmentActivity
    val isAuthenticated by viewModel.authenticationState.collectAsState()
    var auth by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    viewModel.setupAuth(context)

    // TODO: Comment when DB connection done
    // email = Constants.DEFAULT_USERNAME
    // password = Constants.DEFAULT_PASSWORD

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))


//        GlideImage(
//            model = R.drawable.output,
//            contentDescription = "Glide image ",
//            modifier = Modifier
//                .size(300.dp)
//        )




        VideoPlayer(
            context = context,
            videoResId = R.raw.cruz_medica,
            modifier = Modifier
                .size(300.dp)
                .aspectRatio(1f)
        )



        Spacer(modifier = Modifier.height(16.dp))

        EmailInput(
            email = email,
            onEmailChange = { email = it },
            isError = viewModel.emailError.value
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            onPasswordChange = { password = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = viewModel.passwordError.value,
            context = context,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.log_in_button_text),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        FingerPrintAuth(
            modifier = Modifier
                .clickable {
                    viewModel.setupAuth(context)
                    if (auth) {
                        onNavigateToHome()
                    } else {
                        viewModel.authenticate(context) { isAuthenticated ->
                            auth = isAuthenticated
                        }
                    }
                },
            context = context
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            stringResource(R.string.no_account_yet_text),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { navigateToSignIn() }, modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                stringResource(R.string.sign_in_button_text),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            onNavigateToHome()
        }
    }
}

@Preview
@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(
        viewModel = LoginViewModel(),
        onNavigateToHome = { /* Simulated Login Success */ },
        navigateToSignIn = { /* Simulated Go to Register */ })
}
