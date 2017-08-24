package com.stefano.marvelclient.ui.comics;

import dagger.Module;
import dagger.Provides;

/**
 * @author Stefano
 */
@Module
public class ComicsPresenterModule {
  private ComicsContract.View view;

  public ComicsPresenterModule(ComicsContract.View view) {
    this.view = view;
  }

  @Provides
  public ComicsContract.View provideView() {
    return view;
  }
}
