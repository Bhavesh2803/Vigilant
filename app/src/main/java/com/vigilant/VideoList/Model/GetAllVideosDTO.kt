package com.vigilant.VideoList.Model

data class GetAllVideosDTO(
    val `data`: List<Data>,
    val success: Boolean
)