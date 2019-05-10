package com.wonn.githubrepo.adapter.contract;

public interface BaseAdapter {
    interface View {
        void notifyAdapter();
    }

    interface Model<M> {
        void addItem(M models);
        M getItem(int position);
        void clear();
    }
}
