package com.app.harigaji.about

import com.app.harigaji.presentation.tabs.KpiData

class AboutRepositoryImpl : AboutRepository {


    override suspend fun getPrivacyPolicySections(): Result<List<KpiData>> {
        return try {
            // Simulate network delay
            kotlinx.coroutines.delay(500)

            val sections = listOf(
                KpiData(
                    title = "1. Data Collection",
                    content = "We collect personal information such as your name, email address, and usage data to improve our services."
                ),
                KpiData(
                    title = "2. Data Usage",
                    content = "Your data is used to personalize your experience, provide customer support, and send important updates."
                ),
                KpiData(
                    title = "3. Data Sharing",
                    content = "We do not share your personal information with third parties without your consent, except as required by law."
                ),
                KpiData(
                    title = "4. Data Security",
                    content = "We implement industry-standard security measures to protect your data from unauthorized access and breaches."
                ),
                KpiData(
                    title = "5. User Rights",
                    content = "You have the right to access, modify, or delete your personal information at any time by contacting our support team."
                ),
                KpiData(
                    title = "6. Cookies",
                    content = "We use cookies to enhance your browsing experience and gather analytics about site usage."
                ),
                KpiData(
                    title = "7. Changes to This Policy",
                    content = "We may update our Privacy Policy periodically. We will notify you of any significant changes via email or through our app."
                )
            )

            Result.success(sections)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTermsAndConditionsSections(): Result<List<KpiData>> {
        return try {
            // Simulate network delay
            kotlinx.coroutines.delay(500)
            
            val sections = listOf(
                KpiData(
                    title = "1. Acceptance of Terms",
                    content = "By accessing and using this service, you accept and agree to be bound by the terms and provision of this agreement."
                ),
                KpiData(
                    title = "2. Use License",
                    content = "Permission is granted to temporarily download one copy of the materials for personal, non-commercial transitory viewing only."
                ),
                KpiData(
                    title = "3. User Account",
                    content = "You are responsible for maintaining the confidentiality of your account and password and for restricting access to your device."
                ),
                KpiData(
                    title = "4. Prohibited Uses",
                    content = "You may not use our service for any illegal or unauthorized purpose nor may you violate any laws in your jurisdiction."
                ),
                KpiData(
                    title = "5. Intellectual Property",
                    content = "All content included in or made available through our service is the property of the company and is protected by copyright laws."
                ),
                KpiData(
                    title = "6. Termination",
                    content = "We may terminate or suspend your account immediately, without prior notice or liability, for any reason whatsoever."
                ),
                KpiData(
                    title = "7. Limitation of Liability",
                    content = "In no event shall the company be liable for any indirect, incidental, special, consequential or punitive damages."
                ),
                KpiData(
                    title = "8. Changes to Terms",
                    content = "We reserve the right to modify or replace these terms at any time. We will provide notice of any significant changes."
                )
            )
            
            Result.success(sections)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
