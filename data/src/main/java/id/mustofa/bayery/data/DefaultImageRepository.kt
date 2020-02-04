/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.paging.ImageDataSourceFactory
import id.mustofa.bayery.data.repository.ImageRepository
import id.mustofa.bayery.data.source.remote.Listing
import id.mustofa.bayery.data.source.remote.PixabayService
import id.mustofa.bayery.data.source.remote.Result
import kotlinx.coroutines.CoroutineScope

class DefaultImageRepository(
  private val pixabayService: PixabayService
) : ImageRepository {

  override fun getImages(scope: CoroutineScope): Listing<ImageMin> {
    val dataSourceFactory = ImageDataSourceFactory(pixabayService, scope)
    val config = PagedList.Config.Builder()
      .setPageSize(20)
      .setEnablePlaceholders(true)
      .build()
    val imagePagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    return Listing(
      pagedList = imagePagedList,
      networkState = switchMap(dataSourceFactory.sourceLiveData) { it.networkState },
      refreshState = switchMap(dataSourceFactory.sourceLiveData) { it.initialState },
      retry = { dataSourceFactory.sourceLiveData.value?.retryAllFailed() },
      refresh = { dataSourceFactory.sourceLiveData.value?.invalidate() }
    )
  }

  override suspend fun getImage(id: Long): Result<Image> {
    TODO("not implemented")
  }
}
