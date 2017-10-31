package com.advanced.demo.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advanced.demo.R;
import com.advanced.demo.retrofit.viewHolder.DouBanMovieViewHolder;
import com.advanced.demo.retrofit.viewHolder.MovieBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class DoubanMovieAdapter extends RecyclerView.Adapter<MovieBaseViewHolder> {
    private final static int MOVIE_TYPE_ITEM = 1;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MovieResponse.MovieBean> mMovieList = new ArrayList<>();

    public DoubanMovieAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return MOVIE_TYPE_ITEM;
    }

    @Override
    public MovieBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_douban_movie, parent, false);
        return new DouBanMovieViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(MovieBaseViewHolder holder, int position) {
        MovieResponse.MovieBean movie = mMovieList.get(position);
        holder.showMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void addMovieData(List<MovieResponse.MovieBean> data) {
        if (data != null) {
            mMovieList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mMovieList.clear();
        notifyDataSetChanged();
    }
}
