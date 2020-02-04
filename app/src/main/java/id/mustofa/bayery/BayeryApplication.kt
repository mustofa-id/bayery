/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery

import android.app.Application
import id.mustofa.bayery.data.ServiceLocator
import id.mustofa.bayery.data.repository.ImageRepository

class BayeryApplication : Application() {

  val imageRepository: ImageRepository
    get() = ServiceLocator.provideImageRepository(this)
}