package com.tariq.newnoteapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun userExist(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createNewUser(
        userEmail: String,
        userPassword: String,
        onComplete: (Boolean) -> Unit
    ) = with(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    suspend fun loginUser(
        userEmail: String,
        userPassword: String,
        onComplete: (Boolean) -> Unit
    ) = with(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }
}