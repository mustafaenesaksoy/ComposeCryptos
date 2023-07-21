package com.enesaksoy.composecryptos.di

import com.enesaksoy.composecryptos.repo.CryptoRepository
import com.enesaksoy.composecryptos.service.RetrofitApi
import com.enesaksoy.composecryptos.util.Consts.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun injectRetrofit(): RetrofitApi{
        return Retrofit.Builder().
        baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build().
        create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun injectRepo(api: RetrofitApi) = CryptoRepository(api)

}