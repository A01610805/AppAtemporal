package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
import com.google.firebase.firestore.DocumentSnapshot

class GetUserRoleRequirement {
    suspend operator fun invoke(uid: String, repository: Repository) : DocumentSnapshot {
        return repository.getUserRole(uid)
    }
}