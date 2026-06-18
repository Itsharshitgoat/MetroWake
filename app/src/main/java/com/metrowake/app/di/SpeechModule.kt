package com.metrowake.app.di

import android.content.Context
import com.metrowake.app.data.speech.SpeechEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpeechModule {

    @Provides
    @Singleton
    fun provideSpeechEngine(@ApplicationContext context: Context): SpeechEngine {
        return SpeechEngine(context)
    }
}
