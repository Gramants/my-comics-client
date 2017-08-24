package com.stefano.marvelclient.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;
import com.stefano.marvelclient.AndroidApplication;
import com.stefano.marvelclient.data.ComicsRepositoryComponent;

/**
 * @author Stefano
 */
public class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);


  protected ComicsRepositoryComponent getComicRepositoryComponent() {
    return ((AndroidApplication) getApplication()).getComicRepositoryComponent();
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }
}
