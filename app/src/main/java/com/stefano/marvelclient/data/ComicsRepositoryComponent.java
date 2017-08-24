package com.stefano.marvelclient.data;

import com.stefano.marvelclient.AppModule;
import com.stefano.marvelclient.data.repository.ComicsRepository;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author Stefano
 */
@Singleton
@Component(modules = { ComicsRepositoryModule.class, AppModule.class, ApiServiceModule.class,
    DatabaseModule.class})
public interface ComicsRepositoryComponent {
  ComicsRepository provideComicRepository();
}
