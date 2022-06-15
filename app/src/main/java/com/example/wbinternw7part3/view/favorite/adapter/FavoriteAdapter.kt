package com.example.wbinternw7part3.view.favorite.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wbinternw7part3.R
import com.example.wbinternw7part3.model.FavoriteData
import com.facebook.drawee.view.SimpleDraweeView

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.RecyclerItemViewHolder>() {

    private var data: ArrayList<FavoriteData> = arrayListOf()

    fun setData(newData: List<FavoriteData>) {
        val diffUtilsCallBack = DiffUtilsCallBack(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallBack)
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: FavoriteData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                val uri = Uri.parse(data.url)
                itemView.findViewById<SimpleDraweeView>(R.id.image).setImageURI(uri)
            }
        }
    }
}