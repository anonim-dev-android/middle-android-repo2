import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import ru.yandex.praktikumchatapp.presentation.ChatState
import ru.yandex.praktikumchatapp.presentation.ChatViewModel
import ru.yandex.praktikumchatapp.presentation.Message
import ru.yandex.praktikumchatapp.utils.Logger

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    private var testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ChatViewModel
    private val logger = mock<Logger>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ChatViewModel(isWithReplies = false, logger)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `send message should update state with MyMessage`() = runTest {
        val message = Message.MyMessage("TestMessage")

        // [Задание 5] допишите юнит-тест
        viewModel.sendMyMessage(message.text)
        viewModel.state.test {
            val actual = awaitItem()
            val expect = ChatState(messages = listOf(message), false)
            assert(expect == actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testReceiveMessage_concurrentMessages() = runTest {
        val messagesToSend = (1..100).map { Message.MyMessage("Message $it") }

        // TODO Задание 6: допишите юнит-тест
    }
}