package com.example.littlelemon.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.components.AppHeader
import com.example.littlelemon.ui.components.PrimaryButton
import com.example.littlelemon.ui.components.TextInput
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ProfileScreen() {
    var firstName by remember { mutableStateOf("Tom") }
    var lastName by remember { mutableStateOf("Cruise") }
    var email by remember { mutableStateOf("tomcruise@email.com") }
    var phone by remember { mutableStateOf("1234567891") }

    var orderStatus by remember { mutableStateOf(true) }
    var passwordChanges by remember { mutableStateOf(true) }
    var specialOffers by remember { mutableStateOf(true) }
    var newsletter by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(modifier = Modifier.height(24.dp))

            Text("Personal information", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { }) {
                    Text("Change")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Gray)
                ) {
                    Text("Remove")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextInput(label = "First Name", value = firstName, onValueChange = { firstName = it }, enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Last Name", value = lastName, onValueChange = { lastName = it }, enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Email", value = email, onValueChange = { email = it }, enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Phone number (10 digit)", value = phone, onValueChange = { phone = it })
            Spacer(modifier = Modifier.height(24.dp))

            Text("Email notifications", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            CheckboxWithLabel(label = "Order statuses", checked = orderStatus, onCheckedChange = { orderStatus = it })
            CheckboxWithLabel(label = "Password changes", checked = passwordChanges, onCheckedChange = { passwordChanges = it })
            CheckboxWithLabel(label = "Special offers", checked = specialOffers, onCheckedChange = { specialOffers = it })
            CheckboxWithLabel(label = "Newsletter", checked = newsletter, onCheckedChange = { newsletter = it })

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(text = "Log out") {

            }
        }
    }
}

@Composable
fun CheckboxWithLabel(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreen()
    }
}
