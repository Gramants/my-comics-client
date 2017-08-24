package com.stefano.marvelclient;

import android.app.Application;

import com.stefano.marvelclient.data.ComicsRepositoryComponent;
import com.stefano.marvelclient.data.DaggerComicsRepositoryComponent;

public class AndroidApplication extends Application {

    private ComicsRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDependencies();

    }

    private void initializeDependencies() {
        repositoryComponent = DaggerComicsRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ComicsRepositoryComponent getComicRepositoryComponent() {
        return repositoryComponent;
    }
}
