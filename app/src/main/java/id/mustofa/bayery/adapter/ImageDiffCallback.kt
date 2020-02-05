package id.mustofa.bayery.adapter

import androidx.recyclerview.widget.DiffUtil
import id.mustofa.bayery.data.entity.ImageMin

object ImageDiffCallback : DiffUtil.ItemCallback<ImageMin>() {
  override fun areItemsTheSame(old: ImageMin, new: ImageMin) = old.id == new.id
  override fun areContentsTheSame(old: ImageMin, new: ImageMin) = old == new
}