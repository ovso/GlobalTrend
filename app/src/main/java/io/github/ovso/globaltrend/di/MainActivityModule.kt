package io.github.ovso.globaltrend.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object MainActivityModule {

  @Provides
  fun provideMainRepository(): String {
    return "HelloWorld"
  }

}
