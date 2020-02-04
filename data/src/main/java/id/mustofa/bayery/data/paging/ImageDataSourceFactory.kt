/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.source.remote.PixabayService
import kotlinx.coroutines.CoroutineScope

internal class ImageDataSourceFactory(
  private val pixabayService: PixabayService,
  private val scope: CoroutineScope
) : DataSource.Factory<Int, ImageMin>() {

  val sourceLiveData = MutableLiveData<PageKeyedImageDataSource>()

  override fun create(): DataSource<Int, ImageMin> {
    val dataSource = PageKeyedImageDataSource(pixabayService, scope)
    sourceLiveData.postValue(dataSource)
    return dataSource
  }
}