package ru.yandex.praktikumchatapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yandex.praktikumchatapp.data.ChatRepository

class ChatViewModel(
    val isWithReplies: Boolean = true
) : ViewModel() {

    private val repository = ChatRepository()

    // [Задание 1] замена на Flow
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    // TODO Задание 3: добавьте состояние shouldShowKeyboard

    // TODO Задание 4: замените messages и shouldShowKeyboard на state

    init {
        viewModelScope.launch {
            while (isWithReplies) {
                repository.getReplyMessage().collect { response ->
                    updateState(Message.OtherMessage(response))
                }
            }
        }
    }

    fun sendMyMessage(messageText: String) {
        updateState(Message.MyMessage(messageText))
    }


    /**
     * [Задание 1] для метода sendMessage
     *
     * Обновляем стейт _messages, добавляя новое сообщение в список
     */
    private fun updateState(message: Message) {
        _messages.update { it + message }
        Log.i("ChatViewModel", "updateState with $message. Result: ${_messages.value.joinToString()}~")
    }
}