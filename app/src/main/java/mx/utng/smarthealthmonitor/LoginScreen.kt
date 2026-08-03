package mx.utng.smarthealthmonitor

import SmartHealthMonitorTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.text.KeyboardOptions


import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.unit.dp


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {

    // =========================
    // States
    // =========================
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var emailError by remember {
        mutableStateOf("")
    }


    // =========================
    // Validation Function
    // =========================
    fun validar(): Boolean {

        emailError = when {

            email.isBlank() ->
                "El email no puede estar vacío"

            !email.contains("@") ->
                "Email inválido"

            password.length < 6 ->
                "La contraseña debe tener mínimo 6 caracteres"

            else -> ""
        }

        return emailError.isEmpty()
    }


    // =========================
    // UI
    // =========================
    SmartHealthMonitorTheme {

        Scaffold { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center
            ) {

                // =========================
                // Logo
                // =========================
                Icon(
                    imageVector = Icons.Default.Favorite,

                    contentDescription = "SmartHealth Logo",

                    tint = MaterialTheme.colorScheme.primary,

                    modifier = Modifier.size(80.dp)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "SmartHealth Monitor",

                    style = MaterialTheme.typography.headlineMedium,

                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )


                // =========================
                // Email Field
                // =========================
                OutlinedTextField(
                    value = email,

                    onValueChange = {
                        email = it
                        emailError = ""
                    },

                    label = {
                        Text("Correo electrónico")
                    },

                    isError = emailError.isNotEmpty(),

                    supportingText = {

                        if (emailError.isNotEmpty()) {

                            Text(
                                text = emailError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // =========================
                // Password Field
                // =========================
                OutlinedTextField(
                    value = password,

                    onValueChange = {
                        password = it
                    },

                    label = {
                        Text("Contraseña")
                    },

                    visualTransformation =
                        if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },

                    trailingIcon = {

                        IconButton(
                            onClick = {
                                showPassword = !showPassword
                            }
                        ) {

                            Icon(
                                imageVector =
                                    if (showPassword) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },

                                contentDescription = "Mostrar/Ocultar contraseña"
                            )
                        }
                    },

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                // =========================
                // Login Button
                // =========================
                Button(
                    onClick = {

                        if (validar()) {

                            isLoading = true

                            // Simulación login
                            onLoginSuccess()
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    enabled = !isLoading
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),

                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    } else {

                        Text(
                            text = "ENTRAR",

                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // =========================
                // Forgot Password
                // =========================
                TextButton(
                    onClick = { }
                ) {

                    Text(
                        text = "¿Olvidaste tu contraseña?"
                    )
                }
            }
        }
    }
}
