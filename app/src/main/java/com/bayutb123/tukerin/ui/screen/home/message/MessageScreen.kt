package com.bayutb123.tukerin.ui.screen.home.message

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.ui.components.view.MessageList

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier
) {
    val dummyData = listOf(
        Message(
            1,
            "Post Name 1",
            "User Account 1",
            message = "Message 1",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            2,
            "Post Name 2",
            "User Account 2",
            message = "Message 2",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            3,
            "Post Name 3",
            "User Account 3",
            message = "Message 3",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            4,
            "Post Name 4",
            "User Account 4",
            message = "Message 4",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            5,
            "Post Name 5",
            "User Account 5",
            message = "Message 5",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            6,
            "Post Name 6",
            "User Account 6",
            message = "Message 6",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            7,
            "Post Name 7",
            "User Account 7",
            message = "Message 7",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            8,
            "Post Name 8",
            "User Account 8",
            message = "Message 8",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            9,
            "Post Name 9",
            "User Account 9",
            message = "Message 9",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            10,
            "Post Name 10",
            "User Account 10",
            message = "Message 10",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            11,
            "Post Name 11",
            "User Account 11",
            message = "Message 11",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            12,
            "Post Name 12",
            "User Account 12",
            message = "Message 12",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            13,
            "Post Name 13",
            "User Account 13",
            message = "Message 13",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            14,
            "Post Name 14",
            "User Account 14",
            message = "Message 14",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            15,
            "Post Name 15",
            "User Account 15",
            message = "Message 15",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            16,
            "Post Name 16",
            "User Account 16",
            message = "Message 16",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            17,
            "Post Name 17",
            "User Account 17",
            message = "Message 17",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            18,
            "Post Name 18",
            "User Account 18",
            message = "Message 18",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            19,
            "Post Name 19",
            "User Account 19",
            message = "Message 19",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            20,
            "Post Name 20",
            "User Account 20",
            message = "Message 20",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            21,
            "Post Name 21",
            "User Account 21",
            message = "Message 21",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            22,
            "Post Name 22",
            "User Account 22",
            message = "Message 22",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            23,
            "Post Name 23",
            "User Account 23",
            message = "Message 23",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            24,
            "Post Name 24",
            "User Account 24",
            message = "Message 24",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            25,
            "Post Name 25",
            "User Account 25",
            message = "Message 25",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            26,
            "Post Name 26",
            "User Account 26",
            message = "Message 26",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            27,
            "Post Name 27",
            "User Account 27",
            message = "Message 27",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            28,
            "Post Name 28",
            "User Account 28",
            message = "Message 28",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            29,
            "Post Name 29",
            "User Account 29",
            message = "Message 29",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        ),
        Message(
            30,
            "Post Name 30",
            "User Account 30",
            message = "Message 30",
            attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
        )
    )
    MessageList(data = dummyData, onItemClick = {

    })
}