package com.example.tasks.presentation.upsert_tasks

sealed interface UpsertTaskScreenAction {
    data class OnTaskTitleChanged(val title : String) : UpsertTaskScreenAction
    data object OnUpsertTaskClicked : UpsertTaskScreenAction
}