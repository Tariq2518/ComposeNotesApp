package com.tariq.newnoteapp.data.models.ui_states

import com.tariq.newnoteapp.data.models.NotesModel
import com.tariq.newnoteapp.data.repository.Resources

data class HomeUiState(
    val notesData: Resources<List<NotesModel>> = Resources.Loading(),
    val notesDeletedStatus: Boolean = false
)
