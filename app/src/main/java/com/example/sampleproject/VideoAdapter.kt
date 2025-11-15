package com.example.sampleproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sampleproject.models.VideoItem

class VideoAdapter(
    private val items: List<VideoItem>,
    private val onItemClick: (VideoItem) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoVH(v)
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size

    class VideoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val imgThumb: ImageView = itemView.findViewById(R.id.imgThumb)

        fun bind(item: VideoItem, onClick: (VideoItem) -> Unit) {
            tvTitle.text = item.title

            val thumb = item.thumbUrl
            if (!thumb.isNullOrBlank()) {
                imgThumb.load(thumb) {
                    placeholder(android.R.drawable.ic_media_play)
                    crossfade(true)
                }
            } else {
                imgThumb.setImageResource(android.R.drawable.ic_media_play)
            }

            itemView.setOnClickListener { onClick(item) }

            itemView.isFocusable = true
            itemView.isFocusableInTouchMode = true

            itemView.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.animate()
                        .scaleX(1.08f)
                        .scaleY(1.08f)
                        .translationZ(12f)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setDuration(120)
                        .start()
                } else {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationZ(0f)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setDuration(120)
                        .start()
                }
            }
        }
    }
}
