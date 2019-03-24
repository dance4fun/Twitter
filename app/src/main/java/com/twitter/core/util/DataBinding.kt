package com.twitter.core.util

import android.databinding.BindingAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.twitter.core.util.image.GlideApp

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
  GlideApp.with(view)
      .asBitmap()
      .load(url)
      .apply(RequestOptions().override(view.width, view.height))
      .into(view)
}

@BindingAdapter("onEditorAction")
fun onEditorAction(view: EditText, onEditorActionListener: TextView.OnEditorActionListener) {
  view.setOnEditorActionListener(onEditorActionListener)
}
