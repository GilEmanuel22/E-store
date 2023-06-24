package com.example.e_store.ui.Login



import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.AuthRepository
import com.example.tiuta.User
import kotlinx.coroutines.launch


class LoginViewModel(private val authRepo: AuthRepository): ViewModel() {

    private val _userSignInStatus = MutableLiveData<FirebaseResource<User>>()
    val userSignInStatus: LiveData<FirebaseResource<User>> = _userSignInStatus

    private val _currentUser = MutableLiveData<FirebaseResource<User>>()
    val currentUser: LiveData<FirebaseResource<User>> = _currentUser

    init{
        viewModelScope.launch {
            _currentUser.postValue(FirebaseResource.Loading())
            _currentUser.postValue(authRepo.currentUser())
        }
    }
    fun signInUser(email: String,password:String){
        if(email.isEmpty()||password.isEmpty())
        {
            _userSignInStatus.postValue(FirebaseResource.Error("Empty email or password"))
        }
        else{
            _userSignInStatus.postValue(FirebaseResource.Loading())
            viewModelScope.launch {
                val loginResult = authRepo.login(email, password)
                _userSignInStatus.postValue(loginResult)
            }
        }

    }


    class LoginViewModelFactory(private val repository: AuthRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return LoginViewModel(repository) as T
        }
    }
}