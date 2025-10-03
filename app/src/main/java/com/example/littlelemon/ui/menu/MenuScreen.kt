package com.example.littlelemon.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonGreen
import com.example.littlelemon.ui.theme.LittleLemonLightGray
import com.example.littlelemon.ui.theme.LittleLemonTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    onNavigateToProfile: () -> Unit,
    viewModel: MenuViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = if (searchQuery.isNotBlank()) {
        uiState.menuItems.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    } else {
        uiState.menuItems
    }

    MenuContent(
        uiState = uiState.copy(menuItems = filteredItems),
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onCategoryClick = { /* TODO: viewModel.filter(it) */ },
        onProfileClick = onNavigateToProfile
    )
}

@Composable
fun MenuContent(
    uiState: MenuUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(onProfileClick = onProfileClick)
            HeroSection(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${uiState.error}")
                }
            } else {
                MenuBreakdown(
                    menuItems = uiState.menuItems,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

@Composable
fun HomeHeader(onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(end = 16.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background/*id = R.drawable.ic_profile*/),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun HeroSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonGreen)
            .padding(16.dp)
    ) {
        Text(text = "Little Lemon", style = MaterialTheme.typography.displaySmall, color = Color.White)
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Chicago", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = LittleLemonLightGray)
        )
    }
}

@Composable
fun MenuBreakdown(menuItems: List<MenuItem>, onCategoryClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CategoryButton(text = "Starters") { onCategoryClick("Starters") }
            CategoryButton(text = "Mains") { onCategoryClick("Mains") }
            CategoryButton(text = "Desserts") { onCategoryClick("Desserts") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(menuItems) {
                MenuItemCard(menuItem = it)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = LittleLemonLightGray)
    ) {
        Text(text = text, color = Color.Black)
    }
}

@Preview(showBackground = true, name = "Menu Content Loaded")
@Composable
fun MenuContentPreview() {
    LittleLemonTheme {
        val sampleMenuItems = listOf(
            MenuItem("Greek Salad", "The famous Greek salad of crispy lettuce, peppers, olives and our Chicago style feta cheese, garnished with crunchy garlic and rosemary croutons.", 12.99, R.drawable.ic_launcher_background),
            MenuItem("Bruschetta", "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.", 5.99, R.drawable.ic_launcher_background)
        )
        val sampleUiState = MenuUiState(menuItems = sampleMenuItems)

        MenuContent(
            uiState = sampleUiState,
            searchQuery = "",
            onSearchQueryChange = {},
            onCategoryClick = {},
            onProfileClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Menu Content Loading")
@Composable
fun MenuContentLoadingPreview() {
    LittleLemonTheme {
        MenuContent(
            uiState = MenuUiState(isLoading = true),
            searchQuery = "",
            onSearchQueryChange = {},
            onCategoryClick = {},
            onProfileClick = {}
        )
    }
}
