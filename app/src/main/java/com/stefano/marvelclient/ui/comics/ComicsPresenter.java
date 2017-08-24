package com.stefano.marvelclient.ui.comics;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.data.repository.ComicsRepository;
import com.stefano.marvelclient.util.schedulers.RunOn;

import java.util.List;
import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;

import static com.stefano.marvelclient.util.schedulers.SchedulerType.*;

/**
 * A presenter with life-cycle aware.
 *
 * @author Stefano
 */
public class ComicsPresenter implements ComicsContract.Presenter, LifecycleObserver {

  private ComicsRepository repository;

  private ComicsContract.View view;

  private List<ComicItemAdapter> caches;

  private Scheduler computationScheduler;
  private Scheduler uiScheduler;

  @Inject
  public ComicsPresenter(ComicsRepository repository, ComicsContract.View view,
                         @RunOn(COMPUTATION) Scheduler computationScheduler, @RunOn(UI) Scheduler uiScheduler) {
    this.repository = repository;
    this.view = view;
    this.computationScheduler = computationScheduler;
    this.uiScheduler = uiScheduler;
    // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
    if (view instanceof LifecycleOwner) {
      ((LifecycleOwner) view).getLifecycle().addObserver(this);
    }
  }




    @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  public void onAttach() {

  }

  @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void onDetach() {
    // Clean up your resources here
  }

  @Override
  public void loadComics(boolean onlineRequired) {

    view.clearComics();
    repository.loadComics(onlineRequired)
        .subscribeOn(computationScheduler)
        .observeOn(uiScheduler)
            .subscribe(new Subscriber<List<ComicItemAdapter>>() {
              @Override
              public void onCompleted() {

               view.stopLoadingIndicator();
              }

              @Override
              public void onError(Throwable e) {
                handleError(e);
              }

              @Override
              public void onNext(List<ComicItemAdapter> comiclist) {
                handleReturnedData(comiclist, onlineRequired);
              }
            });
  }





  @Override
  public void getComic(int comicId) {
    // Load comic detail from cache
    if (caches != null && caches.size() != 0) {
      for (ComicItemAdapter comic : caches) {
        if (comic.getId() == comicId) {
          view.showComicDetail(comic);
          break;
        }
      }
    }
  }

  @Override
  public void loadComicsInMoneyRangeFromDB(int maxmoney) {

    view.clearComics();
    // Load new one and populate it into view
    repository.loadComicsInMoneyRangeFromDB(maxmoney)
            .subscribeOn(computationScheduler)
            .observeOn(uiScheduler)
            .subscribe(new Subscriber<List<ComicItemAdapter>>() {
              @Override
              public void onCompleted() {
                view.stopLoadingIndicator();
              }

              @Override
              public void onError(Throwable e) {
                handleError(e);
              }

              @Override
              public void onNext(List<ComicItemAdapter> comiclist) {
                handleReturnedData(comiclist, false);
              }
            });
  }




  /** Private helper methods go here**/

  /**
   * Handles the logic when receiving data from repository.
   * @param list
   * @param onlineRequired
   */
  private void handleReturnedData(List<ComicItemAdapter> list, boolean onlineRequired) {
    view.stopLoadingIndicator();
    // Show on view if returned data is not empty.
    if (list != null && !list.isEmpty()) {
      view.showComics(list);
      caches = list;
    } else {
      // if user requests from local storage and it turns out empty,
      // load again data from remote instead.
      if (!onlineRequired) {
        loadComics(true);
      } else {
        view.showNoDataMessage();
      }
    }
  }

  /**
   * Handle error after loading data from repository.
   * @param error
   */
  private void handleError(Throwable error) {
    view.stopLoadingIndicator();
    view.showErrorMessage(error.getLocalizedMessage());
  }
}
