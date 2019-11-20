package bitcoin.test.ui

import okreplay.OkReplayInterceptor

/**
 * Interface to obtain [OkReplayInterceptor] from dagger application component.
 */
interface OkReplayInterceptorProvider {
    val interceptor: OkReplayInterceptor
}
