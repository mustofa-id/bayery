/*
 * Mustofa on 2/5/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.mustofa.bayery.ui.favorite.FavoriteFragment
import id.mustofa.bayery.ui.home.HomeFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  companion object {
    const val HOME = 0
    const val FAVORITE = 1
  }

  private val fragmentsCreator = mapOf(
    HOME to { HomeFragment() },
    FAVORITE to { FavoriteFragment() }
  )

  override fun getItemCount() = fragmentsCreator.size

  override fun createFragment(position: Int): Fragment {
    return fragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
  }
}