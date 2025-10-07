package com.example.littlelemon.ui.menu

import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MenuViewModelTest {
    private lateinit var viewModel: MenuViewModel
    private val getMenuUseCase: GetMenuUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val sampleMenu = listOf(
        MenuItem("Greek Salad", "...", 12.99, "", "starters"),
        MenuItem("Bruschetta", "...", 5.99, "", "starters"),
        MenuItem("Grilled Fish", "...", 19.99, "", "mains"),
        MenuItem("Pasta", "...", 14.99, "", "mains"),
        MenuItem("Lemon Dessert", "...", 8.99, "", "desserts")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init fetches menu and updates state correctly`() = runTest {
        coEvery { getMenuUseCase() } returns sampleMenu
        viewModel = MenuViewModel(getMenuUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertEquals(sampleMenu.size, finalState.menuItems.size)
        assertEquals(listOf("starters", "mains", "desserts"), finalState.categories)
    }

    @Test
    fun `fetchMenu handles exceptions and updates error state`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getMenuUseCase() } throws RuntimeException(errorMessage)

        viewModel = MenuViewModel(getMenuUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.error)
        assertEquals(errorMessage, state.error)
        assertTrue(state.menuItems.isEmpty())
    }

    @Test
    fun `updateSearchQuery filters menu items correctly`() = runTest {
        coEvery { getMenuUseCase() } returns sampleMenu
        viewModel = MenuViewModel(getMenuUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.updateSearchQuery("salad")

        val state = viewModel.uiState.value
        assertEquals("salad", state.searchQuery)
        assertEquals(1, state.menuItems.size)
        assertEquals("Greek Salad", state.menuItems.first().name)
    }

    @Test
    fun `filterByCategory filters menu items correctly`() = runTest {
        coEvery { getMenuUseCase() } returns sampleMenu
        viewModel = MenuViewModel(getMenuUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.filterByCategory("mains")

        val state = viewModel.uiState.value
        assertEquals("mains", state.selectedCategory)
        assertEquals(2, state.menuItems.size)
        assertTrue(state.menuItems.all { it.category == "mains" })
    }

    @Test
    fun `filterByCategory toggles off when same category is selected again`() = runTest {
        coEvery { getMenuUseCase() } returns sampleMenu
        viewModel = MenuViewModel(getMenuUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.filterByCategory("starters")
        var state = viewModel.uiState.value
        assertEquals("starters", state.selectedCategory)
        assertEquals(2, state.menuItems.size)

        viewModel.filterByCategory("starters")
        state = viewModel.uiState.value
        assertNull(state.selectedCategory)
        assertEquals(5, state.menuItems.size)
    }

    @Test
    fun `search and filter work together`() = runTest {
        coEvery { getMenuUseCase() } returns sampleMenu
        viewModel = MenuViewModel(getMenuUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.filterByCategory("starters")
        viewModel.updateSearchQuery("brus")

        val state = viewModel.uiState.value
        assertEquals("starters", state.selectedCategory)
        assertEquals("brus", state.searchQuery)
        assertEquals(1, state.menuItems.size)
        assertEquals("Bruschetta", state.menuItems.first().name)
    }
}
