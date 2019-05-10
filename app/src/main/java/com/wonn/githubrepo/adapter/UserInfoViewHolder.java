package com.wonn.githubrepo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonn.githubrepo.R;

public class UserInfoViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_userInfo_thumbnail;
    public TextView tv_userInfo_username;

    public UserInfoViewHolder(@NonNull View itemView) {
        super(itemView);

        iv_userInfo_thumbnail = itemView.findViewById(R.id.iv_userInfo_thumbnail);
        tv_userInfo_username = itemView.findViewById(R.id.tv_userInfo_username);
    }
}
