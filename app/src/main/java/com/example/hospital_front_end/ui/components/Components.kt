package com.example.hospital_front_end.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.material3.ShapeDefaults
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
    navViewModel: NavigationViewModel,
    pageTitle: String,
    actionButton: @Composable () -> Unit,
    screenContent: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "User Name".uppercase(),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.nurse_register),
                        contentDescription = "User Image",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                }

                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Spacer(Modifier.height(16.dp))
                    NavigationDrawerItem(
                        label = {
                            PrimaryButton(
                                onClick = { navViewModel.navigateToHome();navViewModel.navigateToFindByName() },
                                imageResource = R.drawable.list,
                                text = "Directory".uppercase(),
                                contentDescription = "View List Button",
                                modifier = Modifier.fillMaxWidth(),
                                enabled = true
                            )
                        },
                        selected = false,
                        onClick = { }
                    )
                    NavigationDrawerItem(
                        label = {
                            PrimaryButton(
                                onClick = { navViewModel.navigateToHome();navViewModel.navigateToNurseList() },
                                imageResource = R.drawable.search,
                                text = "Search".uppercase(),
                                contentDescription = "Find by Name Button",
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false
                            )
                        },
                        selected = false,
                        onClick = { }
                    )
                    Spacer(Modifier.height(16.dp))
                }

                HorizontalDivider()

                DropdownMenuItem(
                    text = { Text("Profile") },
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Settings") },
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )

                HorizontalDivider()

                // Second section
                DropdownMenuItem(
                    text = { Text("Send Feedback") },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Send, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )

                HorizontalDivider()

                DropdownMenuItem(
                    text = { Text("Help") },
                    leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                    trailingIcon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Do something... */ }
                )

                // Third section
                DropdownMenuItem(
                    text = { Text("About") },
                    leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )

                HorizontalDivider(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = {
                        LogoutButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
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
                CenterAlignedTopAppBar(
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
                                contentDescription = "Configuration"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navViewModel.navigateBack() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Configuration"
                            )
                        }
                    },
                    colors = TopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(70.dp),
                    windowInsets = BottomAppBarDefaults.windowInsets,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SingleChoiceSegmentedButton()
                    }
                }
            },
            floatingActionButton = { actionButton() },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    screenContent()
                }
            }
        )
    }
}

@Composable
fun SingleChoiceSegmentedButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(2) }
    val options = listOf("User", "Shop", "Home", "Game", "News")

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                icon = { },
                shape = ShapeDefaults.ExtraLarge,
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                label = { Text(label.uppercase()) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.background,
                    activeContentColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = MaterialTheme.colorScheme.primary,
                    inactiveContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = BorderStroke(
                    1.dp,
                    if (index == selectedIndex) MaterialTheme.colorScheme.primary else Color.Transparent
                )
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
        Text(title)
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
                fontSize = 22.sp
            )
            Text(
                text = nurse.age.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Birth Date: ",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.birthDate),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Email: ",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Register Date: ",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.registerDate),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 22.sp
            )
        }
    }
}

