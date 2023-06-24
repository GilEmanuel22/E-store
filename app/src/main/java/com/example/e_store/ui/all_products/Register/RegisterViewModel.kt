package com.example.e_store.ui.Register

import android.util.Patterns
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.AuthRepository
import com.example.tiuta.User
import kotlinx.coroutines.launch


class RegisterViewModel(private val repository: AuthRepository): ViewModel() {
    private val _userRegisterationStatus = MutableLiveData<FirebaseResource<User>>()
    val userRegisterationStatus : LiveData<FirebaseResource<User>> = _userRegisterationStatus



    fun createUser(userName:String,email:String,phoneNumber:String,password:String)
    {
        val error =
            if(email.isEmpty() || userName.isEmpty()||password.isEmpty()||phoneNumber.isEmpty()) {
                "Empty input"
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                "Not a valid email"
            }else null
        error?.let {
            _userRegisterationStatus.postValue(FirebaseResource.Error(it))
        }
        _userRegisterationStatus.postValue(FirebaseResource.Loading())
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName,email, password, phoneNumber)
            _userRegisterationStatus.postValue(registrationResult)
        }
    }

    class RegisterViewModelFactory(private val repository: AuthRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return RegisterViewModel(repository) as T
        }
    }
}