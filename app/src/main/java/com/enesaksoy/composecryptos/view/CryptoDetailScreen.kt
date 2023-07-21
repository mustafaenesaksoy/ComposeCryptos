package com.enesaksoy.composecryptos.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.enesaksoy.composecryptos.model.Crypto
import com.enesaksoy.composecryptos.util.Resource
import com.enesaksoy.composecryptos.viewmodel.CryptoDetailViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CryptoDetailScreen(
    navController: NavController,
    id: String,
    price: String,
    viewModel: CryptoDetailViewModel = hiltViewModel()) {

    /*
            //inefficient method

    var cryptoItem by remember {mutableStateOf<Resource<Crypto>>(Resource.Loading())}
    var scope = rememberCoroutineScope()
    scope.launch {
        cryptoItem = viewModel.getCrypto()
        println(cryptoItem.data)
    }*/

    /*
            //Better method
    var cryptoItem by remember {mutableStateOf<Resource<Crypto>>(Resource.Loading())}
    LaunchedEffect(key1 = Unit){
        cryptoItem = viewModel.getCrypto()
        println(cryptoItem.data)
    }*/
                // Better than method
    var cryptoItem = produceState<Resource<Crypto>>(initialValue = Resource.Loading()){
        value = viewModel.getCrypto()
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            when (cryptoItem){
                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]
                    Text(text = selectedCrypto.name,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                        )

                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedCrypto.logoUrl)
                        .crossfade(true)
                        .build(),
                        contentDescription = selectedCrypto.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp, 200.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                    )
                    Text(text = price,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
        
    }
}