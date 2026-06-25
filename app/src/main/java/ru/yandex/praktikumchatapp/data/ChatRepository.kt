package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import ru.yandex.praktikumchatapp.utils.Logger
import kotlin.math.min
import kotlin.random.Random

class ChatRepository(
    private val api: ChatApi = ChatApi(),
    private val logger: Logger = Logger()
) {

    fun getReplyMessage(): Flow<String> {
        return api
            .getReply()
            .retryWhen { cause, attempt ->
                // [Задание 2] добавьте обработку ошибок
                logger.e("ChatRepository", "getReplyMessage error attempt=$attempt, cause: $cause")
                if (cause is Exception) {
                    countDelayMs(attempt).let {
                        logger.d("ChatRepository", "countDelayMs result delay ${it}ms=${it/1000}s")
                        delay(it)
                    }
                    true
                } else {
                    false
                }
            }
    }

    /**
     * Вычисляем величину delay (с линейным увеличением) в мс
     *
     * @param attempt номер попытки
     * @param maxDelayMs максимальная величина delay (1 минута по-дефолту)
     */
    private fun countDelayMs(attempt: Long, maxDelayMs: Long = 60_000): Long {
        val linDelay = 2_000 * attempt
        val jitter = if (linDelay == 0L) 0L else Random.nextLong(0, linDelay)
        return min(1_000 + jitter, maxDelayMs)
    }

}