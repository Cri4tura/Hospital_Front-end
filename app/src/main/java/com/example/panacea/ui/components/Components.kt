package com.example.panacea.ui.components

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.ui.navigation.DETAIL
import com.example.panacea.ui.navigation.DIRECTORY
import com.example.panacea.ui.navigation.DOCUMENTS
import com.example.panacea.ui.navigation.HISTORY
import com.example.panacea.ui.navigation.HOME
import com.example.panacea.ui.navigation.LOGIN
import com.example.panacea.ui.navigation.NEWS
import com.example.panacea.ui.navigation.PROFILE
import com.example.panacea.data.utils.Constants.MENU
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerAppBar(
    nav: NavHostController,
    userName: String?,
    index: Int,
    pageTitle: @Composable () -> Unit,
    leftActionIcon: @Composable (() -> Unit)? = null,
    rightActionIcon: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
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

                    Image(
                        painter = painterResource(id = R.drawable.nurse_register),
                        contentDescription = "User Image",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(Modifier.height(16.dp))

                    userName?.let {
                        Text(
                            it.uppercase(),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                HorizontalDivider()

                DropdownMenuItem(
                    text = { Text("Profile") },
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    onClick = { nav.navigate(PROFILE) }
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
                    trailingIcon = {
                        Icon(
                            Icons.AutoMirrored.Default.Send,
                            contentDescription = null
                        )
                    },
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
                            onConfirm = { nav.navigate(LOGIN) }
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
                    title = pageTitle,
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
                    actions = { },
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
                    modifier = Modifier.height(60.dp),
                    windowInsets = BottomAppBarDefaults.windowInsets,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SingleChoiceSegmentedButton(
                            initialSelectedIndex = index,
                            onOptionSelected = { selectedOption ->
                                println("Selected: $selectedOption")
                                when (selectedOption) {
                                    "${MENU.OPTION_0}" -> nav.navigate(HISTORY)
                                    "${MENU.OPTION_1}" -> nav.navigate(DIRECTORY)
                                    "${MENU.OPTION_2}" -> nav.navigate(HOME)
                                    "${MENU.OPTION_3}" -> nav.navigate(DOCUMENTS)
                                    "${MENU.OPTION_4}" -> nav.navigate(NEWS)
                                }
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (floatingActionButton != null) {
                    floatingActionButton()
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    screenContent()
                }
            }
        )
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    options: List<String> = listOf("history", "agenda", "home", "docs", "news"),
    initialSelectedIndex: Int,
    onOptionSelected: (String) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                icon = { },
                shape = ShapeDefaults.ExtraLarge,
                onClick = {
                    selectedIndex = index
                    onOptionSelected(index.toString())
                },
                selected = index == selectedIndex,
                label = { Text(label.uppercase(), fontSize = 13.sp) },
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

@Composable
fun EmailInput(
    email: String,
    label: String?,
    onEmailChange: (String) -> Unit,
    isError: String?,
    placeholder: String?
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = {
            if (label != null) {
                Text(label)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        placeholder = {
            if (placeholder != null) {
                Text(placeholder)
            }
        },
        isError = isError?.isNotEmpty() ?: false,
        supportingText = {
            Box(modifier = Modifier.height(16.dp)) {
                if (isError?.isNotEmpty() == true) {
                    Text(
                        text = isError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
    )
}


@Composable
fun searchInput(initialQuery: String): String {
    var searchQuery  by remember { mutableStateOf(initialQuery) }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { query -> searchQuery = query },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                return@KeyboardActions
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true
    )

    return searchQuery
}

@Composable
fun TextInput(
    textInput: String,
    label: String?,
    placeholder: String?,
    onTextChange: (String) -> Unit,
    isError: String?
) {
    OutlinedTextField(
        value = textInput,
        onValueChange = onTextChange,
        placeholder = {
            if (placeholder != null) Text(placeholder)
        },
        label = {
            if (label != null) {
                Text(label)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        isError = isError?.isNotEmpty() ?: false,
        supportingText = {
            Box(modifier = Modifier.height(16.dp)) {
                if (isError?.isNotEmpty() == true) {
                    Text(
                        text = isError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
    )
}

@Composable
fun DateInput(
    value: String,
    label: String?,
    onValueChange: (String) -> Unit,
    isError: String?,
    placeholder: String?
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            if (label != null) {
                Text(label)
            }
        },
        placeholder = {
            if (placeholder != null) {
                Text(placeholder)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        isError = isError?.isNotEmpty() ?: false,
        supportingText = {
            Box(modifier = Modifier.height(16.dp)) {
                if (isError?.isNotEmpty() == true) {
                    Text(
                        text = isError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
    )

    if (showDatePicker) {
        DatePickerModal(
            onDismiss = { showDatePicker = false },
            onDateSelected = { date ->
                onValueChange(dateFormatter.format(date))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDismiss: () -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = datePickerState.selectedDateMillis != null

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(Date(it))
                    }
                    onDismiss()
                },
                enabled = confirmEnabled
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
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

        Icon(
            painter = painterResource(id = R.drawable.huella),
            contentDescription = "Fingerprint Icon",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
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
        supportingText = {
            Box(modifier = Modifier.height(16.dp)) {
                if (isError?.isNotEmpty() == true) {
                    Text(
                        text = isError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                val icon = if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                val description = if (passwordVisible) "Hide password" else "Show password"

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = description,
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    icon: ImageVector?,
    text: String,
    description: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(15.dp),
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                imageVector = icon,
                contentDescription = description,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
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
fun DeleteAccountButton(
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
            imageVector = Icons.Outlined.Delete,
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
    context: Context,
    nurse: Nurse,
    nav: NavHostController
) {
    var isFavorite by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                nav.navigate(DETAIL(nurse.id))
            }
    ) {

        Icon(
            modifier = Modifier
                .padding(4.dp)
                .size(25.dp)
                .clip(CircleShape)
                .clickable {
                    Toast.makeText(context, "Like +1", Toast.LENGTH_SHORT).show()
                    isFavorite = !isFavorite
                },
            imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = lerp(Color.Red, Color.Black, 0.35f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp),
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon"
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
        Spacer(modifier = Modifier.weight(1f))

//        Icon(
//            modifier = Modifier
//                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
//                .padding(4.dp)
//                .size(ButtonDefaults.IconSize)
//                .clip(CircleShape)
//                .clickable { Toast.makeText(context, "Calling...", Toast.LENGTH_SHORT).show() },
//            imageVector = Icons.Outlined.Call,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.primary
//        )
//
//        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .padding(4.dp)
                .size(25.dp)
                .clip(CircleShape)
                .clickable {
                    Toast.makeText(context, "Sending Email...", Toast.LENGTH_SHORT).show()
                },
            imageVector = Icons.Outlined.Email,
            contentDescription = null,
            tint = lerp(Color.Yellow, Color.Black, 0.35f)
        )

//        Spacer(modifier = Modifier.width(8.dp))
//
//        Icon(
//            modifier = Modifier
//                .border(1.dp, MaterialTheme.colorScheme.onError, ButtonDefaults.shape)
//                .padding(4.dp)
//                .size(ButtonDefaults.IconSize)
//                .clip(CircleShape)
//                .clickable {
//                    Toast.makeText(context, "Deleting Contact...", Toast.LENGTH_SHORT).show()
//                },
//            imageVector = Icons.Outlined.Delete,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.onError
//        )

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
fun PasswordResetComponent(
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isError: String?
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            onPasswordChange = { password = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = null
            //isError = vm.passwordError.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInput(
            password = confirmPassword,
            passwordVisible = passwordVisible,
            onPasswordChange = { confirmPassword = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = null
            //isError = vm.passwordError.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword && password.isNotEmpty()) {
                    onSubmit()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = password.isNotEmpty() && confirmPassword.isNotEmpty()
        ) {
            Text("Change Password")
        }
    }
}


@Composable
fun ResetPasswordDialog(
    showPasswordResetDialog: MutableState<Boolean>,
    newPassword: MutableState<String>,
    confirmPassword: MutableState<String>,
    context: Context,
    passwordError: MutableState<String?>
) {
    if (showPasswordResetDialog.value) {
        Dialog(onDismissRequest = { showPasswordResetDialog.value = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                PasswordResetComponent(
                    onPasswordChange = { newPassword.value = it },
                    onConfirmChange = { confirmPassword.value = it },
                    onSubmit = {
                        if (newPassword.value == confirmPassword.value && newPassword.value.isNotEmpty()) {
                            Toast.makeText(
                                context,
                                "Password changed successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            showPasswordResetDialog.value = false
                        } else {
                            passwordError.value = "Passwords do not match"
                        }
                    },
                    isError = passwordError.value
                )
            }
        }
    }
}

@Composable
fun RoundedImagePicker(
    imageUri: Boolean,
    onImageChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .zIndex(0.1f)
            .size(240.dp)
            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape) // Borde de color azul
            .background(Color.Transparent) // Color de fondo por defecto
    ) {
        if (imageUri) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Selected Image",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape) // Asegura que la imagen también sea redonda
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Avatar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f) // Asegura que esté adelante
        ) {
            IconButton(
                onClick = onImageChange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Change Image",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}