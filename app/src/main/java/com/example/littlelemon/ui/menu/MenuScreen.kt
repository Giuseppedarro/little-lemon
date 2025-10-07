package com.example.littlelemon.ui.menu

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonDarkGray
import com.example.littlelemon.ui.theme.LittleLemonGreen
import com.example.littlelemon.ui.theme.LittleLemonLightGray
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.LittleLemonYellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    onNavigateToProfile: () -> Unit,
    viewModel: MenuViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MenuContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onCategoryClick = viewModel::filterByCategory,
        onProfileClick = onNavigateToProfile
    )
}

@Composable
fun MenuContent(
    uiState: MenuUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(onProfileClick = onProfileClick)
            HeroSection(
                searchQuery = uiState.searchQuery,
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
                    categories = uiState.categories,
                    selectedCategory = uiState.selectedCategory,
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

 
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.weight(1f).padding(start = 16.dp).height(40.dp)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(end = 16.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(40.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
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
        Text(
            text = "Little Lemon",
            style = MaterialTheme.typography.displaySmall,
            color = LittleLemonYellow,
            fontFamily = FontFamily.Serif
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Chicago", style = MaterialTheme.typography.titleLarge, color = LittleLemonLightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LittleLemonLightGray
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter search phrase") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = LittleLemonLightGray)
        )
    }
}

@Composable
fun MenuBreakdown(
    menuItems: List<MenuItem>,
    categories: List<String>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                CategoryButton(
                    text = category,
                    isSelected = category == selectedCategory,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = LittleLemonDarkGray)
        LazyColumn {
            items(menuItems) {
                MenuItemCard(menuItem = it)
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LittleLemonGreen else LittleLemonLightGray,
            contentColor = if (isSelected) LittleLemonLightGray else LittleLemonDarkGray
        )
    ) {
        Text(text = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
    }
}

@Preview(showBackground = true, name = "Menu Content Loaded")
@Composable
fun MenuContentPreview() {
    LittleLemonTheme {
        val sampleMenuItems = listOf(
            MenuItem(name = "Greek Salad", description = "The famous Greek salad...", price = 12.99, image = "", category = "starters"),
            MenuItem(name = "Bruschetta", description = "Our Bruschetta is made from grilled bread...", price = 5.99, image = "", category = "starters")
        )
        val sampleUiState = MenuUiState(
            menuItems = sampleMenuItems,
            categories = listOf("starters", "mains", "desserts"),
            selectedCategory = "starters"
        )

        MenuContent(
            uiState = sampleUiState,
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
            onSearchQueryChange = {},
            onCategoryClick = {},
            onProfileClick = {}
        )
    }
}
