package com.stefano.marvelclient.ui.base;

import android.view.View;

/**
 * @author Stefano
 */
public interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    void OnItemClick(View view, int position);
  }

  @FunctionalInterface
  interface OnItemLongClickListener {
    void OnItemLongClick(View view, int position);
  }
}
