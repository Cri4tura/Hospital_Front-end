package com.example.hospital_front_end.ui.screens.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.hospital_front_end.R
import com.example.hospital_front_end.utils.Constants

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
    email = Constants.DEFAULT_USERNAME
    password = Constants.DEFAULT_PASSWORD

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.homepage_title_text),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 45.dp, top = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "logo image",
            modifier = Modifier
                .width(250.dp)
                .height(250.dp),
            contentScale = ContentScale.FillBounds
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
            isError = viewModel.passwordError.value
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

        Column(
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.authenticate_with_biometrics_text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
            Image(
                painter = painterResource(R.drawable.huella),
                contentDescription = "fingerprint image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
            )
        }


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

@Composable
fun PasswordInput(
    password: String,
    passwordVisible: Boolean,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    isError: String?
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.password_placeholder_text)) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(15.dp),
        isError = isError?.isNotEmpty() ?: false,
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                val icon =
                    if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                val description = if (passwordVisible) "Hide password" else "Show password"
                Image(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp),
                    painter = painterResource(id = icon),
                    contentDescription = description
                )
            }
        }
    )
    if (isError?.isNotEmpty() == true) {
        Text(
            text = isError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun EmailInput(
    email: String,
    onEmailChange: (String) -> Unit,
    isError: String?
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(stringResource(R.string.email_placeholder_text)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        isError = isError?.isNotEmpty() ?: false,
    )
    if (isError?.isNotEmpty() == true) {
        Text(
            text = isError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginViewPreview() {
    LoginView(viewModel = LoginViewModel(),
        onNavigateToHome = { /* Simulated Login Success */ },
        navigateToSignIn = { /* Simulated Go to Register */ })
}
