package com.example.panacea.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.panacea.R
import com.example.panacea.ui.components.EmailInput
import com.example.panacea.ui.components.FingerPrintAuth
import com.example.panacea.ui.components.PasswordInput
import com.example.panacea.ui.components.PrimaryButton

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    val context = LocalContext.current as FragmentActivity
    val isAuthenticated by viewModel.authenticationState.collectAsState()
    var auth by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    viewModel.setupAuth(context)

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.panacea),
            contentDescription = "Panacea logo",
            modifier = Modifier.size(232.dp)
        )

        EmailInput(
            email = email,
            onEmailChange = { email = it },
            isError = viewModel.emailError.value
        )

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            onPasswordChange = { password = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = viewModel.passwordError.value
        )

        PrimaryButton(
            text = stringResource(R.string.log_in_button_text),
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            description = "Log In Button",
            enabled = true,
            icon = null
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.no_account_yet_text),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        PrimaryButton(
            onClick = { navigateToSignIn() },
            modifier = Modifier.fillMaxWidth(),
            icon = null,
            text = stringResource(R.string.sign_in_button_text),
            description = "Sign In Button",
            enabled = true
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}