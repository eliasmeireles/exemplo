package br.com.tech.application.componet

import br.com.tech.ui.activity.MainActivity
import dagger.Subcomponent

@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}