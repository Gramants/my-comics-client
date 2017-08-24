package com.stefano.marvelclient.ui;

import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.data.repository.ComicsRepository;
import com.stefano.marvelclient.ui.comics.ComicsContract;
import com.stefano.marvelclient.ui.comics.ComicsPresenter;
import com.stefano.marvelclient.util.schedulers.RunOn;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.TestScheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.stefano.marvelclient.util.schedulers.SchedulerType.COMPUTATION;
import static com.stefano.marvelclient.util.schedulers.SchedulerType.UI;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ComicDetailPresenterTest {
  private static final ComicItemAdapter comic1 = new ComicItemAdapter(1,"b","c","d",1.0,"e","f");
  private static final ComicItemAdapter comic2 = new ComicItemAdapter(2,"b","c","d",1.0,"e","f");;
  private static final ComicItemAdapter comic3 = new ComicItemAdapter(3,"b","c","d",1.0,"e","f");
  private List<ComicItemAdapter> comiclist = Arrays.asList(comic1, comic2, comic3);

  @Mock
  private ComicsRepository repository;

  @Mock
  private ComicsContract.View view;

  @RunOn(COMPUTATION)
  private Scheduler computationScheduler;

  @RunOn(UI)
  private Scheduler uiScheduler;

  private TestScheduler testScheduler;

  private ComicsPresenter presenter;

  @Before
  public void setUp() {
    // Make sure to use TestScheduler for RxJava testing
    testScheduler = new TestScheduler();
    computationScheduler = testScheduler;
    uiScheduler = testScheduler;
    presenter = new ComicsPresenter(repository, view, computationScheduler, uiScheduler);



  }

  @Test
  public void loadComics_FromRepoToView_WithDataReturned() {
    doReturn(Observable.just(comiclist)).when(repository).loadComics(true);
    presenter.loadComics(true);
    testScheduler.triggerActions(); // Trigger actions for test scheduler

    verify(view).clearComics();
    verify(view).showComics(comiclist);
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }

  @Test
  public void loadComics_FromRepoToView_WithNoDataReturned() {
    doReturn(Observable.just(new ArrayList<ComicItemAdapter>())).when(repository).loadComics(true);
    presenter.loadComics(true);
    testScheduler.triggerActions(); // Trigger actions for test scheduler

    verify(view).clearComics();
    verify(view, never()).showComics(comiclist);
    verify(view).showNoDataMessage();
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }
}
