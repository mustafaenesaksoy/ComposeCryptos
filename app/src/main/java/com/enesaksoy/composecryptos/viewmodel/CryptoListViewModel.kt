package com.enesaksoy.composecryptos.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.composecryptos.model.CryptoListItem
import com.enesaksoy.composecryptos.repo.CryptoRepository
import com.enesaksoy.composecryptos.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel@Inject constructor(private val repo: CryptoRepository): ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMsg = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadData()
    }

    fun searchCryptoList(query: String){

        val listToSearch = if (isSearchStarting){
            cryptoList.value
        }else{
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                it.currency.contains(query, ignoreCase = true)
            }

            if (isSearchStarting){
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }
            cryptoList.value = results
        }
    }

    fun loadData(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repo.getCryptoList()
            when (result){
                is Resource.Success ->{
                    val cryptoItem = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency, cryptoListItem.price)
                    }
                    isLoading.value = false
                    errorMsg.value = ""
                    cryptoList.value += cryptoItem
                }

                is Resource.Error -> {
                    errorMsg.value = result.message!!
                    isLoading.value = false
                }
                else -> {}
            }
        }
    }
}