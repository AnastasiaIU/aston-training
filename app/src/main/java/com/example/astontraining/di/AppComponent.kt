package com.example.astontraining.di

import com.example.astontraining.di.modules.RoomDatabaseModule
import com.example.astontraining.view.fragments.BaseFragment
import dagger.Component
import dagger.Module
import javax.inject.Singleton

/**
 * Main graph of the application dependencies.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun roomDatabaseModule(roomDatabaseModule: RoomDatabaseModule): Builder

        fun build(): AppComponent
    }

    /**
     * Injects dependencies at [BaseFragment].
     */
    fun inject(fragment: BaseFragment)
}

/**
 * Main module which includes all other application modules.
 */
@Module(includes = [RoomDatabaseModule::class])
class AppModule
