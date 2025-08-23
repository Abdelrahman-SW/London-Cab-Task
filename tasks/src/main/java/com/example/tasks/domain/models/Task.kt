package com.example.tasks.domain.models

data class Task(
    val id : Int = 0,
    val description : String,
    val timeStamp : Long
)