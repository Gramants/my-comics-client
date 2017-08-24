package com.stefano.marvelclient.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.stefano.marvelclient.data.database.ComicDao;
import com.stefano.marvelclient.data.database.MarvelComicsDb;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Stefano
 */

@Module
public class DatabaseModule {
  private static final String DATABASE = "test";

  @Provides
  @Named(DATABASE)
  String provideDatabaseName() {
    return Config.DATABASE_NAME;
  }

  @Provides
  @Singleton
  MarvelComicsDb provideMarvelComicDao(Context context, @Named(DATABASE) String databaseName) {
    return Room.databaseBuilder(context, MarvelComicsDb.class, databaseName).allowMainThreadQueries().build();
  }

  @Provides
  @Singleton
  ComicDao provideComicDao(MarvelComicsDb marvelComicsDb) {
    return marvelComicsDb.comicDao();
  }
}
