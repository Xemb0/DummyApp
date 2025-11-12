package com.app.harigaji.about

import com.app.harigaji.navigation.Route
import com.app.harigaji.presentation.tabs.KpiData

data class PolicyState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val sections: List<KpiData> = emptyList()
)

data class TermsAndConditionsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val termsAndConditions: List<KpiData>  = emptyList()
)