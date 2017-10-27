package com.advanced.demo.retrofit.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.advanced.demo.R;
import com.advanced.demo.retrofit.MovieResponse;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class DouBanMovieViewHolder extends MovieBaseViewHolder {
    ImageView movieAvatar;
    TextView movieName;
    TextView movieRate;
    TextView directorName;
    TextView actorName;

    public DouBanMovieViewHolder(Context context, View view) {
        super(context, view);
        movieAvatar = (ImageView) view.findViewById(R.id.movie_avatar);
        movieName = (TextView) view.findViewById(R.id.movie_name);
        movieRate = (TextView) view.findViewById(R.id.rate_number);
        directorName = (TextView) view.findViewById(R.id.director_name);
        actorName = (TextView) view.findViewById(R.id.actor_name);
    }

    @Override
    public void onShow() {
        Glide.with(mContext).load(mMovie.images.large).error(R.color.color_live_level_bg).into(movieAvatar);
        movieName.setText(mMovie.title);
        movieRate.setText(String.valueOf(mMovie.rating.average));
        StringBuilder directorBuilder = new StringBuilder();
        List<MovieResponse.MovieBean.DirectorsBean> directorList = mMovie.directors;
        if (directorList != null && directorList.size() != 0) {
            for (int i = 0; i < mMovie.directors.size(); i++) {
                if (i == 0) {
                    directorBuilder.append("导演: ");
                } else {
                    directorBuilder.append(",");
                }
                directorBuilder.append(directorList.get(i).name);
            }
        } else {
            directorBuilder.append("无法查找导演");
        }
        directorName.setText(directorBuilder.toString());
        StringBuilder actorBuilder = new StringBuilder();
        List<MovieResponse.MovieBean.CastsBean> castList = mMovie.casts;
        if (castList != null && castList.size() != 0) {
            for (int i = 0; i< castList.size(); i++) {
                if (i == 0) {
                    actorBuilder.append("演员: ");
                } else {
                    actorBuilder.append(",");
                }
                actorBuilder.append(castList.get(i).name);
            }
        } else {
            actorBuilder.append("无法查找到演员");
        }
        actorName.setText(actorBuilder.toString());
    }
}
