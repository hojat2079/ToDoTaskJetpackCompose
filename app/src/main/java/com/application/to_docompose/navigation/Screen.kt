package com.application.to_docompose.navigation

import androidx.navigation.NavHostController
import com.application.to_docompose.util.Action
import com.application.to_docompose.util.LIST_SCREEN
import com.application.to_docompose.util.SPLASH_SCREEN

class Screen(private val navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

}