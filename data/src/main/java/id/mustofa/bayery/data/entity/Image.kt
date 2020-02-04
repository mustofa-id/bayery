/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.entity

import com.google.gson.annotations.SerializedName

data class Image(
  override val id: Long,

  @SerializedName("webformatURL")
  override val imageURL: String,

  val largeImageURL: String,
  val webformatHeight: Int,
  val webformatWidth: Int,
  val likes: Int,
  val imageWidth: Int,

  @SerializedName("user_id")
  val userId: Int,

  val views: Int,
  val comments: Int,
  val pageURL: String,
  val imageHeight: Int,
  val type: String,
  val previewHeight: Int,
  val tags: String,
  val downloads: Int,
  val user: String,
  val favorites: Int,
  val imageSize: Int,
  val previewWidth: Int,
  val userImageURL: String,
  val previewURL: String
) : BaseImage()

