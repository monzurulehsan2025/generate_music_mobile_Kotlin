package com.aura.musicapp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aura.musicapp.model.Track
import com.aura.musicapp.ui.theme.*
import com.aura.musicapp.viewmodel.GenerationState
import com.aura.musicapp.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicGenerationScreen(viewModel: MusicViewModel) {
    var prompt by remember { mutableStateOf("") }
    val generationState by viewModel.generationState.collectAsState()
    val recentTracks by viewModel.recentTracks.collectAsState()

    Scaffold(
        containerColor = AuraBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "AURA AI", 
                        style = Typography.headlineMedium.copy(
                            letterSpacing = 2.sp,
                            brush = Brush.horizontalGradient(listOf(AuraPurple, AuraMagenta))
                        )
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            // AI Prompt Input Card
            GenerationInputSection(
                prompt = prompt,
                onPromptChange = { prompt = it },
                onGenerateRequested = { viewModel.generateMusic(prompt) },
                isLoading = generationState is GenerationState.Loading
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Recent Tracks Section
            Text(
                "RECENT CREATIONS",
                style = Typography.labelMedium.copy(color = AuraGray, letterSpacing = 1.sp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(recentTracks) { track ->
                    TrackCard(track)
                }
            }
        }
    }
}

@Composable
fun GenerationInputSection(
    prompt: String,
    onPromptChange: (String) -> Unit,
    onGenerateRequested: () -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(AuraDeepGray)
            .border(
                1.dp, 
                Brush.linearGradient(listOf(AuraPurple.copy(alpha = 0.5f), AuraMagenta.copy(alpha = 0.5f))),
                RoundedCornerShape(24.dp)
            )
            .padding(24.dp)
    ) {
        Text(
            "What should the AI create?",
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = prompt,
            onValueChange = onPromptChange,
            placeholder = { Text("A lo-fi beat for late night coding...", color = AuraGray) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AuraBlack.copy(alpha = 0.5f),
                unfocusedContainerColor = AuraBlack.copy(alpha = 0.3f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AuraPurple
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onGenerateRequested,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = !isLoading && prompt.isNotBlank(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AuraPurple,
                disabledContainerColor = AuraGray.copy(alpha = 0.2f)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Generate Magic", style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
fun TrackCard(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AuraDeepGray.copy(alpha = 0.5f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder for Album Art
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.sweepGradient(listOf(AuraCyan, AuraPurple, AuraMagenta)))
        ) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center).size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1.dp)) {
            Text(track.title, style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold), maxLines = 1)
            Text(track.artist, style = Typography.labelMedium.copy(color = AuraGray))
        }

        IconButton(onClick = { /* Share or More */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = null, tint = AuraGray)
        }
    }
}
