package com.wonn.githubrepo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wonn.githubrepo.R;
import com.wonn.githubrepo.adapter.contract.BaseAdapter;
import com.wonn.githubrepo.model.Repository;
import com.wonn.githubrepo.model.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseAdapter.View, BaseAdapter.Model<Repository> {
    private Context context;
    private ArrayList<Repository> repositories = new ArrayList<>();
    private UserInfo userInfo = null;

    private static final int TYPE_USERINFO = -1;
    private static final int TYPE_REPOSITOTY = 0;

    public RepositoryAdapter(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        this.context = viewGroup.getContext();
        if (viewType == TYPE_USERINFO) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_userinfo, viewGroup, false);
            return new UserInfoViewHolder(view);
        } else if (viewType == TYPE_REPOSITOTY) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repository, viewGroup, false);
            return new ReposViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserInfoViewHolder) {
            if (userInfo != null) {
                UserInfoViewHolder userInfoViewHolder = (UserInfoViewHolder) holder;

                Glide.with(context).asBitmap().load(userInfo.getAvatar_url()).into(userInfoViewHolder.iv_userInfo_thumbnail);
                userInfoViewHolder.tv_userInfo_username.setText(userInfo.getName());
            }
        } else if (holder instanceof ReposViewHolder) {
            if (repositories.size() != 0) {
                Repository repository = repositories.get(position-1);
                ReposViewHolder reposViewHolder = (ReposViewHolder) holder;
                Log.d("Adapter", "onBindViewHolder: " + repository.getName() + " position: " + position);

                reposViewHolder.tv_repos_name.setText(repository.getName());
                if (repository.getDescription() != null) {
                    reposViewHolder.tv_repos_description.setText(repository.getDescription());
                }
                reposViewHolder.tv_repos_star.setText(String.valueOf(repository.getStarCount()));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_USERINFO;
        }
        return TYPE_REPOSITOTY;
    }

    @Override
    public int getItemCount() {
        return repositories.size()+1;
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void addItem(Repository models) {
        repositories.add(models);
    }

    @Override
    public Repository getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public void clear() {
        repositories.clear();
    }

    public void sort() {
        Collections.sort(repositories, new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return (o2.getStarCount() - o1.getStarCount());
            }
        });
    }
}

