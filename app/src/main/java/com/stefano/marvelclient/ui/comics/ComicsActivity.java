package com.stefano.marvelclient.ui.comics;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.stefano.marvelclient.data.model.ComicItemAdapter;
import com.stefano.marvelclient.ui.base.BaseActivity;
import com.stefano.marvelclient.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComicsActivity extends BaseActivity implements ComicsContract.View {

    @BindView(R.id.comic_recycler)
    RecyclerView comicRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.notiText)
    TextView notiText;
    @BindView(R.id.tickEnd)
    TextView tickEnd;
    @BindView(R.id.tickEndSeek)
    SeekBar tickEndSeek;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ComicsAdapter adapter;

    @Inject
    ComicsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializePresenter();
        setupWidgets();
        getSupportActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comics_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initializePresenter() {
        DaggerComicsComponent.builder()
                .comicsPresenterModule(new ComicsPresenterModule(this))
                .comicsRepositoryComponent(getComicRepositoryComponent())
                .build()
                .inject(this);
    }

    private void setupWidgets() {
        // Setup recycler view
        adapter = new ComicsAdapter(this, new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        comicRecyclerView.setLayoutManager(layoutManager);
        comicRecyclerView.setAdapter(adapter);
        comicRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(
                (view, position) -> presenter.getComic(adapter.getItem(position).getId()));

        // Refresh layout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tickEnd.setVisibility(View.GONE);
                tickEndSeek.setVisibility(View.GONE);
                tickEndSeek.setProgress(10);
                tickEnd.setText("Select your max budget");
                presenter.loadComics(true);
                notiText.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        tickEndSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar tickCountSeek, int progress, boolean fromUser) {
                tickEnd.setText("Max available budget: " + progress + " GBP");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress();
                presenter.loadComicsInMoneyRangeFromDB(value);


            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        tickEndSeek.setProgress(10);
        tickEnd.setText("Select your max budget");

    }

    @Override
    protected void onStart() {
        super.onStart();
        tickEnd.setVisibility(View.GONE);
        tickEndSeek.setVisibility(View.GONE);
        presenter.loadComics(false);
    }

    @Override
    public void showComics(List<ComicItemAdapter> comiclist) {
        notiText.setVisibility(View.GONE);
        tickEnd.setVisibility(View.VISIBLE);
        tickEndSeek.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.replaceData(comiclist);
    }

    @Override
    public void showNoDataMessage() {
        notiText.setVisibility(View.VISIBLE);
        tickEnd.setVisibility(View.GONE);
        tickEndSeek.setVisibility(View.GONE);
        notiText.setText(getResources().getString(R.string.error_no_data));
    }

    @Override
    public void showErrorMessage(String error) {
        notiText.setVisibility(View.VISIBLE);
        tickEnd.setVisibility(View.GONE);
        tickEndSeek.setVisibility(View.GONE);
        notiText.setText(error);
    }

    @Override
    public void clearComics() {
        adapter.clearData();
    }

    @Override
    public void stopLoadingIndicator() {

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showComicDetail(ComicItemAdapter comic) {


        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title(comic.getFormat())
                        .customView(R.layout.comic_detail_dialog, true)
                        .build();

        final View v = dialog.getCustomView();
        vh = new DialogViewHolder(v);

        vh.comicTitle.setText(comic.getTitle());
        vh.comicPrice.setText(String.valueOf(comic.getPrice()) + " GBP");
        vh.comicCreator.setText(comic.getCreatorname());
        vh.comicDescription.setText(comic.getDescription());
        vh.comicImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        vh.progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(comic.getComicimage().replaceAll("standard_small", "landscape_amazing"))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        vh.progressBar.setVisibility(View.GONE);
                        vh.comicImage.setImageResource(0);
                        vh.comicImage.setScaleType(ImageView.ScaleType.FIT_XY);
                        return false;

                    }
                })
                .crossFade(1000)
                .into(vh.comicImage);

        dialog.show();

    }

    public class DialogViewHolder {

        @BindView(R.id.comic_title)
        TextView comicTitle;
        @BindView(R.id.comic_price)
        TextView comicPrice;
        @BindView(R.id.comic_creator)
        TextView comicCreator;
        @BindView(R.id.comic_description)
        TextView comicDescription;
        @BindView(R.id.comic_icon)
        ImageView comicImage;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        DialogViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public DialogViewHolder vh;
}
