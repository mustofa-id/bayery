/*
 * Mustofa on 2/5/20
 * https://mustofa.id
 */
package id.mustofa.bayery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import id.mustofa.bayery.R
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.ui.common.ImageViewHolder
import id.mustofa.bayery.util.GlideRequests

class ImagesAdapter(
  private val glide: GlideRequests
) : PagedListAdapter<ImageMin, ImageViewHolder>(ImageDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    return ImageViewHolder(view, glide)
  }

  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}