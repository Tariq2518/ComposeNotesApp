package com.tariq.newnoteapp.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tariq.newnoteapp.data.models.NotesModel
import com.tariq.newnoteapp.utils.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RemoteDataRepository {

    fun currentUser() = Firebase.auth.currentUser

    fun userExists(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val notesCollectionReference: CollectionReference = Firebase
        .firestore.collection(Constants.NOTES_COLLECTION_NAME)

    fun getAllNotes(
        userId: String
    ): Flow<Resources<List<NotesModel>>> = callbackFlow {
        var snapShotStateListener: ListenerRegistration? = null

        try {

            snapShotStateListener = notesCollectionReference
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .whereEqualTo("userId", userId)
                .addSnapshotListener { value, error ->
                    val response = if (value != null) {
                        val notesData = value.toObjects(NotesModel::class.java)
                        Resources.Success(data = notesData)

                    } else {
                        Resources.Error(throwable = error?.cause)
                    }
                    trySend(response)

                }

        } catch (ex: Exception) {
            trySend(Resources.Error(throwable = ex.cause))
        }

        awaitClose {
            snapShotStateListener?.remove()
        }
    }


    fun getSingleNote(
        noteId: String,
        errorCallback: (Throwable?) -> Unit,
        successCallback: (NotesModel?) -> Unit
    ) {
        notesCollectionReference
            .document(noteId)
            .get()
            .addOnSuccessListener {
                successCallback.invoke(it?.toObject(NotesModel::class.java))
            }
            .addOnFailureListener {
                errorCallback.invoke(it.cause)
            }
    }

    fun addNoteToFirebase(
        userId: String,
        notesTitle: String,
        notesDescription: String,
        timeStamp: Timestamp,
        color: Int = 0,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = notesCollectionReference.document().id
        val noteModel = NotesModel(
            userId = userId,
            documentId = documentId,
            noteTitle = notesTitle,
            notesDescription = notesDescription,
            timeStamp = timeStamp,
            colorIndex = color,

            )

        notesCollectionReference
            .document(documentId)
            .set(noteModel)
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }


    fun deleteNoteFromFirebase(
        noteId: String,
        onComplete: (Boolean) -> Unit
    ) {
        notesCollectionReference
            .document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun editNoteFromFirebase(
        noteId: String,
        notesTitle: String,
        notesDescription: String,
        color: Int,
        onComplete: (Boolean) -> Unit
    ) {

        val updatedData = hashMapOf<String, Any>(
            "colorIndex" to color,
            "notesDescription" to notesDescription,
            "noteTitle" to notesTitle,

            )

        notesCollectionReference
            .document(noteId)
            .update(updatedData)
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun signOutUser() = Firebase.auth.signOut()

}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)


}