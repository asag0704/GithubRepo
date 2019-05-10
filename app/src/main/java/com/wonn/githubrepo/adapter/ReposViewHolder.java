package com.wonn.githubrepo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wonn.githubrepo.R;

public class ReposViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_repos_name;
    public TextView tv_repos_description;
    public TextView tv_repos_star;

    public ReposViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_repos_name = itemView.findViewById(R.id.tv_repos_name);
        tv_repos_description = itemView.findViewById(R.id.tv_repos_description);
        tv_repos_star = itemView.findViewById(R.id.tv_repos_star);
    }
}
