package com.example.kotlinbase

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.squareup.picasso.Picasso
import java.io.File

class GalleryAdapter(private val fileArray: Array<File> ,val context: Context) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.local_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_img, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = fileArray[position]


        // sets the image to the imageview from our itemHolder class

        Glide.with(context ).load(ItemsViewModel).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return fileArray.size
    }


}