/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.paging.ImageDataSourceFactory
import id.mustofa.bayery.data.repository.ImageRepository
import id.mustofa.bayery.data.source.local.FavoriteDao
import id.mustofa.bayery.data.source.remote.Listing
import id.mustofa.bayery.data.source.remote.PixabayService
import id.mustofa.bayery.data.source.remote.Result
import kotlinx.coroutines.CoroutineScope

class DefaultImageRepository(
  private val pixabayService: PixabayService,
  private val favoriteDao: FavoriteDao
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

  override fun getFavoriteImages(): LiveData<PagedList<ImageMin>> {
    val dataSourceFactory = favoriteDao.selectAll()
    return dataSourceFactory.toLiveData(pageSize = 10)
  }

  override suspend fun getImage(id: Long): Result<Image> {
    return handleError {
      val response = pixabayService.fetchImage(id)
      val image = response.body()?.hits?.first() ?: return Result.Error("No data!")
      Result.Success(image)
    }
  }

  override suspend fun updateFavorite(image: Image) = handleError {
    favoriteDao.updateFavorite(image)
    Result.Success(Unit)
  }

  override suspend fun isFavoriteImage(id: Long) = try {
    favoriteDao.isExists(id) > 0
  } catch (e: Exception) {
    // Not reporting any exception
    false
  }

  private inline fun <T> handleError(block: () -> Result<T>) = try {
    block()
  } catch (e: Exception) {
    val error = e.message ?: "Unknown error!"
    Result.Error(error)
  }
}
