package com.rainbown.netmedicine.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rainbown.netmedicine.ui.theme.onPrimaryLight
import com.rainbown.netmedicine.ui.theme.primaryLight

@Composable
fun barra(
    title: String
) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryLight)
                .height(100.dp)
                .padding(30.dp)
        ) {
                Text(
                    text = title,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    color = onPrimaryLight,
                    modifier = Modifier.padding(10.dp)
                )
            }
    }

