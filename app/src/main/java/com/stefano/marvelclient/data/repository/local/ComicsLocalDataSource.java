package com.stefano.marvelclient.data.repository.local;

import com.stefano.marvelclient.data.database.ComicDao;
import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.data.repository.ComicsDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static rx.Observable.just;

/**
 * @author Stefano
 */
public class ComicsLocalDataSource implements ComicsDataSource {

    private ComicDao comicDao;

    @Inject
    public ComicsLocalDataSource(ComicDao comicDao) {
        this.comicDao = comicDao;
    }


    @Override
    public void addComic(ComicItemAdapter comic) {
        // Insert new one
        comicDao.insert(comic);
    }

    @Override
    public void clearData() {
        // Clear old data
        comicDao.deleteAll();
    }

    @Override
    public Observable<List<ComicItemAdapter>> loadComics(boolean forceRemote) {
        return just(comicDao.getAllComics());
    }

    @Override
    public Observable<List<ComicItemAdapter>> loadComicsInMoneyRangeFromDB(int maxmoney) {
        List<ComicItemAdapter> filteredlist = new ArrayList<ComicItemAdapter>();
        double total = 0.0;
        for (ComicItemAdapter comic : comicDao.getAllComics()) {
            total = total + comic.getPrice();
            if (total <= maxmoney) {
                filteredlist.add(comic);
            }
        }

        return just(filteredlist);
    }


}
