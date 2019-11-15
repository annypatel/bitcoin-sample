package bitcoin.base.data.network

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Wrapper around [Retrofit] client.
 */
@Singleton
class NetworkClient @Inject constructor(
    private val retrofit: Retrofit
) {

    inline fun <reified T : Any> create() = create(T::class)

    fun <T : Any> create(serviceClass: KClass<T>): T {
        return retrofit.create(serviceClass.java)
    }
}
