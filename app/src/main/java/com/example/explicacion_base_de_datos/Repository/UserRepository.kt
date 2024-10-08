package com.example.explicacion_base_de_datos.Repository

import androidx.compose.animation.splineBasedDecay
import com.example.explicacion_base_de_datos.Dao.UserDao
import com.example.explicacion_base_de_datos.Model.User

class UserRepository (private val userDao : UserDao) {
    suspend fun insert(user: User){
        userDao.insert(user)
    }

    suspend fun getAllUser(): List<User>{
        return userDao.getAllUsers()
    }

    suspend fun deleteById(userId: Int): Int{
        return userDao.deleteById(userId)
    }

    suspend fun updateUser(id:Int, nombre:String, apellido:String, edad:Int){
        return userDao.updateUser(id, nombre, apellido, edad)
    }

    suspend fun buscarId(id:Int): List<User>{
        return userDao.buscarId(id)
    }

}