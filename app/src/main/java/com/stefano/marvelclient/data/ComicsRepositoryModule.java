package com.stefano.marvelclient.data;

import com.stefano.marvelclient.data.repository.Local;
import com.stefano.marvelclient.data.repository.ComicsDataSource;
import com.stefano.marvelclient.data.repository.Remote;
import com.stefano.marvelclient.data.repository.local.ComicsLocalDataSource;
import com.stefano.marvelclient.data.repository.remote.ComicsRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author Stefano
 */
@Module
public class ComicsRepositoryModule {

  @Provides
  @Local
  @Singleton
  public ComicsDataSource provideLocalDataSource(ComicsLocalDataSource comicsLocalDataSource) {
    return comicsLocalDataSource;
  }

  @Provides
  @Remote
  @Singleton
  public ComicsDataSource provideRemoteDataSource(ComicsRemoteDataSource comicsRemoteDataSource) {
    return comicsRemoteDataSource;
  }

}
