package com.aura.musicapp.model

data class Track(
    val id: String,
    val title: String,
    val artist: String = "Aura AI",
    val duration: String,
    val imageUrl: String,
    val prompt: String? = null,
    val genre: String? = null
)
