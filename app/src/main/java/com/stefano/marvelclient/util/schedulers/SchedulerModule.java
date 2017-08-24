package com.stefano.marvelclient.util.schedulers;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Provides common Schedulers used by RxJava
 *
 * @author Stefano
 */
@Module
public class SchedulerModule {

  @Provides
  @RunOn(SchedulerType.IO)
  rx.Scheduler provideIo(){
    return Schedulers.io();
  }

  @Provides
  @RunOn(SchedulerType.COMPUTATION)
  rx.Scheduler provideComputation() {
    return Schedulers.computation();
  }

  @Provides
  @RunOn(SchedulerType.UI)
  rx.Scheduler provideUi() {
    return AndroidSchedulers.mainThread();
  }
}
