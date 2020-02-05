/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.bayery.data.repository.ImageRepository
import id.mustofa.bayery.ui.detail.DetailViewModel
import id.mustofa.bayery.ui.favorite.FavoriteViewModel
import id.mustofa.bayery.ui.home.HomeViewModel

class ViewModelFactory(
  repository: ImageRepository
) : ViewModelProvider.NewInstanceFactory() {

  private val models = mapOf(
    HomeViewModel::class.java to HomeViewModel(repository),
    FavoriteViewModel::class.java to FavoriteViewModel(repository),
    DetailViewModel::class.java to DetailViewModel(repository)
  )

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(model: Class<T>): T {
    return models[model] as T?
      ?: throw IllegalArgumentException("Unknown ViewModel class: $model")
  }
}