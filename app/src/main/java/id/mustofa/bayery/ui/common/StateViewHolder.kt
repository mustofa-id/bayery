package id.mustofa.bayery.ui.common

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.bayery.data.source.remote.NetworkState
import kotlinx.android.synthetic.main.item_state.view.*

class StateViewHolder(
  view: View,
  private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {

  fun bind(state: NetworkState?) {
    with(itemView) {
      stateProgressBar.isVisible = state is NetworkState.Loading
      if (state is NetworkState.Error) {
        stateRetryBtn.isVisible = true
        stateMessage.isVisible = true
        stateRetryBtn.setOnClickListener { retryCallback() }
        stateMessage.text = state.message
      } else {
        stateRetryBtn.isVisible = false
        stateMessage.isVisible = false
      }
    }
  }
}