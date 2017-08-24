package com.stefano.marvelclient.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.stefano.marvelclient.data.Config;
import com.stefano.marvelclient.data.model.ComicItemAdapter;

import java.util.List;

/**
 * @author Stefano
 */
@Dao
public interface ComicDao {
  @Query("SELECT * FROM " + Config.COMICS_TABLE)
  List<ComicItemAdapter> getAllComics();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(ComicItemAdapter result);

  @Query("DELETE FROM " + Config.COMICS_TABLE)
  void deleteAll();

}
