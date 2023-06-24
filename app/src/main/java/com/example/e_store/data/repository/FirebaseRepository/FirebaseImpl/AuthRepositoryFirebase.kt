package com.example.tiuta.Repository.FirebaseImpl

import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.AuthRepository
import com.example.tiuta.User
import com.example.tiuta.utils.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthRepositoryFirebase: AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val userRef = FirebaseFirestore.getInstance().collection("users")
    override suspend fun currentUser(): FirebaseResource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await().toObject(User::class.java)!!
                FirebaseResource.Success(user)
            }
        }
    }

    override suspend fun login(email: String, password: String): FirebaseResource<User> {
        return withContext(Dispatchers.IO){
            safeCall {

                val result =
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user =  userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                FirebaseResource.Success(user)
            }
        }
    }

    override suspend fun createUser(
        userName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): FirebaseResource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                val userID = registrationResult.user?.uid!!
                val newUser = User(userName,email,phoneNumber)
                userRef.document(userID).set(newUser).await()
                FirebaseResource.Success(newUser)
            }
        }
    }


    override fun logout() {
        firebaseAuth.signOut()
    }
}