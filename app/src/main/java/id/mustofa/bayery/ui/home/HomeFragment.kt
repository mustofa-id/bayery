package id.mustofa.bayery.ui.home


import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.bayery.adapter.NetworkImagesAdapter
import id.mustofa.bayery.data.source.remote.NetworkState
import id.mustofa.bayery.ui.common.ItemsFragment
import id.mustofa.bayery.util.GlideApp
import id.mustofa.bayery.util.viewModel
import kotlinx.android.synthetic.main.fragment_items.*

class HomeFragment : ItemsFragment() {

  private val model: HomeViewModel by viewModel()
  private lateinit var adapter: NetworkImagesAdapter

  override fun setupViews() {
    adapter = NetworkImagesAdapter(GlideApp.with(this)) { model.retry() }
    itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    itemsRecyclerView.adapter = adapter
    itemsRefresher.setOnRefreshListener { model.refresh() }
  }

  override fun subscribeObserver() {
    model.networkState.observe(viewLifecycleOwner, adapter::setNetworkState)
    model.refreshState.observe(viewLifecycleOwner) {
      itemsRefresher.isRefreshing = it is NetworkState.Loading && !isEmpty
    }
    model.images.observe(viewLifecycleOwner) {
      isEmpty(it.isNullOrEmpty())
      adapter.submitList(it)
    }
  }
}