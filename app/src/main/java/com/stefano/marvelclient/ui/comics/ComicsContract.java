package com.stefano.marvelclient.ui.comics;


import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.ui.base.BasePresenter;
import java.util.List;


/**
 * @author Stefano
 */
public interface ComicsContract {
  interface View {

    void showComics(List<ComicItemAdapter> comiclist);

    void clearComics();

    void showNoDataMessage();

    void showErrorMessage(String error);

    void showComicDetail(ComicItemAdapter comic);

    void stopLoadingIndicator();
  }

  interface Presenter extends BasePresenter<ComicsContract.View> {
    void loadComics(boolean onlineRequired);
    void getComic(int comicId);
    void loadComicsInMoneyRangeFromDB(int value);
  }
}
