package es.iessaladillo.pedrojoya.pr08.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO:
//  Crear una clase de datos User que implemente Parcelable y que
//  contenga el id de tipo Long, nombre, email, phoneNumber, address, web y photoUrl
//  todas ellas de tipo String.
@Parcelize
data class User(val id: Long, val name: String, val email: String, val address: String,
        val phoneNumber: String, val web: String, val photoUrl: String): Parcelable
