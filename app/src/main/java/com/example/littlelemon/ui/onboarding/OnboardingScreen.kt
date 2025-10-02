package com.example.littlelemon.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.components.AppHeader
import com.example.littlelemon.ui.components.PrimaryButton
import com.example.littlelemon.ui.components.TextInput
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun OnboardingScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppHeader()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Let us get to know you",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))

            TextInput(
                label = "First Name",
                value = firstName,
                onValueChange = { firstName = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextInput(
                label = "Last Name",
                value = lastName,
                onValueChange = { lastName = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextInput(
                label = "Email",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(text = "Next") {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    LittleLemonTheme {
        OnboardingScreen()
    }
}
