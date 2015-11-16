package com.benny.library.neobinding.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import rx.functions.Action1;

/**
 * Created by benny on 9/17/15.
 */

public abstract class PagingAdapter<T> extends BaseAdapter {
    private boolean hasNextPage = true;
    AdapterPagingListener listener;

    public PagingAdapter(AdapterPagingListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return hasNextPage ? getCountBase() + 1 : getCountBase();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    final public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewBase(position, convertView, parent);

        if(position == getCount() - 1 && hasNextPage) {
            listener.onLoadPage(getItem(position - 1), position);
        }
        return convertView;
    }

    public void loadComplete(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        notifyDataSetChanged();
    }

    public abstract View getViewBase(int position, View convertView, ViewGroup parent);
    public abstract int getCountBase();

    @Override
    public abstract T getItem(int position);

}
