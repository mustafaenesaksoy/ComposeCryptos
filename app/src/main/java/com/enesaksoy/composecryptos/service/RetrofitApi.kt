package com.enesaksoy.composecryptos.service

import com.enesaksoy.composecryptos.model.Crypto
import com.enesaksoy.composecryptos.model.CryptoList
import retrofit2.http.GET

interface RetrofitApi {

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptoList(): CryptoList

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/crypto.json")
    suspend fun getCrypto(): Crypto
}