package bitcoin.base.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveResultTest {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private val owner = mock<LifecycleOwner>()
    private val registry = LifecycleRegistry(owner)

    private val liveResult = MutableLiveResult<String>()

    private val mockedStart = mock<() -> Unit>()
    private val mockedValue = mock<(String) -> Unit>()
    private val mockedError = mock<(Int) -> Unit>()
    private val mockedComplete = mock<() -> Unit>()

    @Before
    fun setup() {
        whenever(owner.lifecycle).thenReturn(registry)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun shouldCallOnStart() {
        // GIVEN
        liveResult.value = Result.start()

        // WHEN
        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedStart)()
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedError)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun shouldCallOnValue() {
        // GIVEN
        val value = "xyz"
        liveResult.value = Result.value(value)

        // WHEN
        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedValue)(value)
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedError)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun shouldCallOnError() {
        // GIVEN
        val errorMsg = android.R.string.ok
        liveResult.value = Result.error(errorMsg)

        // WHEN
        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedError)(errorMsg)
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun shouldCallOnComplete() {
        // GIVEN
        liveResult.value = Result.complete()

        // WHEN
        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedComplete)()
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedError)
    }
}
