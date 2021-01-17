package com.example.ulessontest.ui.player

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.di.room.entities.RecentlyWatched
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentPlayMediaBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.global.gomoney.utils.viewbinding.viewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PlayMediaFragment : BaseFragment(R.layout.fragment_play_media) {

    private val binding by viewBinding(FragmentPlayMediaBinding::bind)
    private val args by navArgs<PlayMediaFragmentArgs>()
    private val lesson by lazy { args.lesson }

    private val viewModel: PlayMediaViewModel by viewModels()

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun setUp(view: View) {
        binding.lessonTitle.text = lesson.name
        binding.chapterTitle.text = args.info.chapter

        binding.exit.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.videoPlayer.setShowNextButton(false)
        binding.videoPlayer.setShowPreviousButton(false)
        binding.videoPlayer.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
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
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saveRecentlyWatchedVideo(buildRecentlyVideoData())
        }
    }

    private fun buildRecentlyVideoData(): RecentlyWatched {
        return RecentlyWatched(args.info.chapter, lesson.name, lesson.mediaUrl, args.info.subject, SimpleDateFormat().format(
            Date()), lesson.icon)
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady ?: false
            playbackPosition = player?.currentPosition ?: 0
            currentWindow = player?.currentWindowIndex ?: 1
            player?.release()
            player = null
        }
    }
}