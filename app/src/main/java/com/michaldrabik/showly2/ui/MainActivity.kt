package com.michaldrabik.showly2.ui

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.michaldrabik.showly2.R
import com.michaldrabik.showly2.appComponent
import com.michaldrabik.showly2.utilities.extensions.dimenToPx
import com.michaldrabik.showly2.utilities.extensions.fixBlinking
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  companion object {
    private const val NAVIGATION_TRANSITION_DURATION_MS = 400L
    private const val ARG_NAVIGATION_VISIBLE = "ARG_NAVIGATION_VISIBLE"
  }

  private val navigationHeight by lazy { dimenToPx(R.dimen.bottomNavigationHeightPadded) }
  private val decelerateInterpolator by lazy { DecelerateInterpolator(2F) }

  @Inject lateinit var uiCache: UiCache

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appComponent().inject(this)
    setContentView(R.layout.activity_main)
    setupNavigation()
    setupNavigationBackHandler()
    restoreState(savedInstanceState)
  }

  private fun setupNavigation() {
    bottomNavigationView.fixBlinking()
    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      if (bottomNavigationView.selectedItemId == item.itemId) {
        return@setOnNavigationItemSelectedListener true
      }

      val target = when (item.itemId) {
        R.id.menuWatchlist -> R.id.actionNavigateWatchlistFragment
        R.id.menuDiscover -> R.id.actionNavigateDiscoverFragment
        R.id.menuShows -> R.id.actionNavigateMyShowsFragment
        else -> throw IllegalStateException("Invalid menu item.")
      }

      uiCache.clear()
      navigationHost.findNavController().navigate(target)
      return@setOnNavigationItemSelectedListener true
    }
  }

  private fun setupNavigationBackHandler() {
    onBackPressedDispatcher.addCallback(this) {
      navigationHost.findNavController().run {
        if (currentDestination?.id == R.id.watchlistFragment) {
          remove()
          super.onBackPressed()
        }
        when (currentDestination?.id) {
          R.id.discoverFragment, R.id.myShowsFragment -> {
            bottomNavigationView.selectedItemId = R.id.menuWatchlist
          }
        }
      }
    }
  }

  fun hideNavigation() {
    bottomNavigationWrapper.animate()
      .translationYBy(navigationHeight.toFloat())
      .setDuration(NAVIGATION_TRANSITION_DURATION_MS)
      .setInterpolator(decelerateInterpolator)
      .start()
  }

  fun showNavigation() {
    bottomNavigationWrapper.animate()
      .translationY(0F)
      .setDuration(NAVIGATION_TRANSITION_DURATION_MS)
      .setInterpolator(decelerateInterpolator)
      .start()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putBoolean(ARG_NAVIGATION_VISIBLE, bottomNavigationWrapper.translationY == 0F)
    super.onSaveInstanceState(outState)
  }

  private fun restoreState(savedInstanceState: Bundle?) {
    val isNavigationVisible = savedInstanceState?.getBoolean(ARG_NAVIGATION_VISIBLE, true) ?: true
    if (!isNavigationVisible) hideNavigation()
  }
}
