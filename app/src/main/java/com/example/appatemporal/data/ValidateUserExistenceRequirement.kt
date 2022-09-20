package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class ValidateUserExistenceRequirement {

    suspend operator fun invoke (uid: String, repository: Repository) : Boolean{
        return repository.verifyUser(uid)
    }
}