package com.example.littlelemon.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MenuUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class MenuViewModel(
    private val getMenuUseCase: GetMenuUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        fetchMenu()
    }

    private fun fetchMenu() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val menuItems = getMenuUseCase()
                _uiState.value = _uiState.value.copy(menuItems = menuItems, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.localizedMessage, isLoading = false)
            }
        }
    }
}
