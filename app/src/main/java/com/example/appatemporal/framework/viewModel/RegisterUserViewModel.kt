package com.example.appatemporal.framework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.RegisterUserRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel
import kotlinx.coroutines.launch

class RegisterUserViewModel : ViewModel() {

    val registerUserRequirement = RegisterUserRequirement()

    fun addUser(uid: String ,user: UserModel, role: String, repository: Repository) {
        viewModelScope.launch {
            registerUserRequirement(uid, user, role, repository)
        }
    }

}