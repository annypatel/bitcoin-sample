# Bitcoin Sample


## Overview

* [Clean Architecture][clean_arch]
* MVVM with [ViewModel][view_model] and [LiveData][live_data]
* [Dagger Android][dagger] for Dependency Injection
* RxJava, RxKotlin and RxAndroid
* Unit Tests using Junit4 and Mockito
* Instrumented UI Tests using [Espresso][espresso], [RxIdler][rxidler] and [OkReplay][okreplay]
* [Detekt][detekt] for static code analysis


## Architecture

The app is architected using **Clean Architecture** with **MVVM**. The app is mainly divided into two modules: **base** and **charts**. The base module contains the stuff that is common across all the features. The charts module contains stuff only related to the displaying charts. There are two more modules **app** and **test**. The app is an installable application module and the test module contains common stuff for unit and instrumentation tests. Again these modules are divided into three sub-modules: **domain**, **data**, and **ui**.

### Domain Layer

The domain sub-module defines the use-cases, repository interfaces, and domain models. Repository interfaces are defined in the domain layer because they serve as an output port to the data layer. Data layer implements these interfaces. For charts feature, **:charts:domain** sub-module defines the following:

#### Usecase
* `GetMarketPrice`
* `GetDurationFromBaseline`
* `DurationToDate`
* `ToDisplayablePrice`

#### Repository
* `BitcoinRepository`

#### Domain Model
* `Price`
* `Interval`
* `BaselinedPrice` & `BaselinedPrices`

### Data Layer

The data sub-module implements repository interfaces from the domain layer. For charts feature, **:charts:data** sub-module defines `BitcoinRepositoryImpl`, which is backed by `BitcoinService`. `BitcoinService` is a retrofit service interface. It will fetch the market price from the remote server.

### UI Layer

The UI sub-module defines everything required for the user interface(Activities, Fragments, etc.) as well as ViewModels. For charts feature, **:charts:ui** sub-module defines `MarketPriceChartFragment`, `PriceMarkerView`, and `MarketPriceChartViewModel`. LiveData is used to observe the result from ViewModel.


## Unit / Instrumentation Tests

Unit tests are written using the **JUnit4** and **Mockito**. Most of the codebase is covered with JVM unit tests except for Fragments and Activities. Fragments and Activities are tested using the **Espresso**. Custome Views are tested with **Robolectric**.

To make espresso tests more hermetic, we need to mock the network API calls. Network API calls are mocked using [OkReplay][okreplay]. Junit test rule for OkReplay required `OkReplayInterceptor`, which is provided by the provision method in `TestAppComponent`, supplied by `OkReplayInterceptorProvider`.

Since all the operations in the app are asynchronous, **Resource Idling** is also required. This is achieved by replacing `RxSchedulersModule` module with `TestRxSchedulersModule` in  `TestAppComponent`. `TestRxSchedulersModule` is test specific dagger module that wraps the RxJava schedulers into `IdlingResourceScheduler` before injecting them into the application component.


[clean_arch]: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
[view_model]: https://developer.android.com/topic/libraries/architecture/viewmodel 
[live_data]: https://developer.android.com/topic/libraries/architecture/livedata
[dagger]: https://google.github.io/dagger/android
[espresso]: https://developer.android.com/training/testing/espresso
[rxidler]: https://github.com/square/RxIdler
[okreplay]: https://github.com/airbnb/okreplay
[detekt]: https://github.com/arturbosch/detekt
