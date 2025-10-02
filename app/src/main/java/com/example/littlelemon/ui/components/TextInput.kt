package com.example.littlelemon.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonLightGray
import com.example.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = LittleLemonLightGray,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    LittleLemonTheme {
        TextInput(label = "First Name", value = "Tom", onValueChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputPreviewDisabled() {
    LittleLemonTheme {
        TextInput(label = "First Name", value = "Tom", onValueChange = {}, enabled = false)
    }
}
