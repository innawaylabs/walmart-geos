package com.innawaylabs.walmartgeos

import com.innawaylabs.walmartgeos.network.CountryService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CountryServiceTest {

    private lateinit var service: CountryService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)
    }

    @Test
    fun `list countries fetches data correctly`() {
        // Prepare a mock response
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("[{\"name\":\"Country1\",\"region\":\"Region1\",\"code\":\"Code1\",\"capital\":\"Capital1\"}]")
        mockWebServer.enqueue(mockResponse)

        // Execute the call
        val response = service.listCountries().execute()

        // Assert the response data
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
        val countries = response.body()!!
        Assert.assertEquals(1, countries.size)
        Assert.assertEquals("Country1", countries[0].name)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
