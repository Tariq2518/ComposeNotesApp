package com.tariq.newnoteapp.data.models.ui_states

import com.tariq.newnoteapp.data.models.NotesModel

data class NotesScreenUiState(
    val noteTitle: String = "",
    val notesDescription: String = "",
    val colorIndex: Int = 0,
    val noteAddedStatus: Boolean = false,
    val noteUpdatedStatus: Boolean = false,
    val notesSelected: NotesModel? = null
)
