package com.example.tasks.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id : Int,
    val description: String ,
    val timeStamp : Long
)
