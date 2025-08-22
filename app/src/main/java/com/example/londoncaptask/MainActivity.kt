package com.example.londoncaptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.presentation.LoginScreenRoot
import com.example.core.presentation.designsystem.LondonCapTaskTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    }
                    else {
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
                                Button(
                                    onClick = {
                                        viewModel.logout()
                                        navController.navigate("Login") {
                                            popUpTo("Tasks") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                ) {
                                    Text("Logout")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

