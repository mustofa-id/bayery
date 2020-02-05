package id.mustofa.bayery.ui.common

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.ui.main.MainFragmentDirections
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

        setOnClickListener { view ->
          val action = MainFragmentDirections.actionToDetail(it.id)
          view.findNavController().navigate(action)
        }
      }
    }
  }
}