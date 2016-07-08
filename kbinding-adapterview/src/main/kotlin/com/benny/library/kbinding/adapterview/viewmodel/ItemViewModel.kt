package com.benny.library.kbinding.adapterview.viewmodel;

import android.view.View
import com.benny.library.autoadapter.viewholder.IViewHolder
import com.benny.library.kbinding.viewmodel.ViewModel

/**
 * Created by benny on 11/17/15.
 */

abstract class ItemViewModel<T> : ViewModel(), IViewHolder<T> {
    override fun bind(view: View?) {
    }
}