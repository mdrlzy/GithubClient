package com.mordeniuss.githubclient.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadCircleImage(url: String, container: ImageView) {
    Glide.with(container.context)
        .load(url)
        .circleCrop()
        .into(container)
}
