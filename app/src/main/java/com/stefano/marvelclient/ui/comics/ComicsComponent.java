package com.stefano.marvelclient.ui.comics;

import com.stefano.marvelclient.data.ComicsRepositoryComponent;
import com.stefano.marvelclient.ui.base.ActivityScope;
import com.stefano.marvelclient.util.schedulers.SchedulerModule;
import dagger.Component;

/**
 * @author Stefano
 */
@ActivityScope
@Component(modules = {ComicsPresenterModule.class, SchedulerModule.class}, dependencies = {
    ComicsRepositoryComponent.class
})
public interface ComicsComponent {
  void inject(ComicsActivity comicsActivity);
}
