package com.metrowake.app.utils

import java.util.Locale
import kotlin.math.min

object FuzzyMatcher {

    /**
     * Calculates the Levenshtein distance between two strings.
     * Lower distance means the strings are more similar.
     */
    fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
        val lhsLength = lhs.length
        val rhsLength = rhs.length

        var cost = Array(lhsLength + 1) { it }
        var newCost = Array(lhsLength + 1) { 0 }

        for (i in 1..rhsLength) {
            newCost[0] = i
            for (j in 1..lhsLength) {
                val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1
                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1
                newCost[j] = min(min(costInsert, costDelete), costReplace)
            }
            val swap = cost
            cost = newCost
            newCost = swap
        }
        return cost[lhsLength]
    }

    /**
     * Calculates a similarity score between 0.0 and 1.0.
     * 1.0 means exact match, 0.0 means completely different.
     */
    fun similarityScore(s1: String, s2: String): Double {
        val maxLen = java.lang.Math.max(s1.length, s2.length)
        if (maxLen == 0) return 1.0
        val dist = levenshtein(s1, s2)
        return (maxLen - dist).toDouble() / maxLen.toDouble()
    }

    /**
     * Normalizes a string for better phonetic/fuzzy matching.
     * e.g., "Rajeev Chowk" -> "rajiv chowk"
     */
    fun normalizeForPhonetic(input: String): String {
        var text = input.lowercase(Locale.getDefault())
        // Apply common misspellings/phonetic rules in Indian contexts
        text = text.replace("ee", "i")
        text = text.replace("chauk", "chowk")
        text = text.replace("oo", "u")
        text = text.replace("dh", "d") // Dhaula -> Daula
        text = text.replace("ph", "f") // Phool -> Fool
        text = text.replace(Regex("[^a-z0-9 ]"), "") // Remove punctuation
        return text.trim()
    }
}
