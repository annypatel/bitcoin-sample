package bitcoin.base.data.network

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import retrofit2.Retrofit

class NetworkClientTest {

    interface TestService

    @Test
    fun shouldCreateInstanceOfService() {
        // GIVEN
        val client = NetworkClient(
            Retrofit.Builder()
                .baseUrl("https://bitcoin.com")
                .build()
        )

        // WHEN
        val testService: TestService = client.create()

        // THEN
        assertThat(testService, instanceOf(TestService::class.java))
    }
}
