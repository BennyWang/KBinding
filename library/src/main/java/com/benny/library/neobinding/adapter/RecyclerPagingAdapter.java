package com.benny.library.neobinding.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.benny.library.neobinding.bind.BindableModel;

import rx.functions.Action1;

/**
 * Created by benny on 9/17/15.
 */

public abstract class RecyclerPagingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void notifyPropertyChange(T data, int position) {
            if(itemView.getTag() != null) {
                ((BindableModel<T>) (itemView.getTag())).notifyPropertyChange(data, position);
            }
        }
    }

    private boolean hasNextPage = true;
    Action1<T> listener;


    public RecyclerPagingAdapter(Action1<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder)holder).notifyPropertyChange(getItem(position), position);

        if(position == getItemCount() - 1 && hasNextPage) {
            listener.call(getItem(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return hasNextPage ? getCountBase() + 1 : getCountBase();
    }

    public void loadComplete(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        notifyDataSetChanged();
    }

    public abstract T getItem(int position);
    public abstract int getCountBase();
}
