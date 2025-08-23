package com.example.tasks.presentation.upsert_tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.ext.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpsertTaskScreenRoot(
    modifier: Modifier = Modifier,
    onTaskUpserted: () -> Unit,
    upsertTaskViewModel: UpsertTaskViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(upsertTaskViewModel.events) {
        when (it) {
            is UpsertTaskScreenEvent.Error -> {
                context.showToast(it.error)
            }

            UpsertTaskScreenEvent.OnTaskUpserted -> {
                onTaskUpserted()
            }
        }
    }

    UpsertTaskScreen(
        modifier = modifier,
        upsertTaskScreenState = upsertTaskViewModel.state,
        onAction = upsertTaskViewModel::onAction,
    )
}

@Composable
fun UpsertTaskScreen(
    modifier: Modifier = Modifier,
    upsertTaskScreenState: UpsertTaskScreenState,
    onAction: (UpsertTaskScreenAction) -> Unit
) {

    val isCreatingTask = upsertTaskScreenState.currentTask == null


    if (upsertTaskScreenState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                label = {
                    Text(
                        text = "Title"
                    )
                },
                value = upsertTaskScreenState.taskTitleTextField,
                onValueChange = {
                    onAction(UpsertTaskScreenAction.OnTaskTitleChanged(it))
                }
            )

            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    onAction(UpsertTaskScreenAction.OnUpsertTaskClicked)
                }
            ) {
                Text(if (isCreatingTask) "Create Task" else "Update Task")
            }
        }
    }
}