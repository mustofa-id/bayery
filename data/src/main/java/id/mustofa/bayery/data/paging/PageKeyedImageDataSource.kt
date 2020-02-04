/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.source.remote.NetworkState
import id.mustofa.bayery.data.source.remote.PixabayService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class PageKeyedImageDataSource(
  private val pixabayService: PixabayService,
  private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ImageMin>() {

  val networkState = MutableLiveData<NetworkState>()
  val initialState = MutableLiveData<NetworkState>()

  private var retryCallback: (() -> Any)? = null

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, ImageMin>
  ) {
    NetworkState.Loading.run {
      networkState.postValue(this)
      initialState.postValue(this)
    }

    val startPage = 1
    val nextPage = startPage + 1
    val limit = params.requestedLoadSize
    scope.launch {
      try {
        val response = pixabayService.fetchAllImages(startPage, limit)
        val images = response.body()?.hits ?: emptyList()
        retryCallback = null
        NetworkState.Success.run {
          networkState.postValue(this)
          initialState.postValue(this)
        }
        callback.onResult(images, startPage, nextPage)
      } catch (e: Exception) {
        retryCallback = { loadInitial(params, callback) }
        val error = e.message ?: "Unknown error!"
        NetworkState.Error(error).run {
          networkState.postValue(this)
          initialState.postValue(this)
        }
      }
    }
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, ImageMin>
  ) {
    networkState.postValue(NetworkState.Loading)
    val currentPage = params.key
    val nextPage = currentPage + 1
    val limit = params.requestedLoadSize
    scope.launch {
      try {
        val response = pixabayService.fetchAllImages(currentPage, limit)
        val images = response.body()?.hits ?: emptyList()
        retryCallback = null
        networkState.postValue(NetworkState.Success)
        callback.onResult(images, nextPage)
      } catch (e: Exception) {
        retryCallback = { loadAfter(params, callback) }
        val error = e.message ?: "Unknown error!"
        networkState.postValue(NetworkState.Error(error))
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, ImageMin>
  ) {
    // not used
  }

  fun retryAllFailed() {
    val prevRetry = retryCallback
    retryCallback = null
    prevRetry?.let {
      scope.launch { it.invoke() }
    }
  }
}
