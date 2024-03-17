package com.bayutb123.tukerin

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.bayutb123.tukerin.ui.App
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var kbClosed: () -> Unit = {}
    private var isKbClosed: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val focusManager = LocalFocusManager.current
            kbClosed = {
                focusManager.clearFocus()
            }
            TukerInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
        setupKeyboardDetection(findViewById(android.R.id.content))
    }

    private fun setupKeyboardDetection(contentView: View) {
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = contentView.rootView.height
            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                isKbClosed = false
                // kb opened
            } else if(!isKbClosed) {
                isKbClosed = true
                kbClosed()
            }
        }
    }
}

