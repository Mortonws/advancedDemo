package com.advanced.demo.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advanced.demo.R;
import com.advanced.demo.retrofit.viewHolder.DouBanMovieViewHolder;
import com.advanced.demo.retrofit.viewHolder.MovieBaseViewHolder;
import com.advanced.demo.retrofit.viewHolder.MovieLoadingViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class DoubanMovieAdapter extends RecyclerView.Adapter<MovieBaseViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MovieResponse.MovieBean> mMovieList = new ArrayList<>();
    private MovieResponse.MovieBean mMovieBean = new MovieResponse.MovieBean();

    public DoubanMovieAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mMovieBean.itemType = RetrofitRequestUtils.ItemViewType.ITEM_TYPE_LOADING;
        mMovieBean.loadingStatus = RetrofitRequestUtils.LoadingStatus.STATUS_LOAD_COMPLETED;
        mMovieList.add(mMovieBean);
    }

    @Override
    public int getItemViewType(int position) {
        return mMovieList.get(position).itemType;
    }

    @Override
    public MovieBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MovieBaseViewHolder viewHolder;
        if (viewType == RetrofitRequestUtils.ItemViewType.ITEM_TYPE_LOADING) {
            view = mLayoutInflater.inflate(R.layout.item_movie_loading, parent, false);
            viewHolder = new MovieLoadingViewHolder(mContext, view);
        } else {
            view = mLayoutInflater.inflate(R.layout.item_douban_movie, parent, false);
            viewHolder = new DouBanMovieViewHolder(mContext, view);
        }
        return viewHolder;
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

    public void loadingStatusChanged(int loadingStatus) {
        mMovieList.get(getItemCount() - 1).loadingStatus = loadingStatus;
        notifyItemChanged(getItemCount() - 1);
    }

    public void addMovieData(List<MovieResponse.MovieBean> data) {
        if (data != null) {
            mMovieList.addAll(mMovieList.size() - 1, data);
            notifyDataSetChanged();
        }
    }

    public int getLoadingStatus() {
        return mMovieList.get(getItemCount() - 1).loadingStatus;
    }

    public void clearData() {
        mMovieList.clear();
        mMovieList.add(mMovieBean);
        notifyDataSetChanged();
    }
}
