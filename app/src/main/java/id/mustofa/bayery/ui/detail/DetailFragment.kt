package id.mustofa.bayery.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import id.mustofa.bayery.R
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.util.GlideApp
import id.mustofa.bayery.util.viewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

  private val args: DetailFragmentArgs by navArgs()
  private val model: DetailViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_detail, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    detailFavButton.setOnClickListener { model.toggleFavorite() }
    subscribeObservers()
  }

  override fun onResume() {
    super.onResume()
    model.load(args.imageId)
  }

  private fun subscribeObservers() {
    model.image.observe(viewLifecycleOwner, ::bindImage)
    model.errorMessage.observe(viewLifecycleOwner) {}
    model.favoriteMessage.observe(viewLifecycleOwner) {}
    model.loading.observe(viewLifecycleOwner) {}
    model.favoriteIcon.observe(viewLifecycleOwner, detailFavButton::setImageResource)
  }

  private fun bindImage(image: Image) {
    GlideApp.with(this).load(image.imageURL).into(detailImage)
    detailToolbar.title = image.user
  }
}
