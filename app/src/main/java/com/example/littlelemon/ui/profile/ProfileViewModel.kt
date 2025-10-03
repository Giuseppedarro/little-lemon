package com.example.littlelemon.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.UserDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = ""
)

sealed interface ProfileNavigationEvent {
    data object NavigateToOnboarding : ProfileNavigationEvent
}

class ProfileViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<ProfileNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val (firstName, lastName, email) = userDataRepository.getUserData()
            _uiState.update { it.copy(firstName = firstName, lastName = lastName, email = email) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataRepository.clearUserData()
            _navigationEvent.emit(ProfileNavigationEvent.NavigateToOnboarding)
        }
    }
}