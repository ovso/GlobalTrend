package io.github.ovso.globaltrend.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainActivityModule {

  @Provides
  @ActivityRetainedScoped
  fun provideMainRepository(): String {
    return "HelloWorld"
  }

}
