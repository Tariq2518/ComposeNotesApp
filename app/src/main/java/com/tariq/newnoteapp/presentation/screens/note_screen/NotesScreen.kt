package com.tariq.newnoteapp.presentation.screens.note_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tariq.newnoteapp.data.models.ui_states.NotesScreenUiState
import com.tariq.newnoteapp.presentation.components.ColorItem
import com.tariq.newnoteapp.utils.Constants
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavHostController,
    notesViewModel: NotesViewModel? = hiltViewModel()
) {
    val notesScreenUiState = notesViewModel?.notesScreenUiState ?: NotesScreenUiState()
    var noteId = notesViewModel?.noteId

    val isUiNotEmpty = notesScreenUiState.noteTitle.isNotBlank() &&
            notesScreenUiState.notesDescription.isNotBlank()

    val selectedColor by animateColorAsState(
        targetValue = Constants.colors[notesScreenUiState.colorIndex], label = ""
    )

    val isNoteIdNotEmpty = noteId?.isNotBlank()


    val currentIcon = if (isNoteIdNotEmpty == true) Icons.Default.Refresh
    else Icons.Default.Check

    LaunchedEffect(key1 = noteId) {
        if (isNoteIdNotEmpty == true) {
            if (noteId != null) {
                notesViewModel?.getSingleNote(noteId)
            }
        } else notesViewModel?.resetAllStates()
    }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {

            FloatingActionButton(onClick = {
                if (isNoteIdNotEmpty == true) {
                    if (noteId != null) {
                        notesViewModel?.updateNoteInFirebase(noteId)
                    }
                } else {
                    if (isUiNotEmpty)
                        notesViewModel?.addNoteToFirebase()
                }
            }) {
                Icon(imageVector = currentIcon, contentDescription = "")
            }


        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = selectedColor)
                    .padding(padding)
            ) {

                if (notesScreenUiState.noteAddedStatus) {
                    LaunchedEffect(key1 = notesScreenUiState.noteAddedStatus) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Notes Added")
                            notesViewModel?.resetUpdateState()
                            navController.popBackStack()
                        }
                    }
                }

                if (notesScreenUiState.noteUpdatedStatus) {
                    LaunchedEffect(key1 = notesScreenUiState.noteUpdatedStatus) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Notes Updated")
                            notesViewModel?.resetUpdateState()
                            navController.popBackStack()
                        }
                    }
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    contentPadding = PaddingValues(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    )
                ) {
                    itemsIndexed(Constants.colors) { index, value ->
                        ColorItem(color = value) {
                            notesViewModel?.notesColorChange(index)
                        }

                    }
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = notesScreenUiState.noteTitle ?: "",
                    onValueChange = {
                        notesViewModel?.onNoteTitleChange(it)
                    },
                    label = {
                        Text(text = "Title")
                    },
                    shape = RoundedCornerShape(20),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp),
                    value = notesScreenUiState.notesDescription ?: "",
                    onValueChange = {
                        notesViewModel?.onNotesDescriptionChange(it)
                    },
                    label = {
                        Text(text = "Note")
                    },
                    shape = RoundedCornerShape(5),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

        }

    )
}

