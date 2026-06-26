package ru.yandex.praktikumchatapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yandex.praktikumchatapp.data.ChatRepository
import ru.yandex.praktikumchatapp.utils.Logger

class ChatViewModel(
    private val isWithReplies: Boolean = true,
    private val logger: Logger = Logger()
) : ViewModel() {

    private val repository = ChatRepository(logger = logger)

    // [Задание 1] замена на Flow
    // [Задание 3] добавьте состояние shouldShowKeyboard
    // [Задание 4] замените messages и shouldShowKeyboard на state
    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    // [Задание 4] индикатор, первое сообщение или нет, в дальнейшем можно убрать и унифицировать поведение для всех сообщений
    private var isFirstIncoming = true


    init {
        viewModelScope.launch {
            while (isWithReplies) {
                repository.getReplyMessage().collect { response ->
                    updateState(Message.OtherMessage(response), shouldShowKeyboard = isFirstIncoming)
                    isFirstIncoming = false
                }
            }
        }
    }

    fun sendMyMessage(messageText: String) {
        updateState(Message.MyMessage(messageText), shouldShowKeyboard = false)
    }


    /**
     * [Задание 1] для метода sendMessage
     * [Задание 4] замена на [ChatState]
     *
     * Обновляем стейт _messages, добавляя новое сообщение в список
     */
    private fun updateState(message: Message? = null, shouldShowKeyboard: Boolean? = null) {
        logger.i("ChatViewModel", "updateState with (message=$message, shouldShowKeyboard=$shouldShowKeyboard)~")
        _state.update {
            it.copy(
                messages = if (message != null) it.messages + message else it.messages,
                shouldShowKeyboard = shouldShowKeyboard ?: it.shouldShowKeyboard
            )
        }
    }
}
