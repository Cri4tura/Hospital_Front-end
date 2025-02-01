package com.example.panacea.ui.screens.login

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    vm: LoginViewModel,
    onLogIn: () -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalActivity.current as FragmentActivity

    var auth by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("test@gmail.com") }
    var password by remember { mutableStateOf("1234") }
    var passwordVisible by remember { mutableStateOf(false) }

    // setup biometrics
    vm.setupAuth(context)

    LaunchedEffect(vm.authenticationState.value) {
        if (vm.authenticationState.value) {
            onLogIn()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(end = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            if (vm.isLoading.value) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(
                painter = painterResource(R.drawable.panacea),
                contentDescription = "Panacea logo",
                modifier = Modifier
                    .size(232.dp)
                    .padding(top = 128.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            EmailInput(
                email = email,
                onEmailChange = { email = it },
                isError = vm.emailError.value,
                label = "Email",
                placeholder = null
            )

            PasswordInput(
                password = password,
                passwordVisible = passwordVisible,
                onPasswordChange = { password = it },
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                isError = vm.passwordError.value
            )

            PrimaryButton(
                text = stringResource(R.string.log_in_button_text),
                onClick = {
                    vm.login(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                description = "Log In Button",
                enabled = true,
                icon = null
            )

            Spacer(modifier = Modifier.weight(1f))

            FingerPrintAuth(
                modifier = Modifier.clickable {
                    vm.setupAuth(context)
                    if (auth) {
                        onLogIn()
                    } else {
                        vm.authenticate(context) {
                            auth = it
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
                onClick = { onSignIn() },
                modifier = Modifier.fillMaxWidth(),
                icon = null,
                text = stringResource(R.string.sign_in_button_text),
                description = "Sign In Button",
                enabled = true
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}