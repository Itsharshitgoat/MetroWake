package com.metrowake.app.domain.usecases

import com.metrowake.app.data.local.StationEntity
import com.metrowake.app.data.repository.MetroRepository
import com.metrowake.app.utils.FuzzyMatcher
import javax.inject.Inject

class MatchStationFromSpeechUseCase @Inject constructor(
    private val repository: MetroRepository
) {
    /**
     * Tries to find a station in the system that matches the spoken text snippet.
     */
    suspend operator fun invoke(speechTranscript: String): MatchResult? {
        val transcriptNormalized = FuzzyMatcher.normalizeForPhonetic(speechTranscript)
        val allStations = repository.getAllStations()

        var bestMatch: StationEntity? = null
        var highestScore = 0.0

        for (station in allStations) {
            val stationNormalized = FuzzyMatcher.normalizeForPhonetic(station.name)

            // Check if the transcript contains the station name (e.g. "Next station is Rajiv Chowk")
            if (transcriptNormalized.contains(stationNormalized)) {
                return MatchResult(station, 1.0)
            }

            // Alternatively, split the transcript and find the best matching chunk
            val words = transcriptNormalized.split(" ")
            val stationWordsLength = stationNormalized.split(" ").size

            // Sliding window over transcript to find best fuzzy match
            if (words.size >= stationWordsLength) {
                for (i in 0..words.size - stationWordsLength) {
                    val window = words.subList(i, i + stationWordsLength).joinToString(" ")
                    val score = FuzzyMatcher.similarityScore(window, stationNormalized)
                    if (score > highestScore) {
                        highestScore = score
                        bestMatch = station
                    }
                }
            } else {
                // If the transcript is shorter than the station name, just fuzzy match the whole thing
                val score = FuzzyMatcher.similarityScore(transcriptNormalized, stationNormalized)
                if (score > highestScore) {
                    highestScore = score
                    bestMatch = station
                }
            }
        }

        // Return the best match if it exceeds our confidence threshold
        val CONFIDENCE_THRESHOLD = 0.75
        return if (highestScore >= CONFIDENCE_THRESHOLD && bestMatch != null) {
            MatchResult(bestMatch, highestScore)
        } else {
            null
        }
    }

    data class MatchResult(
        val station: StationEntity,
        val confidenceScore: Double
    )
}
