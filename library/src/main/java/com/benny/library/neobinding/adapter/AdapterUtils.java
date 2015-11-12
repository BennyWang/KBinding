package com.benny.library.neobinding.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.benny.library.neobinding.bind.BindableModel;
import com.benny.library.neobinding.bind.ViewCreator;
import com.benny.library.neobinding.bind.ViewCreatorCollection;

import rx.functions.Action1;
import rx.functions.Func1;

public class AdapterUtils {

    public static <T> Func1<AdapterItemAccessor<T>, PagerAdapter> toPagerAdapter(final ViewCreator<T> viewCreator) {
        return items -> new PagerAdapter() {

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                T data = items.get(position);
                View convertView = viewCreator.view(container);
                container.addView(convertView);
                ((BindableModel<T>)convertView.getTag()).notifyPropertyChange(data, (position != 0 && position == getCount() - 1) ? -1 : position);
                return convertView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
    }

    public static <T> Func1<AdapterItemAccessor<T>, BaseAdapter> toAdapter(final ViewCreatorCollection<T> collection) {
        return items -> new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public T getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                T data = getItem(position);
                if(convertView == null) {
                    convertView = collection.view(parent);
                }
                if(convertView.getTag() != null) {
                    ((BindableModel<T>)convertView.getTag()).notifyPropertyChange(data, (position != 0 && position == getCount() - 1) ? -1 : position);
                }
                return convertView;
            }

            @Override
            public int getViewTypeCount() {
                return collection.typeCount();
            }

            @Override
            public int getItemViewType(int position) {
                return collection.viewTypeFor(getItem(position));
            }
        };
    }

    public static <T> Func1<AdapterItemAccessor<T>, BaseAdapter> toAdapter(final ViewCreator<T> viewCreator) {
        return items -> new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public T getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                T data = getItem(position);
                if(convertView == null) {
                    convertView = viewCreator.view(parent);
                }
                if(convertView.getTag() != null) {
                    ((BindableModel<T>)convertView.getTag()).notifyPropertyChange(data, (position != 0 && position == getCount() - 1) ? -1 : position);
                }
                return convertView;
            }
        };
    }

    public static <T> Func1<AdapterItemAccessor<T>, PagingAdapter<T>> toPagingAdapter(ViewCreatorCollection<T> collection, Action1<T> listener)  {
        return items -> new PagingAdapter<T>(listener) {
            int viewType = -1;

            @Override
            public View getViewBase(int position, View convertView, ViewGroup parent) {
                //Log.d("PagingAdapter", "getView----- position:" + position + "view type:" + viewType + " view:" + convertView);
                T data = getItem(position);
                if(convertView == null) {
                    convertView = collection.view(parent);
                }
                if(convertView.getTag() != null) {
                    ((BindableModel<T>)convertView.getTag()).notifyPropertyChange(data, (position != 0 && position == getCount() - 1) ? -1 : position);
                }
                return convertView;
            }

            @Override
            public int getCountBase() {
                return items.size();
            }

            @Override
            public T getItem(int position) {
                return position < 0 || position >= items.size() ? null : items.get(position);
            }

            @Override
            public int getViewTypeCount() {
                return collection.typeCount();
            }

            @Override
            public int getItemViewType(int position) {
                viewType = collection.viewTypeFor(getItem(position));
                //Log.d("PagingAdapter", "getViewType--- position:" + position + " view type :" + viewType);
                return viewType;
            }
        };
    }

    public static <T> Func1<AdapterItemAccessor<T>, PagingAdapter<T>> toPagingAdapter(ViewCreator<T> viewCreator, Action1<T> listener)  {
        return items -> new PagingAdapter<T>(listener) {
            @Override
            public View getViewBase(int position, View convertView, ViewGroup parent) {
                T data = getItem(position);
                if(convertView == null) {
                    convertView = viewCreator.view(parent);
                }
                if(convertView.getTag() != null) {
                    ((BindableModel<T>)convertView.getTag()).notifyPropertyChange(data, (position != 0 && position == getCount() - 1) ? -1 : position);
                }
                return convertView;
            }

            @Override
            public int getCountBase() {
                return items.size();
            }

            @Override
            public T getItem(int position) {
                return position < 0 || position >= items.size() ? null : items.get(position);
            }
        };
    }

    public static  <T> Func1<AdapterItemAccessor<T>, RecyclerView.Adapter > toRecyclerAdapter(ViewCreator<T> viewCreator) {
        return items -> new RecyclerView.Adapter() {

            class ViewHolder extends RecyclerView.ViewHolder {
                public ViewHolder(View itemView) {
                    super(itemView);
                }

                public void notifyPropertyChange(T data, int position) {
                    ((BindableModel<T>)(itemView.getTag())).notifyPropertyChange(data, position);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = viewCreator.view(parent);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((ViewHolder)holder).notifyPropertyChange(items.get(position), position);
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };
    }

    public static  <T> Func1<AdapterItemAccessor<T>, RecyclerView.Adapter > toRecyclerAdapter(ViewCreatorCollection<T> collection) {
        return items -> new RecyclerView.Adapter() {

            class ViewHolder extends RecyclerView.ViewHolder {
                public ViewHolder(View itemView) {
                    super(itemView);
                }

                public void notifyPropertyChange(T data, int position) {
                    ((BindableModel<T>)(itemView.getTag())).notifyPropertyChange(data, position);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = collection.view(parent);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((ViewHolder)holder).notifyPropertyChange(items.get(position), position);
            }

            @Override
            public int getItemViewType(int position) {
                return collection.viewTypeFor(items.get(position));
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };
    }

    public static  <T> Func1<AdapterItemAccessor<T>, RecyclerPagingAdapter<T> > toRecyclerPagingAdapter(ViewCreator<T> viewCreator, Action1<T> listener) {
        return items -> new RecyclerPagingAdapter<T>(listener) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = viewCreator.view(parent);
                return new ViewHolder(view);
            }

            @Override
            public T getItem(int position) {
                return position < 0 || position >= items.size() ? null : items.get(position);
            }

            @Override
            public int getCountBase() {
                return items.size();
            }
        };
    }

    public static  <T> Func1<AdapterItemAccessor<T>, RecyclerPagingAdapter<T> > toRecyclerPagingAdapter(ViewCreatorCollection<T> collection, Action1<T> listener) {
        return items -> new RecyclerPagingAdapter<T>(listener) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = collection.view(parent);
                return new ViewHolder(view);
            }

            @Override
            public int getItemViewType(int position) {
                return collection.viewTypeFor(getItem(position));
            }

            @Override
            public T getItem(int position) {
                return position < 0 || position >= items.size() ? null : items.get(position);
            }

            @Override
            public int getCountBase() {
                return items.size();
            }
        };
    }
}
