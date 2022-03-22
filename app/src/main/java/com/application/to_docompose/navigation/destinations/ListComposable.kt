package com.application.to_docompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.application.to_docompose.ui.screen.list.ListScreen
import com.application.to_docompose.util.LIST_ARGUMENT_KEY
import com.application.to_docompose.util.LIST_SCREEN
import com.application.to_docompose.util.toAction
import com.application.to_docompose.viewmodel.SharedViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN, arguments = listOf(
            navArgument(
                name = LIST_ARGUMENT_KEY
            ) {
                type = NavType.StringType
            }
        )
    ) { navBackStack ->
        val action = navBackStack.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
        )
    }
}