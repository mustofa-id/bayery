/*
 * Mustofa on 2/5/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.mustofa.bayery.R

abstract class ItemsFragment : Fragment() {

  protected var isEmpty = true

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_items, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    subscribeObserver()
  }

  // TODO: Handle empty view
  fun isEmpty(constraint: Boolean) {
    isEmpty = constraint
  }

  protected abstract fun setupViews()

  protected open fun subscribeObserver() {}
}