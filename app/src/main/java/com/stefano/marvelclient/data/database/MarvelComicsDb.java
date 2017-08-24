package com.stefano.marvelclient.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.stefano.marvelclient.data.model.ComicItemAdapter;

/**
 * @author Stefano
 */
@Database(entities = ComicItemAdapter.class, version = 1)
public abstract class MarvelComicsDb extends RoomDatabase {

  public abstract ComicDao comicDao();
}
