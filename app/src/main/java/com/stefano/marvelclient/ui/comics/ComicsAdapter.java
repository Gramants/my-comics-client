package com.stefano.marvelclient.ui.comics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.ui.base.BaseRecyclerViewAdapter;
import com.stefano.marvelclient.R;

import io.reactivex.annotations.NonNull;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author Stefano
 */
class ComicsAdapter extends BaseRecyclerViewAdapter<ComicsAdapter.ComicViewHolder> {


    private final Context context;


    /*------ nested viewholder ----*/
  class  ComicViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.comic_title)
    TextView comicTitle;
    @BindView(R.id.comic_price)
    TextView comicPrice;
    @BindView(R.id.comic_creator)
    TextView comicCreator;
    @BindView(R.id.comic_icon)
    ImageView comicImage;

    public ComicViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  private List<ComicItemAdapter> comics;

  public ComicsAdapter(Context context, @NonNull List<ComicItemAdapter> comics) {
    this.context=context;
    this.comics = comics;
  }

  @Override
  public ComicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater
        .from(viewGroup.getContext())
        .inflate(R.layout.item_comic, viewGroup, false);
    return new ComicViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    super.onBindViewHolder(viewHolder, i);
    ComicViewHolder vh = (ComicViewHolder) viewHolder; //safe cast
    ComicItemAdapter comic = comics.get(i);
    vh.comicTitle.setText(comic.getTitle());
    vh.comicPrice.setText(String.valueOf(comic.getPrice())+" GBP");
    vh.comicCreator.setText(comic.getCreatorname());

      Glide.with(context)
              .load(comic.getComicimage())
              .fitCenter()
              .into(vh.comicImage);

  }

  @Override
  public int getItemCount() {
    return comics.size();
  }

  /* Public API*/

  public void replaceData(List<ComicItemAdapter> comicslist) {
    this.comics.clear();
    this.comics.addAll(comicslist);
    notifyDataSetChanged();
  }

  public ComicItemAdapter getItem(int position) {
    if (position < 0 || position >= comics.size()) {
      throw new InvalidParameterException("Invalid item index");
    }
    return comics.get(position);
  }

  public void clearData() {
    comics.clear();
    notifyDataSetChanged();
  }
}
