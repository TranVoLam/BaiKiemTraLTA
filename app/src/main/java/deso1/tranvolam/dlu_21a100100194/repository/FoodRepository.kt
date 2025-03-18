package deso1.tranvolam.dlu_21a100100194.repository

import deso1.tranvolam.dlu_21a100100194.data.dao.FoodDAO
import deso1.tranvolam.dlu_21a100100194.data.model.Food
import kotlinx.coroutines.flow.Flow

class FoodRepository (private val foodDAO: FoodDAO) {
    val allFoods: Flow<List<Food>> = foodDAO.getAllFoods()

    suspend fun insert(food: Food) {
        foodDAO.insertFood(food)
    }

    suspend fun update(food: Food) {
        foodDAO.updateFood(food)
    }
}