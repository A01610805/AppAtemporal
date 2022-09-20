package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel

class RegisterUserRequirement {

    suspend operator fun invoke(uid: String, user: UserModel, role: String, repository: Repository) {
        repository.addUser(uid, user, role)
    }
}