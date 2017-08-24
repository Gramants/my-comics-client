package com.stefano.marvelclient.data.repository;

import com.stefano.marvelclient.data.model.ComicItemAdapter;


import java.util.List;
import javax.inject.Inject;


import rx.Observable;
import rx.functions.Action1;

import static rx.Observable.just;


public class ComicsRepository implements ComicsDataSource {

  private ComicsDataSource remoteDataSource;
  private ComicsDataSource localDataSource;

  @Inject
  public ComicsRepository(@Local ComicsDataSource localDataSource,
                          @Remote ComicsDataSource remoteDataSource) {
    this.localDataSource = localDataSource;
    this.remoteDataSource = remoteDataSource;
  }



  @Override
  public void addComic(ComicItemAdapter comic) {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public void clearData() {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  // A helper method to save data in database after fetching new data from remote source.
  private void saveDataToLocal(List<ComicItemAdapter> listcomic) {
    // Clear old data
    localDataSource.clearData();
    for (ComicItemAdapter comic : listcomic) {
      localDataSource.addComic(comic);
    }
  }

  @Override
  public Observable<List<ComicItemAdapter>> loadComics(boolean forceRemote) {
    Observable<List<ComicItemAdapter>> comics;
    if (forceRemote) {
      comics = remoteDataSource.loadComics(true).doOnNext(new Action1<List<ComicItemAdapter>>() {

        public void call(List<ComicItemAdapter> list) {
          if (list != null && !list.isEmpty()) {
            saveDataToLocal(list);
          }
        }

      });
    } else {
        // Stream the same observable from the db. using Flowable and Rxjava2 will be better so not to have the db ops on the main thread
      comics = localDataSource.loadComics(false);
    }
    return comics;
  }

    @Override
    public Observable<List<ComicItemAdapter>> loadComicsInMoneyRangeFromDB(int maxmoney) {
        return localDataSource.loadComicsInMoneyRangeFromDB(maxmoney);
    }


}
