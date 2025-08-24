package com.example.londoncaptask.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.auth.presentation.LoginScreenRoot
import com.example.core.presentation.designsystem.LondonCapTaskTheme
import com.example.londoncaptask.R
import com.example.tasks.presentation.tasks_list.TasksListScreenRoot
import com.example.tasks.presentation.upsert_tasks.UpsertTaskScreenRoot
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var splitInstallManager: SplitInstallManager
    private val splitInstallListener =
        com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener { state ->
            when (state.status()) {
                SplitInstallSessionStatus.INSTALLED -> {
                    viewModel.setAnalyticsDialogVisibility(false)
                    Toast.makeText(
                        applicationContext,
                        R.string.analytics_installed,
                        Toast.LENGTH_LONG
                    ).show()
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    viewModel.setAnalyticsDialogVisibility(true)
                }

                SplitInstallSessionStatus.DOWNLOADING -> {
                    viewModel.setAnalyticsDialogVisibility(true)
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    splitInstallManager.startConfirmationDialogForResult(state, this, 0)
                }

                SplitInstallSessionStatus.FAILED -> {
                    viewModel.setAnalyticsDialogVisibility(false)
                    Toast.makeText(
                        applicationContext,
                        R.string.error_installation_failed,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(splitInstallListener)
    }

    override fun onPause() {
        super.onPause()
        splitInstallManager.unregisterListener(splitInstallListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splitInstallManager = SplitInstallManagerFactory.create(this)
        requestNotificationPermission()
        enableEdgeToEdge()
        setContent {
            LondonCapTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (viewModel.state.isCheckingAuth) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val navController = rememberNavController()
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = if (viewModel.state.isLoggedIn) "Tasks" else "Login"
                        ) {
                            composable("Login") {
                                LoginScreenRoot(
                                    onUserLoggedIn = {
                                        navController.navigate("Tasks") {
                                            popUpTo("Login") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }
                            composable("Tasks") {
                                TasksListScreenRoot(
                                    onNavigateToUpsertTask = {
                                        navController.navigate("UpsertTasks/$it")
                                    },
                                    onOpenAnalyticsPageButtonClicked = {
                                        installOrStartAnalyticsFeature()
                                    }
                                )
                            }

                            composable(
                                route = "UpsertTasks/{taskId}",
                                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                // val id = backStackEntry.arguments?.getInt("taskId") ?: -1
                                UpsertTaskScreenRoot(
                                    onTaskUpserted = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                        }
                    }

                    if (viewModel.state.showAnalyticsInstallDialog) {
                        Dialog(onDismissRequest = {}) {
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(id = R.string.installing_module),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted → now safe to send notifications
            } else {
                // User denied → maybe show a rationale or disable feature
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]!!)) {
                    // Case 2: User denied once
                    showRationaleDialog(this, permissions[0]!!)
                } else {
                    // Case 3: User denied + "Don’t ask again"
                    showGoToSettingsDialog(this)
                }
            }
        }
    }

    private fun showRationaleDialog(
        context: Context,
        permission: String
    ) {
        AlertDialog.Builder(context)
            .setTitle("Permission needed")
            .setMessage("We need notification permission to remind you about tasks.")
            .setPositiveButton("Allow") { _, _ -> requestNotificationPermission() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showGoToSettingsDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Permission denied")
            .setMessage("Please enable notifications in settings to receive reminders.")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun installOrStartAnalyticsFeature() {
        if (splitInstallManager.installedModules.contains("analytics")) {
            // using reflection :
            Intent()
                .setClassName(
                    packageName,
                    "com.example.analytics.presentation.AnalyticsActivity"
                )
                .also(::startActivity)
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule("analytics")
            .build()
        splitInstallManager
            .startInstall(request)
            .addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    R.string.error_couldnt_load_module,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun uninstallAnalyticsModule() {
        // based on feature flags we can uninstall or install module instead of just installing it when click on a button like i did
        splitInstallManager.deferredUninstall(listOf("analytics"))
            .addOnSuccessListener {
                Log.d("PlayCore", "Module scheduled for uninstall")
            }
            .addOnFailureListener { exception ->
                Log.e("PlayCore", "Failed to uninstall: $exception")
            }
    }

}

