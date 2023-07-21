package com.enesaksoy.composecryptos.model


import com.google.gson.annotations.SerializedName

data class CryptoItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("logo_url")
    val logoUrl: String,
    @SerializedName("name")
    val name: String
)