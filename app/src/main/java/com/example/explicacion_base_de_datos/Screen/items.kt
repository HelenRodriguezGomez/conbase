package com.example.conbase.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explicacion_base_de_datos.Dao.UserDao
import com.example.explicacion_base_de_datos.Model.User
import com.example.explicacion_base_de_datos.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var Eliminar by remember { mutableStateOf("") }
    var Buscar by remember { mutableStateOf("") }
    var users by remember { mutableStateOf(listOf<User>()) }
    var Listar by remember { mutableStateOf(listOf<User>()) }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .background(Color(0xFFFFC1E3)), // Fondo rosado claro
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "Registro de Usuarios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2196F3), // Color azul
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.size(300.dp, 50.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.size(300.dp, 50.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                modifier = Modifier.size(300.dp, 50.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val user = User(
                        nombre = nombre,
                        apellido = apellido,
                        edad = edad.toIntOrNull() ?: 0
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.insert(user)
                        }
                        Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.size(200.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Botón azul
            ) {
                Text("Registrar", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    scope.launch {
                        users = withContext(Dispatchers.IO) {
                            userRepository.getAllUser()
                        }
                    }
                },
                modifier = Modifier.size(200.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Botón azul
            ) {
                Text(text = "Listar", color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column {
                users.forEach { user ->
                    Text(
                        "ID:${user.id} Nombre: ${user.nombre} Apellido: ${user.apellido} Edad: ${user.edad}",
                        fontSize = 16.sp,
                        color = Color(0xFF0D47A1) // Texto azul oscuro
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = Eliminar,
                onValueChange = { Eliminar = it },
                label = { Text("Eliminar") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.deleteById(Eliminar.toInt())
                        }
                        Toast.makeText(context, "Usuario Eliminado", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)) // Botón rosado
            ) {
                Text(text = "Eliminar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = Buscar,
                onValueChange = { Buscar = it },
                label = { Text("ID BUSCAR") },
                modifier = Modifier.size(300.dp, 50.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    scope.launch {
                        Listar = withContext(Dispatchers.IO) {
                            userRepository.buscarId(Buscar.toInt())
                        }
                    }
                },
                modifier = Modifier.size(200.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Botón azul
            ) {
                Text(text = "Buscar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Listar.forEach { user ->
                TextField(
                    value = user.nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.size(300.dp, 50.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = user.apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.size(300.dp, 50.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = user.edad.toString(),
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier.size(300.dp, 50.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                userRepository.updateUser(Buscar.toInt(), nombre, apellido, edad.toInt())
                            }
                        }
                    },
                    modifier = Modifier.size(200.dp, 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Botón azul
                ) {
                    Text(text = "Actualizar", color = Color.White)
                }
            }
        }
    }
}
