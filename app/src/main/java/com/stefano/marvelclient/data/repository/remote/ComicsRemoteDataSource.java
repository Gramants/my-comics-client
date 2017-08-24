package com.stefano.marvelclient.data.repository.remote;

import com.stefano.marvelclient.data.api.MarvelComicsService;
import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.data.model.ComicsResponse;
import com.stefano.marvelclient.data.model.Result;
import com.stefano.marvelclient.data.repository.ComicsDataSource;
import com.stefano.marvelclient.util.RxUtils;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.List;
import javax.inject.Inject;

/**
 * @author Stefano
 */
public class ComicsRemoteDataSource implements ComicsDataSource {

  private MarvelComicsService marvelComicsService;
    private static final int OFFSET = 0;
    private static final int LIMIT = 100;

  @Inject
  public ComicsRemoteDataSource(MarvelComicsService marvelComicsService) {
    this.marvelComicsService = marvelComicsService;
  }


    @Override
    public void addComic(ComicItemAdapter comic) {
        // not used remotely
    }

    @Override
    public void clearData() {
        // not used remotely
    }

    @Override
  public Observable<List<ComicItemAdapter>> loadComics(boolean forceRemote) {

          return marvelComicsService.getComicsFromNetwork(0,100)

//https://codedump.io/share/NWLRK2x09eDa/1/rxjava-how-to-convert-list-of-objects-to-list-of-another-objects

                  .map(new Func1<ComicsResponse, List<Result>>() {
                      @Override
                      public List<Result> call(ComicsResponse comicsResponse) {
                         return comicsResponse.getData().getResults();
                      }
                  })

                  .scan(new Func2<List<Result>, List<Result>, List<Result>>() {
                      @Override
                      public List<Result> call(List<Result> results, List<Result> newResults) {
                          results.addAll(newResults);
                          return results;
                      }
                  }).flatMap(new Func1<List<Result>, Observable<List<ComicItemAdapter>>>() {
                      @Override
                      public Observable<List<ComicItemAdapter>> call(List<Result> results) {
                          return Observable.from(results)
                                  .map(new Func1<Result, ComicItemAdapter>() {
                                      @Override
                                      public ComicItemAdapter call(Result result) {

                                        Double price=0.0;
                                        String author="";
                                          if (result.getCreators() != null) {
                                              if (result.getCreators().getItems()!=null)
                                                  if (result.getCreators().getItems().size()>0)
                                                             author = result.getCreators().getItems().get(0).getName();
                                           }
                                          if (result.getPrices() != null) {
                                              price = result.getPrices().get(0).getPrice();
                                          }

                                          return new ComicItemAdapter(
                                                  result.getId(),
                                                  result.getTitle(),
                                                  result.getFormat(),
                                                  author,
                                                  price,
                                                  result.getThumbnail().getPath()+ "/standard_small."+result.getThumbnail().getExtension(),
                                                  result.getDescription()

                                            );

                                      }
                                  }).collect(RxUtils.<ComicItemAdapter>createList(),
                                                  RxUtils.<ComicItemAdapter>addItemToList());

                                      }
                                  });
                      }

    @Override
    public Observable<List<ComicItemAdapter>> loadComicsInMoneyRangeFromDB(int maxmoney) {
        // not used remotely
        return null;
    }


}
