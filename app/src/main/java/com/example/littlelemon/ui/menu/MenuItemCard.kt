package com.example.littlelemon.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(menuItem: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = menuItem.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = menuItem.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${menuItem.price}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        GlideImage(
            model = menuItem.image,
            contentDescription = "Image of ${menuItem.name}",
            modifier = Modifier.size(90.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
    HorizontalDivider(color = Color.LightGray)
}

@Preview(showBackground = true)
@Composable
fun MenuItemCardPreview() {
    LittleLemonTheme {
        val sampleItem = MenuItem(
            name = "Greek Salad",
            description = "The famous Greek salad of crisp lettuce, peppers, olives and our Chicago...",
            price = 12.99,
            image = "",
            category = "starters"
        )
        MenuItemCard(menuItem = sampleItem)
    }
}
