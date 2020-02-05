/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.bayery.data.repository.ImageRepository

class HomeViewModel(
  private val imageRepo: ImageRepository
) : ViewModel() {

  private val loader = MutableLiveData<Unit>()
  private val listing = map(loader) { imageRepo.getImages(viewModelScope) }

  val images = switchMap(listing) { it.pagedList }
  val networkState = switchMap(listing) { it.networkState }
  val refreshState = switchMap(listing) { it.refreshState }

  init {
    loader.value = Unit
  }

  fun refresh() = listing.value?.refresh?.invoke()

  fun retry() = listing.value?.retry?.invoke()
}