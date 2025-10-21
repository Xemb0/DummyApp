package com.app.harigaji.artical

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticlesViewModel : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _selectedCategory = MutableStateFlow("Trending")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        loadDummyArticles()
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    private fun loadDummyArticles() {
        _articles.value = listOf(
            // 🔥 Trending
            Article(
                id = 1,
                title = "Participate in the Corra Finance Airdrop & Earn Rewards 🎉",
                description = "Join the Corra community and earn up to $50 worth of tokens by completing simple tasks.",
                imageUrl = "https://picsum.photos/seed/airdrop/400/200",
                date = "2024-06-15",
                category = "Trending"
            ),
            Article(
                id = 2,
                title = "Web3 Trends: What’s Next for 2025 🚀",
                description = "Explore the next wave of blockchain innovation shaping the financial world.",
                imageUrl = "https://picsum.photos/seed/web3/400/200",
                date = "2024-06-10",
                category = "Trending"
            ),
            Article(
                id = 3,
                title = "DeFi 3.0: The Future of Decentralized Finance 💎",
                description = "A deep dive into next-gen protocols revolutionizing global finance.",
                imageUrl = "https://picsum.photos/seed/defi/400/200",
                date = "2024-06-08",
                category = "Trending"
            ),
            Article(
                id = 4,
                title = "AI Meets Blockchain: Smarter Finance Systems 🤖",
                description = "Discover how artificial intelligence is enhancing decentralized platforms.",
                imageUrl = "https://picsum.photos/seed/aiblockchain/400/200",
                date = "2024-06-18",
                category = "Trending"
            ),

            // 🕒 Recent
            Article(
                id = 5,
                title = "How to Secure Your Wallet in 2025 🔐",
                description = "Best practices to protect your digital assets from scams and hacks.",
                imageUrl = "https://picsum.photos/seed/security/400/200",
                date = "2024-06-12",
                category = "Recent"
            ),
            Article(
                id = 6,
                title = "Top 10 Airdrops to Watch This Month 💰",
                description = "Don't miss these trending airdrops offering lucrative rewards for early users.",
                imageUrl = "https://picsum.photos/seed/airdrops/400/200",
                date = "2024-06-14",
                category = "Recent"
            ),
            Article(
                id = 7,
                title = "NFT Market Making a Comeback in 2025 🖼️",
                description = "After a slow 2024, NFTs are back — here’s what’s driving the resurgence.",
                imageUrl = "https://picsum.photos/seed/nftcomeback/400/200",
                date = "2024-06-13",
                category = "Recent"
            ),
            Article(
                id = 8,
                title = "Stablecoins: The Backbone of Crypto Payments 💵",
                description = "Learn how stablecoins are reshaping payment systems worldwide.",
                imageUrl = "https://picsum.photos/seed/stablecoin/400/200",
                date = "2024-06-09",
                category = "Recent"
            ),

            // 💾 Saved
            Article(
                id = 9,
                title = "Saved: Corra Finance Airdrop Details",
                description = "Full guide to participation steps and reward eligibility.",
                imageUrl = "https://picsum.photos/seed/saved1/400/200",
                date = "2024-06-15",
                category = "Saved"
            ),
            Article(
                id = 10,
                title = "Saved: Top Crypto Wallets for Beginners 🔑",
                description = "We break down the most secure and beginner-friendly wallets for 2025.",
                imageUrl = "https://picsum.photos/seed/saved2/400/200",
                date = "2024-06-16",
                category = "Saved"
            ),
            Article(
                id = 11,
                title = "Saved: Tax Guide for Crypto Investors in 2025 🧾",
                description = "A simple breakdown of tax regulations and how to stay compliant.",
                imageUrl = "https://picsum.photos/seed/saved3/400/200",
                date = "2024-06-17",
                category = "Saved"
            ),
            Article(
                id = 12,
                title = "Saved: Understanding Gas Fees on Ethereum ⚡",
                description = "Tips to minimize gas fees and optimize your transactions.",
                imageUrl = "https://picsum.photos/seed/saved4/400/200",
                date = "2024-06-18",
                category = "Saved"
            )
        )
    }
}