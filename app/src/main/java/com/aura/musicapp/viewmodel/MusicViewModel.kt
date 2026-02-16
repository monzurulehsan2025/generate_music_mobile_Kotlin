package com.aura.musicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.musicapp.model.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class GenerationState {
    object Idle : GenerationState()
    object Loading : GenerationState()
    data class Success(val track: Track) : GenerationState()
    data class Error(val message: String) : GenerationState()
}

class MusicViewModel : ViewModel() {

    private val _generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    private val _recentTracks = MutableStateFlow<List<Track>>(emptyList())
    val recentTracks: StateFlow<List<Track>> = _recentTracks.asStateFlow()

    val discoveryStyles = listOf(
        "Lo-fi Chill" to "A relaxing, low-fidelity beat with dusty vinyl textures.",
        "Synthwave" to "Retro-futuristic 80s aesthetics with lush neon synths.",
        "Orchestral" to "Epic cinematic strings and powerful brass arrangements.",
        "Cyberpunk" to "Aggressive industrial bass and glitchy digital sounds.",
        "Acoustic" to "Clean guitar melodies and soft rhythmic percussion."
    )

    fun generateMusic(prompt: String) {
        viewModelScope.launch {
            _generationState.value = GenerationState.Loading
            
            // Simulate AI generation delay
            delay(3000)

            val newTrack = Track(
                id = UUID.randomUUID().toString(),
                title = "Aura Vibe: ${prompt.take(15)}...",
                duration = "3:45",
                imageUrl = "https://picsum.photos/seed/${UUID.randomUUID()}/400/400",
                prompt = prompt,
                genre = "AI Experimental"
            )

            _generationState.value = GenerationState.Success(newTrack)
            _recentTracks.value = listOf(newTrack) + _recentTracks.value
        }
    }
}
