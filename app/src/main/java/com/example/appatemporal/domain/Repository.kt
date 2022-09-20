package com.example.appatemporal.domain

import android.content.Context
import com.example.appatemporal.data.localdatabase.LocalDatabase
import com.example.appatemporal.data.localdatabase.entities.*
import com.example.appatemporal.domain.models.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class Repository(context: Context) {

    val firestoreAPI = FirestoreService()

    suspend fun addUser(uid: String, user: UserModel, role: String) {
        firestoreAPI.addUser(uid, user)
        firestoreAPI.addUserRole(uid, role)
    }

    suspend fun verifyUser(uid: String) : Boolean {
        return firestoreAPI.verifyUser(uid)
    }

    suspend fun getUser(uid: String) : DocumentSnapshot{
        return firestoreAPI.getUser(uid)
    }

    suspend fun getUserRole(uid: String) : DocumentSnapshot {
        return firestoreAPI.getUserRole(uid)
    }


    val actividadDao = LocalDatabase.getInstance(context).actividadDao
    val areaDao = LocalDatabase.getInstance(context).areaDao
    val estatusDao = LocalDatabase.getInstance(context).estatusDao
    val objetivoDao = LocalDatabase.getInstance(context).objetivoDao
    val proyectoDao = LocalDatabase.getInstance(context).proyectoDao

    suspend fun insertActividad(actividad: Actividad) = actividadDao.insert(actividad)
    suspend fun insertAllActividades(actividades: List<Actividad>) = actividadDao.insertAll(actividades)
    suspend fun getAllActividades() = actividadDao.getAll()
    suspend fun getActividadById(id: Int) = actividadDao.getById(id)
    suspend fun deleteActividad(actividad: Actividad) = actividadDao.delete(actividad)
    suspend fun deleteAllActividades() = actividadDao.deleteAll()

    suspend fun insertArea(area: Area) = areaDao.insert(area)
    suspend fun insertAllAreas(areas: List<Area>) = areaDao.insertAll(areas)
    suspend fun getAllAreas() = areaDao.getAll()
    suspend fun getAreaById(id: Int) = areaDao.getById(id)
    suspend fun deleteArea(area: Area) = areaDao.delete(area)
    suspend fun deleteAllAreas() = areaDao.deleteAll()

    suspend fun insertEstatus(estatus: Estatus) = estatusDao.insert(estatus)
    suspend fun insertAllEstatus(estatus: List<Estatus>) = estatusDao.insertAll(estatus)
    suspend fun getAllEstatus() = estatusDao.getAll()
    suspend fun getEstatusById(id: Int) = estatusDao.getById(id)
    suspend fun deleteEstatus(estatus: Estatus) = estatusDao.delete(estatus)
    suspend fun deleteAllEstatus() = estatusDao.deleteAll()

    suspend fun insertObjetivo(objetivo: Objetivo) = objetivoDao.insert(objetivo)
    suspend fun insertAllObjetivos(objetivos: List<Objetivo>) = objetivoDao.insertAll(objetivos)
    suspend fun getAllObjetivos() = objetivoDao.getAll()
    suspend fun getObjetivoById(id: Int) = objetivoDao.getById(id)
    suspend fun deleteObjetivo(objetivo: Objetivo) = objetivoDao.delete(objetivo)
    suspend fun deleteAllObjetivos() = objetivoDao.deleteAll()

    suspend fun insertProyecto(proyecto: Proyecto) = proyectoDao.insert(proyecto)
    suspend fun insertAllProyectos(proyectos: List<Proyecto>) = proyectoDao.insertAll(proyectos)
    suspend fun getAllProyectos() = proyectoDao.getAll()
    suspend fun getProyectoById(id: Int) = proyectoDao.getById(id)
    suspend fun deleteProyecto(proyecto: Proyecto) = proyectoDao.delete(proyecto)
    suspend fun deleteAllProyectos() = proyectoDao.deleteAll()



}