package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ich.reciptopia.presentation.main.analyze_ingredient.AnalyzeIngredientEvent
import com.ich.reciptopia.presentation.main.analyze_ingredient.AnalyzeIngredientViewModel
import com.ich.reciptopia.presentation.main.components.MainScreenUI
import com.ich.reciptopia.presentation.main.search.util.ChipState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AnalyzeIngredientScreen(
    navController: NavController,
    viewModel: AnalyzeIngredientViewModel = hiltViewModel(),
    onSearch: (List<ChipState>) -> Unit
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AnalyzeIngredientViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box {
        CameraView(
            currentImageCount = state.value.images.size,
            onImageCaptured = { bitmap, fromGallery ->
                viewModel.onEvent(AnalyzeIngredientEvent.OnImageCaptured(bitmap))
            },
            onError = {
                it.printStackTrace()
            },
            onImageButtonClicked = {
                viewModel.onEvent(AnalyzeIngredientEvent.ManageDialogStateChanged(true))
            },
            onAnalyzeButtonClicked = {
                viewModel.onEvent(AnalyzeIngredientEvent.StartAnalyzing)
            }
        )
    }

    ManageImageDialog(
        showDialog = state.value.showManageDialog,
        images = state.value.images,
        numOfImages = state.value.images.size,
        onDeleteImage = { checkedBitmaps ->
            viewModel.onEvent(AnalyzeIngredientEvent.DeleteImages(checkedBitmaps))
        },
        onAnalyzeButtonClicked = {
            viewModel.onEvent(AnalyzeIngredientEvent.StartAnalyzing)
            viewModel.onEvent(AnalyzeIngredientEvent.ManageDialogStateChanged(false))
        },
        onClose = {
            viewModel.onEvent(AnalyzeIngredientEvent.ManageDialogStateChanged(false))
        }
    )

    AnalyzeResultDialog(
        showDialog = state.value.showAnalyzeResultDialog,
        analyzeResults = state.value.analyzeResults,
        onSearchRecipes = {
            onSearch(it)
            navController.navigate(MainScreenUI.SearchScreen.route)
        }
    ) {
        viewModel.onEvent(AnalyzeIngredientEvent.CloseAnalyzeResultDialog)
    }
}