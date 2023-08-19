package com.tariq.newnoteapp.presentation.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tariq.newnoteapp.R
import com.tariq.newnoteapp.data.models.NotesModel
import com.tariq.newnoteapp.data.models.ui_states.HomeUiState
import com.tariq.newnoteapp.data.repository.Resources
import com.tariq.newnoteapp.navigation.AuthNavRoutes
import com.tariq.newnoteapp.navigation.MainNavRouts
import com.tariq.newnoteapp.presentation.components.NotesItem
import com.tariq.newnoteapp.utils.Constants.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel? = hiltViewModel()
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: NotesModel? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotesData()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack()
                navController.navigate(MainNavRouts.NotesScreen.passNoteId(""))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        topBar = {
            Surface(
                shadowElevation = 8.dp,
            ) {

                TopAppBar(
                    title = {
                        Text(text = "Notes", color = MaterialTheme.colorScheme.onPrimary)
                    },
                    actions = {
                        IconButton(onClick = {
                            homeViewModel?.signOutUser()
                            navController.popBackStack()
                            navController.navigate(AuthNavRoutes.Splash.route)
                        }) {
                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    navigationIcon = {

                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (homeUiState.notesData) {
                    is Resources.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }

                    is Resources.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(homeUiState.notesData.data ?: emptyList()) { notes ->
                                NotesItem(notesModel = notes,
                                    onLongClick = {
                                        openDialog = true
                                        selectedNote = notes
                                    },
                                    onClick = {
                                        navController.navigate(MainNavRouts.NotesScreen.passNoteId(notes.documentId))
                                    })
                            }

                        }
                        AnimatedVisibility(visible = openDialog) {
                            AlertDialog(
                                onDismissRequest = {
                                    openDialog = false
                                },
                                title = { Text(text = "Delete Note?") },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            selectedNote?.documentId?.let {
                                                homeViewModel?.deleteNote(it)
                                            }
                                            openDialog = false
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red.copy(alpha = 0.5f)
                                        ),
                                    ) {
                                        Text(text = "Delete")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { openDialog = false }) {
                                        Text(text = "Cancel")
                                    }
                                }
                            )


                        }
                    }

                    else -> {
                        Text(
                            text = homeUiState
                                .notesData.throwable?.localizedMessage ?: "Unknown Error",
                            color = Color.Red

                        )
                    }
                }

            }

        }
    )
    LaunchedEffect(key1 = homeViewModel?.userExists) {
        if (homeViewModel?.userExists == false) {
            navController.navigate(AuthNavRoutes.Splash.route)
        }
    }

}