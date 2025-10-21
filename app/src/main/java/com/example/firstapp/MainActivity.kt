package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                SurveyScreen()
            }
        }
    }
}

@Composable
fun SurveyScreen() {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableFloatStateOf(25f) }
    var selectedGender by remember { mutableStateOf("Мужской") }
    var isSubscribed by remember { mutableStateOf(false) }
    var summaryText by remember { mutableStateOf("") }


    val isNameValid = name.isNotBlank()
    val buttonEnabled = isNameValid


    val labelName = "Имя"
    val errorName = "Введите имя"
    val labelAge = "Возраст"
    val genderMale = "Мужской"
    val genderFemale = "Женский"
    val checkboxText = "Хочу получать новости"
    val buttonSend = "Отправить"
    val labelSubscription = "Подписка"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

//        Image(
//            painter = painterResource(id = DEFAULT_AVATAR_RES_ID),
//            contentDescription = "Логотип приложения",
//            modifier = Modifier
//                .size(96.dp)
//                .clip(CircleShape)
//                .padding(bottom = 24.dp)
//        )


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(labelName) },
            isError = !isNameValid && name.isNotEmpty(),
            supportingText = {
                if (!isNameValid && name.isNotEmpty()) {
                    Text(errorName, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )


        Text(text = "$labelAge: ${age.toInt()}", modifier = Modifier.fillMaxWidth())
        Slider(
            value = age,
            onValueChange = { age = it },
            valueRange = 1f..100f,
            steps = 99,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GenderRadioButton(
                label = genderMale,
                isSelected = selectedGender == genderMale,
                onClick = { selectedGender = genderMale }
            )
            Spacer(Modifier.width(16.dp))
            GenderRadioButton(
                label = genderFemale,
                isSelected = selectedGender == genderFemale,
                onClick = { selectedGender = genderFemale }
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = isSubscribed,
                    onClick = { isSubscribed = !isSubscribed }
                )
                .padding(vertical = 8.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSubscribed,
                onCheckedChange = { isSubscribed = it }
            )
            Spacer(Modifier.width(8.dp))
            Text(checkboxText)
        }


        Button(
            onClick = {
                val subscriptionStatus = if (isSubscribed) "да" else "нет"
                summaryText = "Имя: $name\nВозраст: ${age.toInt()}\nПол: $selectedGender\n$labelSubscription: $subscriptionStatus"
            },
            enabled = buttonEnabled,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(buttonSend)
        }

        if (summaryText.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Сводка данных:\n$summaryText",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun GenderRadioButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = onClick
        )
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Spacer(Modifier.width(4.dp))
        Text(label)
    }
}


@Preview(showBackground = true, name = "Light Theme")
@Composable
fun SurveyScreenPreviewLight() {
    FirstAppTheme(darkTheme = false) {
        SurveyScreen()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun SurveyScreenPreviewDark() {
    FirstAppTheme(darkTheme = true) {
        SurveyScreen()
    }
}