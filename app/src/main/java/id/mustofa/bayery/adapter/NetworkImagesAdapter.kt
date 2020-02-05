/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.bayery.R
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.source.remote.NetworkState
import id.mustofa.bayery.ui.common.ImageViewHolder
import id.mustofa.bayery.ui.common.StateViewHolder
import id.mustofa.bayery.util.GlideRequests

class NetworkImagesAdapter(
  private val glide: GlideRequests,
  private val retryCallback: () -> Unit
) : PagedListAdapter<ImageMin, RecyclerView.ViewHolder>(ImageDiffCallback) {

  private val itemImage = R.layout.item_image
  private val itemState = R.layout.item_state
  private var networkState: NetworkState? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
    return when (viewType) {
      itemImage -> ImageViewHolder(view, glide)
      itemState -> StateViewHolder(view, retryCallback)
      else -> throw IllegalArgumentException("Unknown view type: $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is StateViewHolder -> holder.bind(networkState)
      is ImageViewHolder -> holder.bind(getItem(position))
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (hasExtraRow() && position == itemCount - 1) itemState else itemImage
  }

  override fun getItemCount(): Int {
    return super.getItemCount() + if (hasExtraRow()) 1 else 0
  }

  private fun hasExtraRow(): Boolean {
    return networkState != null && networkState !is NetworkState.Success
  }

  fun setNetworkState(newNetworkState: NetworkState?) {
    val previousState = this.networkState
    val hadExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val hasExtraRow = hasExtraRow()
    if (hadExtraRow != hasExtraRow) {
      if (hadExtraRow) {
        notifyItemRemoved(super.getItemCount())
      } else {
        notifyItemInserted(super.getItemCount())
      }
    } else if (hasExtraRow && previousState != newNetworkState) {
      notifyItemChanged(itemCount - 1)
    }
  }
}