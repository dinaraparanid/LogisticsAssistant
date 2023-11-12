package com.paranid5.biatestapp.presentation.main.tasks

import com.paranid5.biatestapp.data.retrofit.tasks.Contact
import com.paranid5.biatestapp.data.retrofit.tasks.Destination
import com.paranid5.biatestapp.data.retrofit.tasks.OrderDateTime
import com.paranid5.biatestapp.data.retrofit.tasks.Task

val incomingTasksPlaceholder
    get() = listOf(
        sampleTask,
        sampleTask.copy(
            taskId = 1,
            title = "Задание № 002",
            price = "32 000, 00 ₽",
            orderDateTime = OrderDateTime(
                date = "11.08.2023",
                time = "10:00"
            ),
            destinationPoints = listOf(
                Destination(
                    destinationTaskId = 4,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, въезд космонавтов, 96",
                    loadingTime = OrderDateTime(
                        date = "12.08.2023",
                        time = "10:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
                Destination(
                    destinationTaskId = 5,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, пр-кт К. Маркса, 91",
                    loadingTime = OrderDateTime(
                        date = "12.08.2023",
                        time = "12:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
                Destination(
                    destinationTaskId = 6,
                    taskType = "Выгрузка",
                    startLocation = "Москва, Московская область, спуск Косиора, 32",
                    loadingTime = OrderDateTime(
                        date = "13.08.2023",
                        time = "14:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                )
            ),
        ),
        sampleTask.copy(
            taskId = 1,
            title = "Задание № 002",
            price = "20 000, 00 ₽",
            orderDateTime = OrderDateTime(
                date = "11.08.2023",
                time = "09:00"
            ),
            destinationPoints = listOf(
                Destination(
                    destinationTaskId = 7,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, проезд Ладыгина, 44",
                    loadingTime = OrderDateTime(
                        date = "12.08.2023",
                        time = "07:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
                Destination(
                    destinationTaskId = 8,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, проезд Балканская, 20",
                    loadingTime = OrderDateTime(
                        date = "13.08.2023",
                        time = "14:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
            ),
        ),
        sampleTask.copy(
            taskId = 1,
            title = "Задание № 001",
            price = "64 000, 00 ₽",
            orderDateTime = OrderDateTime(
                date = "11.08.2023",
                time = "08:00"
            ),
            destinationPoints = listOf(
                Destination(
                    destinationTaskId = 7,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, проезд Ладыгина, 44",
                    loadingTime = OrderDateTime(
                        date = "12.08.2023",
                        time = "07:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
                Destination(
                    destinationTaskId = 8,
                    taskType = "Погрузка",
                    startLocation = "Москва, Московская область, проезд Балканская, 20",
                    loadingTime = OrderDateTime(
                        date = "13.08.2023",
                        time = "14:00"
                    ),
                    company = "ООО \"Компания\"",
                    contact = Contact(
                        contactPerson = "Линус Торвальдс Бенедикт",
                        phoneNumber = "+7 923 257 4113"
                    ),
                    commentary = "Подъедете заранее, примерно за 15 минут",
                ),
            ),
        )
    )

val executingTasksPlaceholder: List<Task>
    get() {
        val (first, second, third, fourth) = incomingTasksPlaceholder
        return listOf(
            first.copy(status = "Запланированные"),
            second.copy(status = "В процессе"),
            third.copy(status = "Проверка"),
            fourth.copy(status = "Проверка")
        )
    }

private inline val sampleTask
    get() = Task(
        taskId = 0,
        title = "Задание № 003",
        status = "Новое",
        price = "30 000, 00 ₽",
        cargoType = "Замороженные полуфабрикаты",
        taskCity = "Москва",
        orderDateTime = OrderDateTime(
            date = "11.08.2023",
            time = "12:00"
        ),
        carBodyType = "Тентованный",
        cargoWeight = "20 000 кг",
        loadCapacity = "20 тонн",
        loadingType = "Задняя",
        medicalBook = true,
        orderDetails = "Прописанные детали заказа",
        contacts = listOf(
            Contact(
                contactPerson = "Иванов Владимир Иосифович",
                phoneNumber = "+7 800 896 52 63"
            )
        ),
        destinationPoints = listOf(
            Destination(
                destinationTaskId = 0,
                taskType = "Погрузка",
                startLocation = "Москва, Московская область, машиностроительная улица, 91",
                loadingTime = OrderDateTime(
                    date = "12.08.2023",
                    time = "12:00"
                ),
                company = "ООО \"Компания\"",
                contact = Contact(
                    contactPerson = "Линус Торвальдс Бенедикт",
                    phoneNumber = "+7 923 257 4113"
                ),
                commentary = "Подъедете заранее, примерно за 15 минут",
            ),
            Destination(
                destinationTaskId = 1,
                taskType = "Погрузка",
                startLocation = "Москва, Московская область, пр-кт К. Маркса, 91",
                loadingTime = OrderDateTime(
                    date = "12.08.2023",
                    time = "12:00"
                ),
                company = "ООО \"Компания\"",
                contact = Contact(
                    contactPerson = "Линус Торвальдс Бенедикт",
                    phoneNumber = "+7 923 257 4113"
                ),
                commentary = "Подъедете заранее, примерно за 15 минут",
            ),
            Destination(
                destinationTaskId = 2,
                taskType = "Погрузка",
                startLocation = "Москва, Московская область, пр-кт К. Маркса, 91",
                loadingTime = OrderDateTime(
                    date = "12.08.2023",
                    time = "12:00"
                ),
                company = "ООО \"Компания\"",
                contact = Contact(
                    contactPerson = "Линус Торвальдс Бенедикт",
                    phoneNumber = "+7 923 257 4113"
                ),
                commentary = "Подъедете заранее, примерно за 15 минут",
            ),
            Destination(
                destinationTaskId = 3,
                taskType = "Выгрузка",
                startLocation = "Москва, Московская область, магистральная улица, 52",
                loadingTime = OrderDateTime(
                    date = "13.08.2023",
                    time = "13:00"
                ),
                company = "ООО \"Компания\"",
                contact = Contact(
                    contactPerson = "Линус Торвальдс Бенедикт",
                    phoneNumber = "+7 923 257 4113"
                ),
                commentary = "Подъедете заранее, примерно за 15 минут",
            )
        ),
        clientRulesUrl = "https://www.evg-online.org/fileadmin/user_upload/22-11-02-Bebra-Foto.jpg"
    )