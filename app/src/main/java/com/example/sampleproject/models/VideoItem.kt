package com.example.sampleproject.models

data class VideoItem(
    val id: String,
    val title: String,
    val videoUrl: String,
    val thumbUrl: String? = null
)
