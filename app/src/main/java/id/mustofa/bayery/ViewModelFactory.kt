/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.bayery.data.repository.ImageRepository

class ViewModelFactory(
  private val repository: ImageRepository
) : ViewModelProvider.NewInstanceFactory() {

  private val models = mapOf<Class<*>, ViewModel>()

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(model: Class<T>): T {
    return models[model] as T?
      ?: throw IllegalArgumentException("Unknown ViewModel class: $model")
  }
}