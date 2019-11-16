package bitcoin.base.ui

import androidx.lifecycle.ViewModel
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import javax.inject.Provider

class ViewModelFactoryTest {

    @Test
    fun shouldReturnViewModel() {
        // GIVEN
        val factory = ViewModelFactory(mapOf(
            IgnoreViewModel::class.java to Provider<ViewModel> { IgnoreViewModel() },
            TestViewModel::class.java to Provider<ViewModel> { TestViewModel() }
        ))

        // WHEN
        val viewModel = factory.create(TestViewModel::class.java)

        // THEN
        assertThat(viewModel, instanceOf(TestViewModel::class.java))
    }

    @Test
    fun shouldReturnAssignableViewModel() {
        // GIVEN
        val factory = ViewModelFactory(mapOf(
            IgnoreViewModel::class.java to Provider<ViewModel> { IgnoreViewModel() },
            ChildTestViewModel::class.java to Provider<ViewModel> { ChildTestViewModel() }
        ))

        // WHEN
        val viewModel = factory.create(TestViewModel::class.java)

        // THEN
        assertThat(viewModel, instanceOf(ChildTestViewModel::class.java))
    }

    @Test
    fun shouldThrowException_whenMapIsEmpty() {
        // GIVEN
        val factory = ViewModelFactory(mapOf())

        try {
            // WHEN
            factory.create(TestViewModel::class.java)
            fail("exception not thrown")
        } catch (e: IllegalStateException) {
            // THEN
            // success
        }
    }

    private open class IgnoreViewModel : ViewModel()
    private open class TestViewModel : ViewModel()
    private class ChildTestViewModel : TestViewModel()
}
