/*
 * Mustofa on 2/5/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.favorite

import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.bayery.adapter.ImagesAdapter
import id.mustofa.bayery.ui.common.ItemsFragment
import id.mustofa.bayery.util.GlideApp
import id.mustofa.bayery.util.viewModel
import kotlinx.android.synthetic.main.fragment_items.*

class FavoriteFragment : ItemsFragment() {

  private val model: FavoriteViewModel by viewModel()
  private lateinit var adapter: ImagesAdapter

  override fun setupViews() {
    adapter = ImagesAdapter(GlideApp.with(this))
    itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    itemsRecyclerView.adapter = adapter
    itemsRefresher.setOnRefreshListener { subscribeObserver() }
  }

  override fun subscribeObserver() {
    model.favorites.observe(viewLifecycleOwner) {
      isEmpty(it.isNullOrEmpty())
      itemsRefresher.isRefreshing = false
      adapter.submitList(it)
    }
  }
}