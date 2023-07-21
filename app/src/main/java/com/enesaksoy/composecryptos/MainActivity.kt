package com.enesaksoy.composecryptos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.enesaksoy.composecryptos.ui.theme.ComposeCryptosTheme
import com.enesaksoy.composecryptos.view.CryptoDetailScreen
import com.enesaksoy.composecryptos.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCryptosTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "cryptoListScreen"){
                    composable("cryptoListScreen"){
                        //CryptoListScreen
                        CryptoListScreen(navController = navController)
                    }

                    composable("cryptoDetailScreen/{cryptoId}/{cryptoPrice}", arguments = listOf(
                        navArgument("cryptoId"){
                            type = NavType.StringType
                        },
                        navArgument("cryptoPrice"){
                            type = NavType.StringType
                        }
                    )){
                        val cryptoId = remember {
                            it.arguments?.getString("cryptoId")
                        }

                        val cryptoPrice = remember {
                            it.arguments?.getString("cryptoPrice")
                        }
                        //CryptoDetailScreen
                        CryptoDetailScreen(
                            navController = navController,
                            id = cryptoId ?: "",
                            price = cryptoPrice ?: ""
                        )
                    }
                }
            }
        }
    }
}
