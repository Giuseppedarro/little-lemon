package com.example.littlelemon.ui.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.components.AppHeader
import com.example.littlelemon.ui.components.PrimaryButton
import com.example.littlelemon.ui.components.TextInput
import com.example.littlelemon.ui.theme.LittleLemonGreen
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnboardingScreen(
    onNavigateToMenu: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    
    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateToHome -> {
                    Toast.makeText(context, uiState.registrationMessage, Toast.LENGTH_SHORT).show()
                    onNavigateToMenu()
                }
            }
        }
    }

    OnboardingContent(
        uiState = uiState,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::registerUser
    )
}


@Composable
fun OnboardingContent(
    uiState: OnboardingUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    val (lastNameFocusRequester, emailFocusRequester) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(modifier = Modifier.height(40.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = LittleLemonGreen
            ) {
                Text(
                    text = "Let's get to know you",
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Personal information",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(24.dp))

                TextInput(
                    label = "First Name",
                    value = uiState.firstName,
                    onValueChange = onFirstNameChange,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { lastNameFocusRequester.requestFocus() })
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextInput(
                    label = "Last Name",
                    value = uiState.lastName,
                    onValueChange = onLastNameChange,
                    modifier = Modifier.focusRequester(lastNameFocusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextInput(
                    label = "Email",
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.focusRequester(emailFocusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                uiState.registrationMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        color = if (it.contains("successful")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Register",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onRegisterClick
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingContentPreview() {
    LittleLemonTheme {
        OnboardingContent(
            uiState = OnboardingUiState(
                firstName = "Tilly",
                lastName = "Doe",
                email = "tilly.doe@example.com"
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onRegisterClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingContentErrorPreview() {
    LittleLemonTheme {
        OnboardingContent(
            uiState = OnboardingUiState(
                registrationMessage = "Registration unsuccessful. Please enter all data."
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onRegisterClick = {}
        )
    }
}
