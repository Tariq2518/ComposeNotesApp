package com.tariq.newnoteapp.presentation.screens.note_screen

import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.tariq.newnoteapp.data.models.NotesModel
import com.tariq.newnoteapp.data.models.ui_states.NotesScreenUiState
import com.tariq.newnoteapp.data.repository.RemoteDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var noteId = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteId = savedStateHandle.get<String>("noteId") ?: ""
            if (noteId.isNotBlank()) {
                getSingleNote(noteId)
            }

        }
    }

    var notesScreenUiState by mutableStateOf(NotesScreenUiState())
        private set

    private
    val userExists: Boolean
        get() = remoteDataRepository.userExists()

    private val currentUser: FirebaseUser?
        get() = remoteDataRepository.currentUser()


    fun notesColorChange(color: Int) {
        notesScreenUiState = notesScreenUiState.copy(colorIndex = color)
    }

    fun onNoteTitleChange(title: String) {
        notesScreenUiState = notesScreenUiState.copy(noteTitle = title)
    }


    fun onNotesDescriptionChange(notesDescription: String) {
        notesScreenUiState = notesScreenUiState.copy(notesDescription = notesDescription)
    }


    fun addNoteToFirebase() {
        if (userExists) {
            remoteDataRepository.addNoteToFirebase(
                userId = currentUser?.uid!!,
                notesTitle = notesScreenUiState.noteTitle,
                notesDescription = notesScreenUiState.notesDescription,
                color = notesScreenUiState.colorIndex,
                timeStamp = Timestamp.now()
            ) {
                notesScreenUiState = notesScreenUiState.copy(noteAddedStatus = it)
            }
        }
    }


    fun editFieldData(note: NotesModel) {
        notesScreenUiState = notesScreenUiState.copy(
            noteTitle = note.noteTitle,
            notesDescription = note.notesDescription,
            colorIndex = note.colorIndex
        )
    }

    fun getSingleNote(noteId: String) {
        remoteDataRepository.getSingleNote(
            noteId = noteId,
            errorCallback = {

            },
            successCallback = {
                notesScreenUiState = notesScreenUiState
                    .copy(notesSelected = it)
                notesScreenUiState.notesSelected?.let { note ->
                    editFieldData(note)
                }
            }
        )
    }


    fun updateNoteInFirebase(
        noteId: String
    ) {
        remoteDataRepository.editNoteFromFirebase(
            noteId = noteId,
            notesTitle = notesScreenUiState.noteTitle,
            notesDescription = notesScreenUiState.notesDescription,
            color = notesScreenUiState.colorIndex
        ) {
            notesScreenUiState = notesScreenUiState.copy(noteUpdatedStatus = it)

        }
    }

    fun resetUpdateState() {
        notesScreenUiState = notesScreenUiState.copy(
            noteUpdatedStatus = false,
            noteAddedStatus = false
        )
    }

    fun resetAllStates() {
        notesScreenUiState = NotesScreenUiState()
    }

}