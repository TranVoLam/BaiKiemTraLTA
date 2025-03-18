package deso1.tranvolam.dlu_21a100100194.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Food")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val unit: String,
    val imageURL: String,
)