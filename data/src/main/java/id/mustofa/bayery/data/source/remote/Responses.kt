/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.source.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.mustofa.bayery.data.entity.BaseImage

data class ImageResponse<T : BaseImage>(
  val totalHits: Int,
  val hits: List<T>,
  val total: Int
)

data class Listing<T>(
  val pagedList: LiveData<PagedList<T>>,
  val networkState: LiveData<NetworkState>,
  val refreshState: LiveData<NetworkState>,
  val refresh: () -> Unit,
  val retry: () -> Unit
)

sealed class Result<out E> {
  object Loading : Result<Nothing>()
  data class Success<T>(val data: T) : Result<T>()
  data class Error(val message: String) : Result<Nothing>()
}