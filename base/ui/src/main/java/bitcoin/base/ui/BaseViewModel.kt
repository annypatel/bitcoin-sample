package bitcoin.base.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ViewModel which exposes a [autoDispose] method on [Disposable], which will be cleared
 * automatically when the ViewModel is destroyed.
 */
open class BaseViewModel : ViewModel() {

    internal val disposables by lazy { CompositeDisposable() }

    /**
     * Adds disposable to [CompositeDisposable], which will be cleared automatically when ViewModel
     * is destroyed.
     */
    protected fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
