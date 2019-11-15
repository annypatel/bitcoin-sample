package bitcoin.base.domain

import io.reactivex.Scheduler

/**
 * RxJava Schedulers for reactive streams.
 */
data class RxSchedulers(
    val main: Scheduler,
    val io: Scheduler,
    val computation: Scheduler
)
