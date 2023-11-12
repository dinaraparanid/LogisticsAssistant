package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.data.retrofit.tasks.Contact
import com.paranid5.biatestapp.data.retrofit.tasks.Task
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel
import com.paranid5.biatestapp.presentation.ui.theme.BlueLink
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.Divider
import com.paranid5.biatestapp.presentation.ui.utils.ext.toLocalizedYNString
import com.paranid5.biatestapp.presentation.ui.utils.ext.toTimeStringFormat

@Composable
fun FullTaskInfo(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    val task by tasksViewModel.selectedTaskState.collectAsState()

    val isOpenedState = remember {
        mutableStateOf(false)
    }

    val isOpened by isOpenedState

    Card(
        modifier = modifier.animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background)
    ) {
        DropDownRow(
            label = stringResource(id = R.string.task_information),
            isOpenedState = isOpenedState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 19.dp, start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        if (isOpened)
            DetailedDescription(
                task = task!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 19.dp, start = 16.dp, end = 16.dp)
            )
    }
}

@Composable
private fun DetailedDescription(
    task: Task,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        GeneralDescription(
            task = task,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        ContactsList(
            contacts = task.contacts,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        ClientRules(
            clientRulesUrl = task.clientRulesUrl,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Divider(
            width = 72.dp,
            color = colors.onPrimary,
            modifier = Modifier
                .height(2.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun GeneralDescription(
    task: Task,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier) {
        FullDescriptionItem(
            title = stringResource(id = R.string.cargo_type),
            data = task.cargoType,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.task_city),
            data = task.taskCity,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.order_date),
            data = task.orderDateTime.toTimeStringFormat(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.car_body_type),
            data = task.carBodyType,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.cargo_weight),
            data = task.cargoWeight,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.load_capacity),
            data = task.loadCapacity,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.loading_type),
            data = task.loadingType,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.medical_book),
            data = task.medicalBook.toLocalizedYNString(context),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.order_details),
            data = task.orderDetails,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun ContactsList(
    contacts: List<Contact>,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Text(
            text = stringResource(id = R.string.contacts),
            color = colors.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        contacts.forEach {
            ContactView(
                contact = it,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ContactView(
    contact: Contact,
    modifier: Modifier = Modifier
) = Column(modifier) {
    FullDescriptionItem(
        title = stringResource(id = R.string.contact_person),
        data = contact.contactPerson
    )

    Spacer(Modifier.height(12.dp))

    FullDescriptionItem(
        title = stringResource(id = R.string.phone_number),
        data = contact.phoneNumber,
        dataColor = BlueLink
    )
}

@Composable
private fun ClientRules(
    clientRulesUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Text(
            text = stringResource(id = R.string.client_rules),
            color = colors.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = colors.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(clientRulesUrl)
                        }
                    )
                }
        ) {
            Row(
                Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download_icon),
                    contentDescription = stringResource(id = R.string.download),
                    tint = colors.primary,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = stringResource(id = R.string.download),
                    color = colors.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = StolzlFontFamily,
                )
            }
        }
    }
}