package com.aura.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.aura.musicapp.ui.components.MusicGenerationScreen
import com.aura.musicapp.ui.theme.AuraTheme
import com.aura.musicapp.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {
    
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            AuraTheme {
                MusicGenerationScreen(viewModel = viewModel)
            }
        }
    }
}
