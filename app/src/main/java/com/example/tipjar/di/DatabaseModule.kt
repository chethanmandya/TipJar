package com.example.tipjar.di

import android.app.Application
import androidx.room.Room
import com.example.tipjar.constants.Constants
import com.example.tipjar.database.TipDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): TipDatabase = Room.databaseBuilder(
        app,
        TipDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCacheDao(db: TipDatabase) = db.tipJarDao()
}
