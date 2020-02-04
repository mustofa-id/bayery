/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.source.remote

import id.mustofa.bayery.data.BuildConfig
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.entity.ImageMin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

  @GET("api/?key=${BuildConfig.PIXABAY_API_KEY}")
  suspend fun fetchAllImages(
    @Query("page") page: Int,
    @Query("per_page") limit: Int
  ): Response<ImageResponse<ImageMin>>

  @GET("api/?key=${BuildConfig.PIXABAY_API_KEY}")
  suspend fun fetchImage(
    @Query("id") id: Long
  ): Response<ImageResponse<Image>>
}