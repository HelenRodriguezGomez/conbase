package com.example.explicacion_base_de_datos.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.explicacion_base_de_datos.Model.User


@Dao
interface UserDao {

    //suspend evitar que la aplica falle cuando se realizan las peticiones al realizar operaciones asincrona


    @Insert(onConflict = OnConflictStrategy.REPLACE) //Revision de conflictos entre entregistos
    suspend fun insert(user: User)

    @Query("SELECT*FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun  deleteById(userId : Int): Int

    @Query("UPDATE users SET nombre =:nombre, apellido=:apellido, edad =:edad WHERE id=:id")
    suspend fun updateUser(id:Int, nombre:String, apellido:String, edad:Int)

    @Query("SELECT * FROM users WHERE id =:id")
    suspend fun buscarId(id:Int): List<User>




    /*@Update
    suspend fun update(user: User)
   */

}