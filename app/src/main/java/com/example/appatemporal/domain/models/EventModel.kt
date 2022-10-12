package com.example.appatemporal.domain.models

import java.util.*
/**
 * This class is a model that will contain the data necessary
 * in order to create a event objet successfully for it manage in the activities
 *
 * @author Andrés
 *
 * */

data class EventModel (
    var id: String,
    var nombre: String,
    var descripcion: String,
    var ciudad: String,
    var estado: String,
    var ubicacion: String,
    var direccion: String,
    var longitud: String,
    var latitud: String,
    var foto_portada: String,
    var video: String,
    var activo: String,
    var aprobado: String
)