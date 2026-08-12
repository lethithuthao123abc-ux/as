package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.viewmodel.StrokeViewModel
import com.example.ui.screens.CalculatorsScreen
import com.example.ui.screens.DrugsAndFlowchartsScreen
import com.example.ui.screens.EmergencyProtocolScreen
import com.example.ui.screens.GuidelinesScreen
import com.example.ui.screens.PatientRecordsScreen

sealed class NavTab(val route: String, val title: String, val icon: ImageVector) {
    object Emergency : NavTab("emergency", "Cấp Cứu", Icons.Default.FlashOn)
    object Guidelines : NavTab("guidelines", "Khuyến Cáo", Icons.Default.MenuBook)
    object Calculators : NavTab("calculators", "Thang Điểm", Icons.Default.Calculate)
    object DrugsFlowcharts : NavTab("drugs_flowcharts", "Lưu Đồ/Thuốc", Icons.Default.Medication)
    object Records : NavTab("records", "Hồ Sơ BN", Icons.Default.Assignment)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: StrokeViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavTab.Emergency.route

    val tabs = listOf(
        NavTab.Emergency,
        NavTab.Guidelines,
        NavTab.Calculators,
        NavTab.DrugsFlowcharts,
        NavTab.Records
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Khuyến Cáo Đột Quỵ 2026 (AHA/ESO)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            if (currentRoute != tab.route) {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title, fontSize = 11.sp, fontWeight = FontWeight.Medium) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavTab.Emergency.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavTab.Emergency.route) {
                EmergencyProtocolScreen(
                    viewModel = viewModel,
                    onNavigateToFlowchart = {
                        navController.navigate(NavTab.DrugsFlowcharts.route)
                    },
                    onNavigateToNihss = {
                        navController.navigate(NavTab.Calculators.route)
                    }
                )
            }
            composable(NavTab.Guidelines.route) {
                GuidelinesScreen(viewModel = viewModel)
            }
            composable(NavTab.Calculators.route) {
                CalculatorsScreen(
                    viewModel = viewModel,
                    onNavigateToRecords = {
                        navController.navigate(NavTab.Records.route)
                    }
                )
            }
            composable(NavTab.DrugsFlowcharts.route) {
                DrugsAndFlowchartsScreen(viewModel = viewModel)
            }
            composable(NavTab.Records.route) {
                PatientRecordsScreen(viewModel = viewModel)
            }
        }
    }
}
