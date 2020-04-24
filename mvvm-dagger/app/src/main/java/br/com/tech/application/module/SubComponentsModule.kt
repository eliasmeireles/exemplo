package br.com.tech.application.module

import br.com.tech.application.componet.MainActivityComponent
import dagger.Module

@Module(subcomponents = [MainActivityComponent::class])
class SubComponentsModule