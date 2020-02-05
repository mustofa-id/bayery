/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.mustofa.bayery.data.entity.Image

@Database(entities = [Image::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  companion object {

    @Volatile
    private var instance: AppDatabase? = null
    private const val databaseName = "database.db"

    operator fun invoke(context: Context) = instance ?: synchronized(this) {
      instance ?: Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
    }
  }

  abstract fun favoriteDao(): FavoriteDao
}