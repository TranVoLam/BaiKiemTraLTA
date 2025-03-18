package deso1.tranvolam.dlu_21a100100194

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import deso1.tranvolam.dlu_21a100100194.viewmodel.FoodViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import deso1.tranvolam.dlu_21a100100194.data.model.Food
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FoodViewModel = viewModel()
            FoodScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun FoodScreen(viewModel: FoodViewModel) {
    val foods by viewModel.allFoods.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(foods.size) { index ->
                val food = foods[index]
                FoodItem(food, onEditClick = {
                    selectedFood = food
                    showDialog = true
                })
            }
        }

        FloatingActionButton(
            onClick = {
                selectedFood = null // Khi thêm mới, không có món nào được chọn
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Thêm")
        }

        if (showDialog) {
            EditFoodDialog(
                food = selectedFood,
                onDismiss = { showDialog = false },
                onSaveFood = { name, price, imageUri ->
                    if (selectedFood == null) {
                        // Thêm mới món ăn
                        val newFood = Food(name = name, price = price, unit = "VND", imageURL = imageUri.toString())
                        viewModel.insert(newFood)
                    } else {
                        // Cập nhật món ăn hiện tại
                        val updatedFood = selectedFood!!.copy(name = name, price = price, imageURL = imageUri.toString())
                        viewModel.update(updatedFood)
                    }
                    showDialog = false
                }
            )
        }
    }
}


@Composable
fun FoodItem(food: Food, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(food.imageURL),
            contentDescription = "Food Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = food.name, fontWeight = FontWeight.Bold)
            Text(text = "${food.price} VND", color = Color.Gray)
        }
        IconButton(onClick = onEditClick) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Chỉnh sửa")
        }
    }
}


@Composable
fun EditFoodDialog(
    food: Food?,
    onDismiss: () -> Unit,
    onSaveFood: (String, Double, Uri?) -> Unit
) {
    var name by remember { mutableStateOf(food?.name ?: "") }
    var price by remember { mutableStateOf(food?.price?.toString() ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(food?.imageURL?.let { Uri.parse(it) }) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (food == null) "Thêm Món Ăn" else "Chỉnh Sửa Món Ăn", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên món ăn") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Giá món ăn") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .clickable { galleryLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("Chọn ảnh", fontSize = 14.sp, color = Color.DarkGray)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onDismiss) {
                        Text("Hủy")
                    }
                    Button(onClick = {
                        val parsedPrice = price.toDoubleOrNull() ?: 0.0
                        onSaveFood(name, parsedPrice, imageUri)
                    }) {
                        Text("Lưu")
                    }
                }
            }
        }
    }
}




