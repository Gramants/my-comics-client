package com.stefano.marvelclient.data.repository;

import com.stefano.marvelclient.data.model.ComicItemAdapter;

import java.util.List;

import rx.Observable;

public interface ComicsDataSource {

  void addComic(ComicItemAdapter comic);

  void clearData();

  Observable<List<ComicItemAdapter>> loadComics(boolean forceRemote);

  Observable<List<ComicItemAdapter>> loadComicsInMoneyRangeFromDB(int maxmoney);
}
