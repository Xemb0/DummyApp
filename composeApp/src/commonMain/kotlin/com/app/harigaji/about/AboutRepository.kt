package com.app.harigaji.about

import com.app.harigaji.presentation.tabs.KpiData

interface AboutRepository {
    suspend fun getPrivacyPolicySections(): Result<List<KpiData>>
    suspend fun getTermsAndConditionsSections(): Result<List<KpiData>>
}
