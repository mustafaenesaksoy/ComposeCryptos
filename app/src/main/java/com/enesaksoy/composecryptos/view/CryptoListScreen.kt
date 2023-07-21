package com.enesaksoy.composecryptos.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesaksoy.composecryptos.model.CryptoListItem
import com.enesaksoy.composecryptos.viewmodel.CryptoListViewModel

@Composable
fun CryptoListScreen(navController: NavController,viewModel: CryptoListViewModel = hiltViewModel()) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxSize()
    ) {
        
        Column {
            Text(text = "Crypto Crazy",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp
            )
            //Search Bar
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                hint = "Search..."
            ){
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            ObserveOn(navController = navController)
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier, hint: String, onSearch : (String) -> Unit = {}) {

    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        BasicTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
        },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp)
                .background(Color.White, CircleShape)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if (isHintDisplayed){
            Text(text = hint, color = Color.LightGray, modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp))
        }
    }
}

@Composable
fun ObserveOn(navController: NavController, viewModel: CryptoListViewModel = hiltViewModel()) {
    val cryptoList by remember {viewModel.cryptoList}
    val errorMsg by remember {viewModel.errorMsg}
    val isLoading by remember {viewModel.isLoading}
    
    CryptoList(cryptos = cryptoList, navController = navController)
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if (isLoading){
            CircularProgressIndicator(color = Color.Black)
        }
        if (errorMsg.isNotEmpty()){
            RetryView(msg = errorMsg) {
                viewModel.loadData()
            }
        }
    }
}

@Composable
fun CryptoList(cryptos: List<CryptoListItem>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptos){crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}

@Composable
fun CryptoRow(navController: NavController, crypto: CryptoListItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .clickable {
            navController.navigate("cryptoDetailScreen/${crypto.currency}/${crypto.price}")
        }) {

        Text(text = crypto.currency,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
            )

        Text(text = crypto.price,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(2.dp),
            color = Color.Black
        )
    }
}

@Composable
fun RetryView(msg: String, onRetry: () -> Unit) {
    Column {
        Text(text = msg, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}