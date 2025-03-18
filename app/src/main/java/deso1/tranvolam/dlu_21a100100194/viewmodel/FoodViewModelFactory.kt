import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import deso1.tranvolam.dlu_21a100100194.repository.FoodRepository
import deso1.tranvolam.dlu_21a100100194.viewmodel.FoodViewModel

class FoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
