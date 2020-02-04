/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.repository

import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.entity.ImageMin
import id.mustofa.bayery.data.source.remote.Listing
import id.mustofa.bayery.data.source.remote.Result
import kotlinx.coroutines.CoroutineScope

interface ImageRepository {

  fun getImages(scope: CoroutineScope): Listing<ImageMin>

  suspend fun getImage(id: Long): Result<Image>
}