package com.enesaksoy.composecryptos.repo

import com.enesaksoy.composecryptos.model.Crypto
import com.enesaksoy.composecryptos.model.CryptoList
import com.enesaksoy.composecryptos.service.RetrofitApi
import com.enesaksoy.composecryptos.util.Resource
import javax.inject.Inject

class CryptoRepository@Inject constructor(private val api: RetrofitApi) {

    suspend fun getCryptoList(): Resource<CryptoList>{
        val response = try {
            api.getCryptoList()
        }catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(): Resource<Crypto>{
        val response = try {
            api.getCrypto()
        }catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}