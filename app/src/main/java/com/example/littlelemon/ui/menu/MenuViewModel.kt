package com.example.littlelemon.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MenuUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class MenuViewModel(
    private val getMenuUseCase: GetMenuUseCase
) : ViewModel() {

    private var masterMenuList: List<MenuItem> = emptyList()

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        fetchMenu()
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        processMenuItems()
    }

    fun filterByCategory(category: String) {
        val newCategory = if (_uiState.value.selectedCategory == category) null else category
        _uiState.update { it.copy(selectedCategory = newCategory) }
        processMenuItems()
    }

    private fun processMenuItems() {
        val currentState = _uiState.value
        val processedList = masterMenuList
            .sortedBy { it.name }
            .filter { 
                it.name.contains(currentState.searchQuery, ignoreCase = true)
            }
            .filter { 
                currentState.selectedCategory?.equals(it.category, ignoreCase = true) ?: true
            }

        _uiState.update { it.copy(menuItems = processedList) }
    }

    private fun fetchMenu() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                masterMenuList = getMenuUseCase()
                val categories = masterMenuList.map { it.category }.distinct()
                _uiState.update { it.copy(categories = categories, isLoading = false) }
                processMenuItems()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage, isLoading = false) }
            }
        }
    }
}
