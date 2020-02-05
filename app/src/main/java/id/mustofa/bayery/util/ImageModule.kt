/*
 * Mustofa on 2/5/20
 * https://mustofa.id
 */
package id.mustofa.bayery.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import id.mustofa.bayery.R

@GlideModule
class ImageModule : AppGlideModule() {

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    super.applyOptions(context, builder)
    val options = RequestOptions()
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      .error(R.drawable.ic_broken_image)
    builder.setDefaultRequestOptions(options)
  }
}