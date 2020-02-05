/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.bayery.R
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.repository.ImageRepository
import id.mustofa.bayery.data.source.remote.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
  private val imageRepo: ImageRepository
) : ViewModel() {

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  private val _image = MutableLiveData<Image>()
  val image: LiveData<Image> = _image

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = _errorMessage

  private val _favoriteMessage = MutableLiveData<Int>()
  val favoriteMessage: LiveData<Int> = _favoriteMessage

  private val _favoriteIcon = MutableLiveData<Int>()
  val favoriteIcon: LiveData<Int> = _favoriteIcon

  fun load(id: Long) {
    _loading.value = true
    checkFavorite(id)
    viewModelScope.launch {
      when (val result = imageRepo.getImage(id)) {
        is Result.Success -> _image.postValue(result.data)
        is Result.Error -> _errorMessage.postValue(result.message)
      }
      _loading.postValue(false)
    }
  }

  fun toggleFavorite() {
    if (_loading.value == true) {
      _favoriteMessage.value = R.string.msg_please_wait
    } else {
      _image.value?.let {
        viewModelScope.launch(Dispatchers.IO) {
          when (imageRepo.updateFavorite(it)) {
            is Result.Success -> checkFavorite(it.id)
            is Result.Error -> _favoriteMessage.postValue(R.string.mgs_favorite_failed)
          }
        }
      }
    }
  }

  private fun checkFavorite(id: Long) {
    viewModelScope.launch(Dispatchers.IO) {
      val fav = imageRepo.isFavoriteImage(id)
      Log.e(javaClass.name, "checkFavorite: $fav")
      val icon = if (fav) R.drawable.ic_favorite else R.drawable.ic_unfavorite
      _favoriteIcon.postValue(icon)
    }
  }
}