package com.example.explicacion_base_de_datos.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.explicacion_base_de_datos.Dao.UserDao
import com.example.explicacion_base_de_datos.Model.User


@Database(entities = [User::class], version =1, exportSchema = false)
 abstract class UserDatabase : RoomDatabase() {
     abstract fun  userDao():UserDao

     // volatile permite que cualquier hilo que acceda a la variable tenga la version mas actualizada

     companion object{
          @Volatile
          private var INSTANCE:UserDatabase? = null

         fun getDatabase(context : Context): UserDatabase{
             return INSTANCE ?: synchronized(this){
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     UserDatabase :: class.java,
                     "user_database"
                 ).build()
                 INSTANCE =  instance
                 instance
             }

         }
     }
}