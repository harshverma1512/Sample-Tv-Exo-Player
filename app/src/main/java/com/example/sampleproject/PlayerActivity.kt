package com.example.sampleproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class PlayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    // Custom controls
    private lateinit var controlsContainer: View
    private lateinit var topBar: View
    private lateinit var bottomControls: View
    private lateinit var btnBack: ImageButton
    private lateinit var tvTitle: TextView
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnForward: ImageButton
    private lateinit var btnRewind: ImageButton
    private lateinit var seekBar: SeekBar

    private var controlsVisible = false
    private val autoHideDelay = 5000L

    private val handler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            player?.let {
                seekBar.progress = it.currentPosition.toInt()
                handler.postDelayed(this, 500)
            }
        }
    }

    private val hideRunnable = Runnable { hideControls() }

    private var videoUrl = ""
    private var videoTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        videoUrl = intent.getStringExtra("url") ?: ""
        videoTitle = intent.getStringExtra("title") ?: "Video"

        playerView = findViewById(R.id.playerView)

        controlsContainer = findViewById(R.id.customControls)
        topBar = controlsContainer.findViewById(R.id.topBar)
        bottomControls = controlsContainer.findViewById(R.id.bottomControls)
        btnBack = controlsContainer.findViewById(R.id.btnBack)
        tvTitle = controlsContainer.findViewById(R.id.tvTitle)
        btnPlayPause = controlsContainer.findViewById(R.id.btnPlayPause)
        btnForward = controlsContainer.findViewById(R.id.btnForward)
        btnRewind = controlsContainer.findViewById(R.id.btnRewind)
        seekBar = controlsContainer.findViewById(R.id.seekBar)

        if (BuildCompat.isAtLeastT()) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
             finish()
            }
        }else{
            finish()
        }
        tvTitle.text = videoTitle

        btnBack.setOnClickListener { finish() }
        btnPlayPause.setOnClickListener { togglePlayPause() }
        btnForward.setOnClickListener { seekBy(10_000) }
        btnRewind.setOnClickListener { seekBy(-10_000) }

        playerView.setOnClickListener {
            if (!controlsVisible) showControls()
            else togglePlayPause()
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player?.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {
                showControls()
            }

            override fun onStopTrackingTouch(sb: SeekBar?) {
                restartAutoHideTimer()
            }
        })
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(videoUrl)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.play()

        btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)

        player!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == ExoPlayer.STATE_READY) {
                    seekBar.max = player!!.duration.toInt()
                    handler.post(progressRunnable)
                }
            }
        })
    }

    private fun togglePlayPause() {
        player?.let {
            if (it.isPlaying) {
                it.pause()
                btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
            } else {
                it.play()
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
        restartAutoHideTimer()
    }

    private fun seekBy(ms: Long) {
        player?.let {
            val newPos = (it.currentPosition + ms).coerceAtLeast(0)
            it.seekTo(newPos)
        }
        restartAutoHideTimer()
    }

    private fun showControls() {
        controlsContainer.visibility = View.VISIBLE
        controlsVisible = true
        topBar.visibility = View.VISIBLE
        bottomControls.visibility = View.VISIBLE
        restartAutoHideTimer()
    }

    private fun hideControls() {
        controlsContainer.visibility = View.GONE
        controlsVisible = false
        controlsContainer.clearFocus()
        topBar.visibility = View.GONE
        bottomControls.visibility = View.GONE
    }

    private fun restartAutoHideTimer() {
        handler.removeCallbacks(hideRunnable)
        handler.postDelayed(hideRunnable, autoHideDelay)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {

            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER,
                -> {
                if (!controlsVisible) showControls()
                else togglePlayPause()
                return true
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                showControls()
                seekBy(-10_000)
                return true
            }

            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                showControls()
                seekBy(10_000)
                return true
            }

            KeyEvent.KEYCODE_DPAD_CENTER -> {
                showControls()
            }
            KeyEvent.KEYCODE_DPAD_UP -> {
                showControls()
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                showControls()
            }

            KeyEvent.KEYCODE_ENTER -> {
                showControls()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(progressRunnable)
        handler.removeCallbacks(hideRunnable)
        player?.release()
        player = null
    }
}
