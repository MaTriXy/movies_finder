package com.ronen.moviesfinder.ui.activities

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.ronen.moviesfinder.R



class YoutubeVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    var videoId: String? = null

    companion object {
        val KEY_VIDEO_ID = "video_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_video)

        videoId = intent.getStringExtra(KEY_VIDEO_ID)

        initYoutubeVideoPlayer()
    }

    private fun initYoutubeVideoPlayer() {
        val youTubePlayerView = findViewById(R.id.youtube_player_view) as YouTubePlayerView
        youTubePlayerView.initialize(getString(R.string.youtube_api_key), this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        player?.let {
            if (!wasRestored) {
                player.cueVideo(videoId)
            }
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
    }
}
