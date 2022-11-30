package ddd.magdy.movieapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ddd.magdy.movieapp.BuildConfig
import ddd.magdy.movieapp.api.ApiService
import ddd.magdy.movieapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val logginInterceptor = HttpLoggingInterceptor()
            logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", Constants.API_KEY)
                    .build();
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addInterceptor(interceptor = logginInterceptor)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder().build()
        }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)

}











