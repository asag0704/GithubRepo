package com.wonn.githubrepo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wonn.githubrepo.R;
import com.wonn.githubrepo.adapter.contract.BaseAdapter;
import com.wonn.githubrepo.model.Repository;
import com.wonn.githubrepo.model.UserInfo;

import java.util.ArrayList;

public class ReposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseAdapter.View, BaseAdapter.Model<Object> {
    private Context context;
    private ArrayList<Object> arrayList = new ArrayList<>();

    private static final int TYPE_USER = -1;
    private static final int TYPE_REPOS = 0;

    public ReposAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        this.context = viewGroup.getContext();
        if (viewType == TYPE_USER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_repository, viewGroup, false);
            return new ReposViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_userinfo, viewGroup, false);
            return new UserInfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReposViewHolder) {
            Repository repo = (Repository) arrayList.get(position);
            ReposViewHolder reposViewHolder = (ReposViewHolder) holder;

            reposViewHolder.tv_repos_name.setText(repo.getName());
            reposViewHolder.tv_repos_description.setText(repo.getDescription());
            reposViewHolder.tv_repos_star.setText(repo.getStarCount());
        } else {
            UserInfo userInfo = (UserInfo) arrayList.get(position);
            UserInfoViewHolder userInfoViewHolder = (UserInfoViewHolder) holder;

            Glide.with(context).asBitmap().load(userInfo.getAvatar_url()).into(userInfoViewHolder.iv_userInfo_thumbnail);
            userInfoViewHolder.tv_userInfo_username.setText(userInfo.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.size() == 0) {
            return TYPE_USER;
        }
        return TYPE_REPOS;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void addItem(Object models) {
        arrayList.add(models);
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }
}
