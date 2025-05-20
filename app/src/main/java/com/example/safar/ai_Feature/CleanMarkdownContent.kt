package com.example.safar.ai_Feature

fun cleanMarkdownContent(input: String): String {
    val boldPattern = Regex("\\*\\*(.*?)\\*\\*")
    val asteriskPattern = Regex("\\*")

    val cleanedContent = input
        .replace(boldPattern) { match ->
            match.groupValues[1]
        }
        .replace(asteriskPattern, "")
        .trim()

    return cleanedContent
}
