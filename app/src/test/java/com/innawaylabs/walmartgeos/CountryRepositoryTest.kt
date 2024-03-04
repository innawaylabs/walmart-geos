package com.innawaylabs.walmartgeos

import com.innawaylabs.walmartgeos.domain.model.Country
import com.innawaylabs.walmartgeos.domain.repository.CountryRepository
import com.innawaylabs.walmartgeos.network.CountryService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class CountryServiceTest {

    private lateinit var countryService: CountryService
    private lateinit var countryRepository: CountryRepository

    @Before
    fun setUp() {
        countryService = mock()
        countryRepository = CountryRepository(countryService)
    }

    @Test
    fun example_Addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun `fetchCountries returns success`() = runTest {
        val mockCountries = listOf(Country("Country1", "Region1", "Code1", "Capital1"))
        whenever(countryService.listCountries()).thenReturn(Response.success(mockCountries))

        val result = countryRepository.fetchCountries()

        result.onSuccess {
            Assert.assertEquals("Country1", it[0].name)
        }.onFailure {
            fail("Expected success but got failure")
        }
    }
}
