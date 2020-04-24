package br.com.tech.application

import android.app.Application
import br.com.tech.application.componet.ApplicationComponent
import br.com.tech.application.componet.DaggerApplicationComponent

class BaseApplication : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}