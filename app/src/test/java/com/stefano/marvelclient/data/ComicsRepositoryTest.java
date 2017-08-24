package com.stefano.marvelclient.data;

import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.data.repository.ComicsDataSource;
import com.stefano.marvelclient.data.repository.ComicsRepository;
import com.stefano.marvelclient.data.repository.Local;
import com.stefano.marvelclient.data.repository.Remote;

import rx.Observable;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Stefano
 */
@RunWith(MockitoJUnitRunner.class)
public class ComicsRepositoryTest {

  private static final ComicItemAdapter comic1 = new ComicItemAdapter(1,"b","c","d",1.0,"e","f");
  private static final ComicItemAdapter comic2 = new ComicItemAdapter(2,"b","c","d",1.0,"e","f");;
  private static final ComicItemAdapter comic3 = new ComicItemAdapter(3,"b","c","d",1.0,"e","f");
  private List<ComicItemAdapter> comiclist = Arrays.asList(comic1, comic2, comic3);

  @Mock
  @Local
  private ComicsDataSource localDataSource;

  @Mock
  @Remote
  private ComicsDataSource remoteDataSource;

  private ComicsRepository repository;

  @Before
  public void setup() {
    repository = new ComicsRepository(localDataSource, remoteDataSource);
  }

  @Test
  public void loadComics_FromLocal() {
    repository.loadComics(false);

    verify(localDataSource).loadComics(false);
    verify(remoteDataSource, never()).loadComics(true);
  }

  @Test
  public void loadComics_FromRemote() {
    doReturn(Observable.just(comiclist)).when(remoteDataSource).loadComics(true);
    repository.loadComics(true);

    verify(remoteDataSource).loadComics(true);
    verify(localDataSource, never()).loadComics(false);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addComic_ShouldThrowException() {
    repository.addComic(comic1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void clearData_ShouldThrowException() {
    repository.clearData();
  }
}
