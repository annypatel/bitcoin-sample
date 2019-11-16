package bitcoin.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Factory for creating [ViewModel]s.
 */
class ViewModelFactory @Inject constructor(
    private val providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var provider = providers[modelClass]
        if (provider == null) {
            for ((key, value) in providers) {
                if (modelClass.isAssignableFrom(key)) {
                    provider = value
                    break
                }
            }
        }

        checkNotNull(provider) { "unknown model class $modelClass" }

        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}
