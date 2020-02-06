/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.favorite

import androidx.lifecycle.ViewModel
import id.mustofa.bayery.data.repository.ImageRepository

class FavoriteViewModel(
  imageRepo: ImageRepository
) : ViewModel() {

  val favorites = imageRepo.getFavoriteImages()
}