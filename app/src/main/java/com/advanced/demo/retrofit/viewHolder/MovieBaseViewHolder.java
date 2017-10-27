package com.advanced.demo.retrofit.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.advanced.demo.retrofit.MovieResponse;

/**
 * @author by morton_ws on 2017/10/27.
 */

public abstract class MovieBaseViewHolder extends RecyclerView.ViewHolder{
    protected Context mContext;
    protected MovieResponse.MovieBean mMovie;

    public MovieBaseViewHolder(Context context, View view) {
        super(view);
        this.mContext = context;
    }

    public final void showMovie(MovieResponse.MovieBean movie) {
        this.mMovie = movie;
        onShow();
    }

    public abstract void onShow();
}
