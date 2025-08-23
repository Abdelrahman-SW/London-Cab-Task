package com.example.tasks.presentation.tasks_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tasks.domain.models.Task

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: Task, onTaskClicked: (Task) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(onClick = { onTaskClicked(task) })
            .padding(16.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(text = task.description)
    }
}