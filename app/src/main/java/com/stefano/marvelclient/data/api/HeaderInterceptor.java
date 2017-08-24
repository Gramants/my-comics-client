package com.stefano.marvelclient.data.api;

import com.stefano.marvelclient.util.Md5Util;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Stefano
 */
public class HeaderInterceptor implements Interceptor {
  @Override

  public Response intercept(Chain chain) throws IOException {
    final String timestamp = String.valueOf(System.currentTimeMillis());
    final Request request = chain.request();
    final HttpUrl url = request.url().newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", ApiConsts.PUBLIC_KEY)
            .addQueryParameter("hash", Md5Util.generateMd5Hash(timestamp + ApiConsts.PRIVATE_KEY + ApiConsts.PUBLIC_KEY))
            //.addHeader("Accept", "application/json")
            //.addHeader("Content-type", "application/json")
            .build();
    return chain.proceed(request.newBuilder().url(url).build());
  }

}
