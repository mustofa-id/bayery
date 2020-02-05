/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data

import android.content.Context
import com.google.gson.GsonBuilder
import id.mustofa.bayery.data.repository.ImageRepository
import id.mustofa.bayery.data.source.local.AppDatabase
import id.mustofa.bayery.data.source.local.FavoriteDao
import id.mustofa.bayery.data.source.remote.PixabayService
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {

  @Volatile
  private var imageRepository: ImageRepository? = null

  fun provideImageRepository(context: Context) = imageRepository ?: synchronized(this) {
    imageRepository ?: DefaultImageRepository(
      pixabayService = createPixabayService(context),
      favoriteDao = createFavoriteDao(context)
    )
  }

  private fun createPixabayService(context: Context): PixabayService {
    val baseUrl = "https://pixabay.com/"

    val cacheSize = (10 * 1024 * 1024).toLong()
    val cache = Cache(context.cacheDir, cacheSize)

    // From https://pixabay.com/api/docs/,
    // "To keep the Pixabay API fast for everyone,
    // requests must be cached for 24 hours"
    val cacheControl = CacheControl.Builder()
      .maxStale(24, TimeUnit.HOURS)
      .build()

    val okHttpClient = OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)
      .cache(cache)
      .addInterceptor { chain ->
        val request = chain
          .request()
          .newBuilder()
          .cacheControl(cacheControl)
          .build()
        chain.proceed(request)
      }.build()

    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .client(okHttpClient)
      .build()
      .create(PixabayService::class.java)
  }

  private fun createFavoriteDao(context: Context): FavoriteDao {
    val database = AppDatabase(context)
    return database.favoriteDao()
  }
}