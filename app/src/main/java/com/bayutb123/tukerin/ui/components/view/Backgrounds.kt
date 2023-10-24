package com.bayutb123.tukerin.ui.components.view

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

class Backgrounds {
    companion object {
        val largeRadialGradient = object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val biggerDimension = maxOf(size.height, size.width)
                return RadialGradientShader(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)),
                    center = size.center,
                    radius = biggerDimension / 1f,
                    colorStops = listOf(0f, 0.95f)
                )
            }
        }

    }
}