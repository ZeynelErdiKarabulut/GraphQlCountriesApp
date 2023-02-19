package com.erdi.graphqlcountriesapp.data

import com.apollographql.apollo3.ApolloClient
import com.erdi.CountriesQuery
import com.erdi.CountryQuery
import com.erdi.graphqlcountriesapp.domain.CountryClient
import com.erdi.graphqlcountriesapp.domain.DetailedCountry
import com.erdi.graphqlcountriesapp.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}