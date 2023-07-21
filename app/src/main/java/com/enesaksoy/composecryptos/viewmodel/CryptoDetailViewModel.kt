package com.enesaksoy.composecryptos.viewmodel

import androidx.lifecycle.ViewModel
import com.enesaksoy.composecryptos.model.Crypto
import com.enesaksoy.composecryptos.repo.CryptoRepository
import com.enesaksoy.composecryptos.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel@Inject constructor(private val repo: CryptoRepository):ViewModel() {

    suspend fun getCrypto(): Resource<Crypto>{
        return repo.getCrypto()
    }
}