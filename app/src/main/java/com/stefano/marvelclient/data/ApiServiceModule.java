package com.stefano.marvelclient.data;

import com.stefano.marvelclient.data.api.ApiConsts;
import com.stefano.marvelclient.data.api.HeaderInterceptor;
import com.stefano.marvelclient.data.api.MarvelComicsService;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Stefano
 */

@Module
public class ApiServiceModule {
  private static final String BASE_URL = "base_url";

  @Provides
  @Named(BASE_URL)
  String provideBaseUrl() {
    return ApiConsts.API_ENDPOINT;
  }

  @Provides
  @Singleton
  HeaderInterceptor provideHeaderInterceptor() {
    return new HeaderInterceptor();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
  }

  @Provides
  @Singleton
  OkHttpClient provideHttpClient(HeaderInterceptor headerInterceptor,
      HttpLoggingInterceptor httpInterceptor) {
    return new OkHttpClient.Builder().addInterceptor(headerInterceptor)
        .addInterceptor(httpInterceptor)
        .build();
  }

  @Provides
  @Singleton
  Converter.Factory provideGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton
  CallAdapter.Factory provideRxJavaAdapterFactory() {
    return RxJavaCallAdapterFactory.create();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
      CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(client)
        .build();
  }

  /* Specific services */
  @Provides
  @Singleton
  MarvelComicsService provideComicService(Retrofit retrofit) {
    return retrofit.create(MarvelComicsService.class);
  }
}
