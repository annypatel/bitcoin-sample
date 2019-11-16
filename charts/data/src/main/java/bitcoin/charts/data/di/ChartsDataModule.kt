package bitcoin.charts.data.di

import bitcoin.base.data.network.NetworkClient
import bitcoin.charts.data.BitcoinRepositoryImpl
import bitcoin.charts.data.network.BitcoinService
import bitcoin.charts.domain.BitcoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module to inject data layer dependencies into application component for charts feature.
 */
@Module
abstract class ChartsDataModule {

    @Binds
    abstract fun bitcoinRepository(bitcoinRepository: BitcoinRepositoryImpl): BitcoinRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun bitcoinService(client: NetworkClient): BitcoinService = client.create()
    }
}
