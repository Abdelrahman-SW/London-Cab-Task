package com.example.tasks.presentation.upsert_tasks

import com.example.core.presentation.ui.UiText

sealed interface UpsertTaskScreenEvent {
    data object OnTaskUpserted : UpsertTaskScreenEvent
    data class Error(val error: UiText) : UpsertTaskScreenEvent
}