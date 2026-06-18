package com.metrowake.app.presentation.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.metrowake.app.services.tracking.JourneyState

@Composable
fun JourneyScreen(
    navController: NavController,
    startId: String,
    destId: String,
    viewModel: JourneyViewModel = hiltViewModel()
) {
    val journeyState by viewModel.journeyState.collectAsState()
    val currentStationName by viewModel.currentStationName.collectAsState()

    LaunchedEffect(startId, destId) {
        viewModel.startJourney(startId, destId)
    }

    val (remainingStations, eta) = when (val state = journeyState) {
        is JourneyState.Active -> state.remainingStations to state.etaMinutes
        else -> 0 to 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ACTIVE JOURNEY TRACKER",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            letterSpacing = androidx.compose.ui.unit.TextUnit(2f, androidx.compose.ui.unit.TextUnitType.Sp)
        )
        Text(
            text = "Never miss your station",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )

        // Status Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = com.metrowake.app.presentation.components.PerforatedTicketShape(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Approaching Tag
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "APPROACHING",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentStationName.uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Progress Tracker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)))
                    Box(modifier = Modifier.weight(1f).height(2.dp).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)))
                    Box(modifier = Modifier.size(16.dp).clip(androidx.compose.foundation.shape.CircleShape).background(MaterialTheme.colorScheme.primary))
                    Box(modifier = Modifier.weight(1f).height(2.dp).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)))
                    Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)))
                }

                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Stations Card
                    Surface(
                        modifier = Modifier.weight(1f).aspectRatio(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = remainingStations.toString(), style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.primary)
                            Text(text = "STATIONS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }

                    // ETA Card
                    Surface(
                        modifier = Modifier.weight(1f).aspectRatio(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "${eta}M", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.primary)
                            Text(text = "ETA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sensor Status Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(MaterialTheme.colorScheme.tertiary))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MONITORING", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.cancelJourney()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(
                text = "✕ CANCEL JOURNEY",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
