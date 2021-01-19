package com.example.ulessontest.ui.player

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.core.di.room.entities.RecentlyWatched
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentPlayMediaBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.example.ulessontest.ui.dashboard.Subjects
import com.global.gomoney.utils.viewbinding.viewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_play_media.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PlayMediaFragment : BaseFragment(R.layout.fragment_play_media), Player.EventListener {

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
        binding.videoPlayer.keepScreenOn = true
        binding.videoPlayer.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)

        bindPlayButton()
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        if (networkInfo == null) {
            Snackbar.make(binding.root, "Please connect to the Internet", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun bindPlayButton() {
        when (args.info.subject.toLowerCase()) {
            Subjects.CHEMISTRY.value -> {
                Glide.with(requireContext()).load(R.drawable.ic_chemisty_play)
                    .into(binding.videoPlayer.findViewById(R.id.exo_play))
                binding.videoPlayer.findViewById<ImageView>(R.id.exo_pause).setImageDrawable(resources.getDrawable(R.drawable.ic_chemitry_pause,requireActivity().theme))
            }
            Subjects.BIO.value -> {
                Glide.with(requireContext()).load(R.drawable.ic_biology_play)
                    .into(binding.videoPlayer.findViewById(R.id.exo_play))
                binding.videoPlayer.findViewById<ImageView>(R.id.exo_pause).setImageDrawable(resources.getDrawable(R.drawable.ic_biology_pause,requireActivity().theme))
            }
            Subjects.MATHS.value -> {
                Glide.with(requireContext()).load(R.drawable.ic_maths_play)
                    .into(binding.videoPlayer.findViewById(R.id.exo_play))
                binding.videoPlayer.findViewById<ImageView>(R.id.exo_pause).setImageDrawable(resources.getDrawable(R.drawable.ic_chemitry_pause,requireActivity().theme))
            }
            Subjects.PHY.value -> {
                Glide.with(requireContext()).load(R.drawable.ic_physics_play)
                    .into(binding.videoPlayer.findViewById(R.id.exo_play))
                binding.videoPlayer.findViewById<ImageView>(R.id.exo_pause).setImageDrawable(resources.getDrawable(R.drawable.ic_physics_pause,requireActivity().theme))
            }
            Subjects.ENG.value -> {
                Glide.with(requireContext()).load(R.drawable.ic_english_play)
                    .into(binding.videoPlayer.findViewById(R.id.exo_play))
                binding.videoPlayer.findViewById<ImageView>(R.id.exo_pause).setImageDrawable(resources.getDrawable(R.drawable.ic_english_pause,requireActivity().theme))
            }
        }
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
        player?.addListener(this)
        player?.playWhenReady = true
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
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
            player?.removeListener(this)
            player?.release()
            player = null
        }
    }

    override fun onPlaybackStateChanged(state: Int) {
        when(state) {
            Player.STATE_BUFFERING -> {
                binding.videoPlayer.hideController()
            }
            Player.STATE_READY -> {
                binding.buffering.visibility = View.GONE
                binding.videoPlayer.showController()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.saveRecentlyWatchedVideo(buildRecentlyVideoData())
                }
            }
        }
    }
}