package com.hp77.linkstash.di

import android.content.Context
import androidx.room.Room
import com.hp77.linkstash.data.local.LinkStashDatabase
import com.hp77.linkstash.data.local.dao.LinkDao
import com.hp77.linkstash.data.local.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): LinkStashDatabase {
        return Room.databaseBuilder(
            context,
            LinkStashDatabase::class.java,
            LinkStashDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLinkDao(database: LinkStashDatabase): LinkDao {
        return database.linkDao()
    }

    @Provides
    @Singleton
    fun provideTagDao(database: LinkStashDatabase): TagDao {
        return database.tagDao()
    }
}