package com.stefano.marvelclient.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.stefano.marvelclient.data.Config;

@Entity(tableName = Config.COMICS_TABLE)


public class ComicItemAdapter {




    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "format")
    private String format;

    @ColumnInfo(name = "creatorname")
    private String creatorname;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "comicimage")
    private String comicimage;

    @ColumnInfo(name = "description")
    private String description;

    public ComicItemAdapter(Integer id, String title, String format, String creatorname, Double price, String comicimage, String description) {

        this.id=id;
        this.title=title;
        this.format=format;
        this.creatorname=creatorname;
        this.price=price;
        this.comicimage=comicimage;
        this.description=description;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComicimage() {
        return comicimage;
    }

    public void setComicimage(String comicimage) {
        this.comicimage = comicimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
