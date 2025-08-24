package com.example.analytics.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.core.presentation.designsystem.LondonCapTaskTheme
import com.google.android.play.core.splitcompat.SplitCompat

class AnalyticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitCompat.installActivity(this)
        enableEdgeToEdge()
        setContent {
            LondonCapTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnalyticsScreen(Modifier.padding(innerPadding), onBackClicked = {
                        finish()
                    })
                }
            }
        }
    }
}