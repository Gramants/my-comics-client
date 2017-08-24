package com.stefano.marvelclient;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author Stefano
 */
@Module
public class AppModule {
  private Context context;

  public AppModule(Application context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }
}
