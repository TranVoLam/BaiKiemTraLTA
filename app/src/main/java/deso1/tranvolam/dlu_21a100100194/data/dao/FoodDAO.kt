package deso1.tranvolam.dlu_21a100100194.data.dao

import androidx.room.*
import deso1.tranvolam.dlu_21a100100194.data.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Query("SELECT * FROM Food ORDER BY id ASC")
    fun getAllFoods(): Flow<List<Food>>
}