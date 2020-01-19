package eu.yeger.destiny_api_explorer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val BUNGIE_BASE_URL = "https://www.bungie.net/"

const val BUNGIE_API_URL = "${BUNGIE_BASE_URL}platform/"

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface BungieApi {

    @Headers("X-API-Key: 52b973d5b38d4557a2f3ac1b099e9f0b")
    @GET("Destiny2/Manifest/DestinyInventoryItemDefinition/{id}")
    suspend fun getItemDefinition(@Path("id") id: String): NetworkItemDefinition

    @Headers("X-API-Key: 52b973d5b38d4557a2f3ac1b099e9f0b")
//    @GET("Destiny2/Vendors/?components=402")
    @GET("Destiny2/Manifest/DestinyInventoryItemDefinition/3899270607")
    suspend fun getXur(): NetworkItemDefinition
}

object NetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BUNGIE_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val bungieApi: BungieApi = retrofit.create(BungieApi::class.java)
}