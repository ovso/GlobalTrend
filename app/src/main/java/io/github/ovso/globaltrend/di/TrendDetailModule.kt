package io.github.ovso.globaltrend.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.ovso.globaltrend.view.ui.detail.TrendDetailAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrendDetailModule {

  @Provides
  @Singleton
  fun provideTrendDetailAdapter(): TrendDetailAdapter = TrendDetailAdapter()
}
