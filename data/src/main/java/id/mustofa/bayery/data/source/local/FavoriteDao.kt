/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import id.mustofa.bayery.data.entity.Image
import id.mustofa.bayery.data.entity.ImageMin

@Dao
interface FavoriteDao {

  @Query("SELECT id, imageURL, user, likes, comments FROM pixabay_images")
  fun selectAll(): DataSource.Factory<Int, ImageMin>

  @Query("SELECT COUNT(id) FROM pixabay_images WHERE id=:id")
  fun isExists(id: Long): Int

  @Insert
  fun insert(image: Image): Long

  @Delete
  fun delete(image: Image): Int

  @Transaction
  fun updateFavorite(image: Image) {
    val exists = isExists(image.id) > 0
    if (exists) delete(image) else insert(image)
  }
}