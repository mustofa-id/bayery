package id.mustofa.bayery.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.util.GlideRequests
import kotlinx.android.synthetic.main.item_image.view.*

class ImageViewHolder(
  view: View,
  private val glide: GlideRequests
) : RecyclerView.ViewHolder(view) {

  fun bind(image: ImageMin?) {
    with(itemView) {
      image?.let {
        imageUser.text = it.user
        imageLikes.text = it.likes.toString()
        imageComments.text = it.comments.toString()
        glide.load(it.imageURL).into(imageContent)
      }
    }
  }
}