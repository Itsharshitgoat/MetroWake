package com.metrowake.app.domain.usecases

import com.metrowake.app.data.local.StationEntity
import com.metrowake.app.data.repository.MetroRepository
import javax.inject.Inject

class CalculateJourneyProgressUseCase @Inject constructor(
    private val repository: MetroRepository
) {
    /**
     * Given the current station and the destination, calculates the number of remaining stations
     * and an estimated time of arrival (ETA).
     */
    suspend operator fun invoke(
        currentStationId: String,
        destinationStationId: String
    ): JourneyProgress? {
        val currentStation = repository.getStationById(currentStationId) ?: return null
        val destinationStation = repository.getStationById(destinationStationId) ?: return null

        val path = findPath(currentStation, destinationStation) ?: return null

        val remainingStations = path.size - 1

        // Let's assume roughly 2.5 minutes per station
        val etaMinutes = Math.ceil(remainingStations * 2.5).toInt()

        return JourneyProgress(
            remainingStations = remainingStations,
            etaMinutes = etaMinutes,
            path = path
        )
    }

    // A simple BFS to find the path between two stations
    private suspend fun findPath(start: StationEntity, end: StationEntity): List<StationEntity>? {
        if (start.id == end.id) return listOf(start)

        val queue = ArrayDeque<List<StationEntity>>()
        val visited = mutableSetOf<String>()

        queue.add(listOf(start))
        visited.add(start.id)

        while (queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val node = path.last()

            if (node.id == end.id) return path

            val neighbors = mutableListOf<StationEntity>()
            node.nextStationId?.let { id -> repository.getStationById(id)?.let { neighbors.add(it) } }
            node.previousStationId?.let { id -> repository.getStationById(id)?.let { neighbors.add(it) } }

            // If it's an interchange, find other stations with the same name on different lines
            if (node.isInterchange) {
                val interchanges = repository.getStationsByName(node.name).filter { it.id != node.id }
                neighbors.addAll(interchanges)
            }

            for (neighbor in neighbors) {
                if (neighbor.id !in visited) {
                    visited.add(neighbor.id)
                    queue.add(path + neighbor)
                }
            }
        }

        return null
    }

    data class JourneyProgress(
        val remainingStations: Int,
        val etaMinutes: Int,
        val path: List<StationEntity>
    )
}
