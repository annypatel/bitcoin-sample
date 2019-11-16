package bitcoin.test.domain

import bitcoin.base.domain.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * [RxSchedulers] for unit tests.
 */
fun testRxSchedulers(
    main: Scheduler = Schedulers.trampoline(),
    io: Scheduler = Schedulers.trampoline(),
    computation: Scheduler = Schedulers.trampoline()
) = RxSchedulers(main, io, computation)
