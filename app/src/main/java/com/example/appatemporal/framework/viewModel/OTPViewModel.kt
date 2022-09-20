package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.ValidateUserExistenceRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch

class OTPViewModel : ViewModel() {


    val userExists = MutableLiveData<Boolean>()

    fun verifyUser(uid: String, repository: Repository) {
        viewModelScope.launch {
            val validateUserExistenceRequirement = ValidateUserExistenceRequirement()
            var existence = validateUserExistenceRequirement(uid, repository)
            userExists.postValue(existence)
        }
    }

}