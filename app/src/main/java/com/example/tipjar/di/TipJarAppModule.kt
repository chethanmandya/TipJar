package com.example.tipjar.di

import android.app.Application
import android.content.Context
import com.example.tipjar.database.dao.TipJarDao
import com.example.tipjar.model.Calculator
import com.example.tipjar.model.TipCalculator
import com.example.tipjar.repository.TipJarDataRepository
import com.example.tipjar.repository.TipJarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class TipJarAppModule {

    @Singleton
    @Provides
    fun providesContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providesTipCalculationRepository(
        tipJarDao: TipJarDao
    ): TipJarRepository {
        return TipJarDataRepository(tipJarDao)
    }

    @Provides
    @Singleton
    fun providesTipCalculator(
        tipJarRepository: TipJarRepository
    ): Calculator {
        return TipCalculator(tipJarRepository)
    }
}
