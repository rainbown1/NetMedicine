package com.rainbown.netmedicine.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rainbown.netmedicine.ui.theme.onPrimaryLight
import com.rainbown.netmedicine.ui.theme.primaryLight

@Composable
fun barra(
    title: String
) {

    ConstraintLayout {
        val barra = createRef()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryLight)
                .height(100.dp)
                .padding(30.dp)
                .constrainAs(barra){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
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

}
