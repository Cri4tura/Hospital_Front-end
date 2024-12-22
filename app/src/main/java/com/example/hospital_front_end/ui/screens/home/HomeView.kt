package com.example.hospital_front_end.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.ui.components.MyAppBarWithDrawer
import com.example.hospital_front_end.navigation.NavigationViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.hospital_front_end.nurseRepository.NurseRepository
import kotlin.coroutines.coroutineContext
import kotlin.random.Random


@Composable
fun HomeView(
    navViewModel: NavigationViewModel
) {
    val cards = remember { mutableStateListOf<Int>() }

    // Inicializa con 20 elementos
    if (cards.isEmpty()) {
        cards.addAll(0 until 2)
    }

    MyAppBarWithDrawer(
        navViewModel = navViewModel,
        pageTitle = "Home",
        actionButton = {
            FloatingActionButton(
                onClick = {
                    // Añade un nuevo elemento al final
                    cards.add(cards.size)
                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        screenContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                items(cards) { index -> // Repite 20 veces

                    // Recuerda el estado para cada Card
                    var isFilled by remember { mutableStateOf(false) }

                    // Genera un color aleatorio solo una vez
                    val randomColor = remember { getRandomColor() }

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clickable { isFilled = !isFilled }, // Cambia el estado
                        elevation = CardDefaults.cardElevation(4.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))

                            // Primera fila con íconos y título
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Ícono circular con texto
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(randomColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "A",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }

                                // Ícono de estrella que cambia
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = if (isFilled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Star Icon",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Título y subtítulo
                            Text(
                                text = "Title ${index + 1}",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Subhead",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Contenido del cuerpo
                            Text(
                                text = "Material is a design system – backed by open source code – that helps teams build high-quality digital experiences.",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    )
}

fun getRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),   // Valor entre 0.0 y 1.0
        green = Random.nextFloat(), // Valor entre 0.0 y 1.0
        blue = Random.nextFloat(),  // Valor entre 0.0 y 1.0
        alpha = 1f                  // Opacidad completa
    )
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(
        navViewModel = NavigationViewModel(NurseRepository())
    )
}
