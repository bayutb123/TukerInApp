package com.bayutb123.tukerin.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf(selectedItem) }
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                onTextChanged = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                placeholder = null,
                readOnly = true,
                readOnlyText = selectedText
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun CustomDropDownPreview() {
    TukerInTheme {
        CustomDropDown(
            items = listOf("Item 1", "Item 2", "Item 3"),
            selectedItem = "Item 1",
            onItemSelected = {}
        )
    }
}