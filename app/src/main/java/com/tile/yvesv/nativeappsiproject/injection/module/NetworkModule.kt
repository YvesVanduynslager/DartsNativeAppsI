package com.tile.yvesv.nativeappsiproject.injection.module

import com.tile.yvesv.nativeappsiproject.networking.BoredActApi
import com.tile.yvesv.nativeappsiproject.util.BASE_URL

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provides all required dependencies for the network.
 *
 * Object: Singleton instance (https://kotlinlang.org/docs/reference/object-declarations.html)
 *
 * Retrofit: library used for REST connections (https://square.github.io/retrofit/)
 *
 * Methods annotated with @Provides informs Dagger that this method is the constructor
 */
@Module
object NetworkModule
{
    /**
     * Provides the [BoredActApi] service implementation
     * @param retrofit the retrofit object used to instantiate the service
     */
    @Provides
    internal fun provideBoredActivityApi(retrofit: Retrofit): BoredActApi
    {
        return retrofit.create(BoredActApi::class.java)
    }

    /**
     * Return the Retrofit object.
     */
    @Provides
    internal fun provideRetrofitInterface(): Retrofit
    {
        //To debug Retrofit/OkHttp we can intercept the calls and log them.
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(loggingInterceptor)
        }.build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}