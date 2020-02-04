/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.bayery.BayeryApplication
import id.mustofa.bayery.ViewModelFactory

inline fun <reified T : ViewModel> Fragment.viewModel() = lazy {
  val appContext = requireActivity().application.applicationContext
  val repositories = (appContext as BayeryApplication).imageRepository
  val viewModelFactory = ViewModelFactory(repositories)
  ViewModelProvider(requireActivity(), viewModelFactory)[T::class.java]
}