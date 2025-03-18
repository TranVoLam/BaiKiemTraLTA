package deso1.tranvolam.dlu_21a100100194.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import deso1.tranvolam.dlu_21a100100194.data.model.Food
import deso1.tranvolam.dlu_21a100100194.data.dao.FoodDAO

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun FoodDao() : FoodDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}