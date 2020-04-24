package br.com.tech.application.componet

import br.com.tech.application.module.RetrofitModule
import br.com.tech.application.module.SubComponentsModule
import br.com.tech.application.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        SubComponentsModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun mainActivityComponent(): MainActivityComponent.Factory
}