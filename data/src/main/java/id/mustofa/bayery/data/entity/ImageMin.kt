package id.mustofa.bayery.data.entity

import com.google.gson.annotations.SerializedName

data class ImageMin(

  override val id: Long,

  @SerializedName("webformatURL")
  override val imageURL: String,

  val user: String,
  val likes: Int,
  val comments: Int
) : BaseImage()