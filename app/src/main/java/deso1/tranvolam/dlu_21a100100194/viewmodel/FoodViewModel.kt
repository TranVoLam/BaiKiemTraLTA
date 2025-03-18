package deso1.tranvolam.dlu_21a100100194.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import deso1.tranvolam.dlu_21a100100194.data.model.Food
import deso1.tranvolam.dlu_21a100100194.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    val allFoods : Flow<List<Food>> = repository.allFoods

    fun insert(food: Food) = viewModelScope.launch {
        repository.insert(food)
    }

    fun update(food: Food) = viewModelScope.launch {
        repository.update(food)
    }

}
