package com.example.sampleproject

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject.models.VideoItem

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recyclerVideos)
        val layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        recycler.layoutManager = layoutManager

        val list = sampleVideos()
        adapter = VideoAdapter(list) { item ->
            val i = Intent(this, PlayerActivity::class.java)
            i.putExtra("url", item.videoUrl)
            i.putExtra("title", item.title)
            startActivity(i)
        }

        recycler.adapter = adapter
        recycler.isFocusable = true
        recycler.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
    }

    private fun sampleVideos(): List<VideoItem> {
        val baseThumb = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/"
        val v = listOf(
            VideoItem(
                id = "1",
                title = "Big Buck Bunny",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                thumbUrl = baseThumb + "BigBuckBunny.jpg"
            ),
            VideoItem(
                id = "2",
                title = "Elephants Dream",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                thumbUrl = baseThumb + "ElephantsDream.jpg"
            ),
            VideoItem(
                id = "3",
                title = "For Bigger Blazes",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                thumbUrl = baseThumb + "ForBiggerBlazes.jpg"
            ),
            VideoItem(
                id = "4",
                title = "For Bigger Escape",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                thumbUrl = baseThumb + "ForBiggerEscapes.jpg"
            ),
            VideoItem(
                id = "5",
                title = "Sintel",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                thumbUrl = baseThumb + "Sintel.jpg"
            ),
            VideoItem(
                id = "6",
                title = "Tears of Steel",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                thumbUrl = baseThumb + "TearsOfSteel.jpg"
            )
        )
        return v
    }
}
