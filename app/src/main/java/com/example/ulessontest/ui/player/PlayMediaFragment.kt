package com.example.ulessontest.ui.player

import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentPlayMediaBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.global.gomoney.utils.viewbinding.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener

class PlayMediaFragment : BaseFragment(R.layout.fragment_play_media), Player.EventListener {

    private val binding by viewBinding(FragmentPlayMediaBinding::bind)
    private val args by navArgs<PlayMediaFragmentArgs>()
    private val lesson by lazy { args.lesson }

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun setUp(view: View) {
        binding.lessonTitle.text = lesson.name
        binding.chapterTitle.text = args.chapter


        binding.videoPlayer.setControllerVisibilityListener {
            when(it) {
                View.GONE -> {
                    binding.controlContainer.animate().alpha(0f).setDuration(450L).start()
                }
                View.VISIBLE -> {
                    binding.controlContainer.animate().alpha(1f).setDuration(450L).start()
                }
            }
        }

    }

    override fun onPlaybackStateChanged(state: Int) {
        when(state) {
            ExoPlayer.STATE_IDLE -> {}
            ExoPlayer.STATE_BUFFERING -> {
                binding.progressCircular.visibility = View.VISIBLE
            }
            ExoPlayer.STATE_READY -> {
                binding.progressCircular.visibility = View.GONE
            }
            ExoPlayer.STATE_ENDED -> {}
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.videoPlayer.player = player
        val mediaItem = MediaItem.fromUri(lesson.mediaUrl)
        player?.setMediaItem(mediaItem)
        player?.addListener(this)
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady ?: false
            playbackPosition = player?.currentPosition ?: 0
            currentWindow = player?.currentWindowIndex ?: 1
            player?.removeListener(this)
            player?.release()
            player = null
        }
    }
}