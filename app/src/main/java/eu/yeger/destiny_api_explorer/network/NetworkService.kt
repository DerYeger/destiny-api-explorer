package eu.yeger.destiny_api_explorer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface BungieApi {

    @Headers("X-API-Key: 52b973d5b38d4557a2f3ac1b099e9f0b")
    @GET("Destiny2/Vendors/?components=402")
    suspend fun get(@Query("query") query: String): NetworkResponse

    @Headers("X-API-Key: 52b973d5b38d4557a2f3ac1b099e9f0b")
    @GET("Destiny2/Vendors/?components=402")
    suspend fun getXur(): NetworkResponse
}

object NetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.bungie.net/Platform/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val bungieApi: BungieApi = retrofit.create(BungieApi::class.java)
}