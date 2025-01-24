package com.example.safar.ai_Feature

fun cleanMarkdownContent(input: String): String {
    val boldPattern = Regex("\\*\\*(.*?)\\*\\*") // Matches bold text **like this**
    val asteriskPattern = Regex("\\*") // Matches single asterisks *

    val cleanedContent = input
        .replace(boldPattern) { match ->
            match.groupValues[1]
        }
        .replace(asteriskPattern, "") // Removes any leftover single asterisks
        .trim()

    return cleanedContent
}
