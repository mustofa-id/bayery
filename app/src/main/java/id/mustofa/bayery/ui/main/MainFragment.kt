package id.mustofa.bayery.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.mustofa.bayery.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_main, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val pagerAdapter = MainPagerAdapter(this)
    mainPager.adapter = pagerAdapter

    TabLayoutMediator(mainTabLayout, mainPager) { tab, pos ->
      when (pos) {
        MainPagerAdapter.HOME -> tab.setText(R.string.title_home)
        MainPagerAdapter.FAVORITE -> tab.setText(R.string.title_favorite)
      }
    }.attach()

    (activity as AppCompatActivity).setSupportActionBar(mainToolbar)
  }
}
