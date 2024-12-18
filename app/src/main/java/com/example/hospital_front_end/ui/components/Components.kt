package com.example.hospital_front_end.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.hospital_front_end.R
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.ui.navigation.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBarWithDrawer(
    content: @Composable (PaddingValues) -> Unit,
    navViewModel: NavigationViewModel,
    pageTitle: String,
    //imageResource: Int,
) {
    var showDialog by remember { mutableStateOf(false) }
    var presses by remember { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Drawer Title",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()

                Text(
                    "Section 1",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                NavigationDrawerItem(
                    label = {
                        PrimaryButton(
                            onClick = { navViewModel.navigateToNurseList() },
                            imageResource = R.drawable.list,
                            text = "View List",
                            contentDescription = "View List Button",
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    selected = false,
                    onClick = {  }
                )
                NavigationDrawerItem(
                    label = { PrimaryButton(
                        onClick = { navViewModel.navigateToFindByName() },
                        imageResource = R.drawable.search,
                        text = "Find by Name",
                        contentDescription = "Find by Name Button",
                        modifier = Modifier.fillMaxWidth()
                    ) },
                    selected = false,
                    onClick = { }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    "Section 2",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    badge = { Text("20") }, // Placeholder
                    onClick = { /* Handle click */ }
                )
                NavigationDrawerItem(
                    label = { Text("Help and feedback") },
                    selected = false,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Handle click */ },
                )
                NavigationDrawerItem(
                    label = { Text("Help and feedback") },
                    selected = false,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Handle click */ },
                )
                NavigationDrawerItem(
                    label = { Text("Help and feedback") },
                    selected = false,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowForward,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Handle click */ },
                )

                HorizontalDivider(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = {
                        LogoutButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .fillMaxWidth(),
                            enabled = true,
                            text = "Log Out",
                        )
                        LogoutConfirmationDialog(
                            showDialog = showDialog,
                            onDismiss = { showDialog = false },
                            onConfirm = { navViewModel.navigateToLogin() }
                        )
                    },
                    selected = false,
                    icon = {

                    },
                    onClick = { /* Handle click */ },
                )
            }
        },
        gesturesEnabled = true,
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("$pageTitle") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                modifier = Modifier.size(ButtonDefaults.MinWidth),
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Configuración"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navViewModel.navigateBack() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Configuración"
                            )
                        }
                    },
                    colors = TopAppBarColors(

                        //containerColor = Color.Black,
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Green,
                        navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                        titleContentColor = MaterialTheme.colorScheme.tertiary,
                        actionIconContentColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { presses++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            //content(innerPadding)
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SingleChoiceSegmentedButton(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.padding(8.dp),
                    text =
                    """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
                )
            }
        }
    }
}

@Composable
fun SingleChoiceSegmentedButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Day", "Month", "Week")

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@Composable
fun DrawerContent(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navViewModel: NavigationViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.secondary)

    ) {
        Spacer(modifier = Modifier.height(60.dp))

        TextMenu(
            imageResource = R.drawable.list,
            text = "Directory",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scope.launch { drawerState.close() }
                    navViewModel.navigateToNurseList()
                },
            enabled = true

        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.background
        )
        TextMenu(
            imageResource = R.drawable.search,
            text = "Search",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scope.launch { drawerState.close() }
                    navViewModel.navigateToFindByName()
                },
            enabled = true
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.background
        )
        TextMenu(
            imageResource = R.drawable.search_id,
            text = "Find by ID",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scope.launch { drawerState.close() }
                    //TODO()
                },
            enabled = false
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.background
        )


    }
}

@Composable
fun TitleMenu(
    title: String,
    icon: ImageVector
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text( title )
    }
}


@Composable
fun EmailInput(
    email: String,
    onEmailChange: (String) -> Unit,
    isError: String?
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(stringResource(R.string.email_placeholder_text)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        isError = isError?.isNotEmpty() ?: false,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
            focusedTextColor = Color.Black,
        )
    )
    if (isError?.isNotEmpty() == true) {
        Text(
            text = isError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun FingerPrintAuth(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.authenticate_with_biometrics_text),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.huella),
            contentDescription = "Fingerprint Icon",
            modifier = Modifier.size(50.dp)

        )
    }
}

@Composable
fun PasswordInput(
    password: String,
    passwordVisible: Boolean,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    isError: String?,
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.password_placeholder_text)) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(15.dp),
        isError = isError?.isNotEmpty() ?: false,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
            focusedTextColor = Color.Black,
        ),
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                val icon =
                    if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                val description = if (passwordVisible) "Hide password" else "Show password"

                if (passwordVisible) {
                    Image(
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp),
                        painter = painterResource(id = icon),
                        contentDescription = description
                    )
                }
            }
        }
    )
    if (isError?.isNotEmpty() == true) {
        Text(
            text = isError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun VideoPlayer(
    context: Context,
    videoResId: Int,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    borderWidth: Dp = 2.dp
) {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { PlayerView(it).apply { player = exoPlayer; useController = false } },
        modifier = modifier
            .clip(CircleShape)
            .border(borderWidth, borderColor, CircleShape)
    )
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    imageResource: Int,
    text: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TextMenu(
    imageResource: Int,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = if (enabled) modifier
            .padding(horizontal = 16.dp)
            .height(45.dp) else modifier
            .alpha(0.5f)
            .padding(horizontal = 16.dp)
            .height(45.dp)
            .clickable() {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = text,
            modifier = Modifier.size(35.dp)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}

@Composable
fun LogoutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red.copy(alpha = 0.7f),
            contentColor = Color.Black
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.log_out),
            contentDescription = "Log Out",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LogoutConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to log out?") },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("No", color = lerp(Color.Green, Color.Black, 0.35f))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    onConfirm()
                }) {
                    Text(
                        "Yes",
                        modifier = Modifier.padding(horizontal = 40.dp),
                        color = lerp(Color.Red, Color.Black, 0.35f)
                    )
                }
            }
        )
    }
}

@Composable
fun NurseItem(
    nurse: Nurse,
    navViewModel: NavigationViewModel
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navViewModel.navigateToProfile(nurse)
            }) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon", modifier = Modifier.size(60.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${nurse.name} ${nurse.surname}",
                style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun NurseExtendedItem(
    nurse: Nurse
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon", modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${nurse.name} ${nurse.surname}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Column {
        Row {
            Text(
                text = "Age: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
            Text(
                text = nurse.age.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Birth Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.birthDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Email: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Register Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.registerDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 22.sp
            )
        }
    }
}

