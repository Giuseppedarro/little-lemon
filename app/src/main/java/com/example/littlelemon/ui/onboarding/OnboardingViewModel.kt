package com.example.littlelemon.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.UserDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val registrationMessage: String? = null
)

sealed interface NavigationEvent {
    data object NavigateToHome : NavigationEvent
}

class OnboardingViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onFirstNameChange(firstName: String) {
        _uiState.update { it.copy(firstName = firstName, registrationMessage = null) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { it.copy(lastName = lastName, registrationMessage = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, registrationMessage = null) }
    }

    fun registerUser() {
        val state = _uiState.value
        if (state.firstName.isBlank() || state.lastName.isBlank() || state.email.isBlank()) {
            _uiState.update { it.copy(registrationMessage = "Registration unsuccessful. Please enter all data.") }
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.update { it.copy(registrationMessage = "Registration unsuccessful. Please enter a valid email.") }
            return
        }


        viewModelScope.launch {
            userDataRepository.saveUser(state.firstName, state.lastName, state.email)
            _uiState.update { it.copy(registrationMessage = "Registration successful!") }
            _navigationEvent.emit(NavigationEvent.NavigateToHome)
        }
    }
}