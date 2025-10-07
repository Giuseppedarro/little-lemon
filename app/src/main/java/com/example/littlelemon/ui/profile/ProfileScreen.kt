package com.example.littlelemon.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.components.AppHeader
import com.example.littlelemon.ui.components.PrimaryButton
import com.example.littlelemon.ui.components.TextInput
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreen(
    onNavigateToOnboarding: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is ProfileNavigationEvent.NavigateToOnboarding -> {
                    onNavigateToOnboarding()
                }
            }
        }
    }

    ProfileContent(
        uiState = uiState,
        onLogoutClick = { viewModel.logout() }
    )
}


@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    onLogoutClick: () -> Unit
) {
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
                text = "Personal information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(32.dp))

            TextInput(label = "First Name", value = uiState.firstName, onValueChange = {}, enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Last Name", value = uiState.lastName, onValueChange = {}, enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Email", value = uiState.email, onValueChange = {}, enabled = false)

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(text = "Log out", modifier = Modifier.fillMaxWidth(), onClick = onLogoutClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    LittleLemonTheme {
        ProfileContent(
            uiState = ProfileUiState(
                firstName = "Giuseppe",
                lastName = "D'Arrò",
                email = "email@example.com"
            ),
            onLogoutClick = {}
        )
    }
}
