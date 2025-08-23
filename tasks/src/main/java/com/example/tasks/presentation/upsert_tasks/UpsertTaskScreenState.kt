package com.example.tasks.presentation.upsert_tasks

import com.example.tasks.domain.models.Task

data class UpsertTaskScreenState(
    val isLoading : Boolean = false,
    val taskTitleTextField : String = "",
    val currentTask : Task? = null
)
