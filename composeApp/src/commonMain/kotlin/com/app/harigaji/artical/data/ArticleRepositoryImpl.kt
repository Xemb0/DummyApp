package com.app.harigaji.artical.data

import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.artical.domain.ArticleRepository
import kotlinx.coroutines.delay

class ArticleRepositoryImpl : ArticleRepository {

     val dummyArticleDetails = listOf(
        ArticleDetail(
            id = 1,
            title = "Participate in the Corra Finance Airdrop & Earn Rewards 🎉",
            description = "Join the Corra community and earn up to $50 worth of tokens by completing simple tasks.",
            imageUrl = "https://picsum.photos/seed/airdrop/400/200",
            date = "2024-06-15 09:45 AM",
            category = "Trending"
        ),
        ArticleDetail(
            id = 2,
            title = "Web3 Trends: What’s Next for 2025 🚀",
            description = "Explore the next wave of blockchain innovation shaping the financial world.",
            imageUrl = "https://picsum.photos/seed/web3/400/200",
            date = "2024-06-10 10:15 AM",
            category = "Trending"
        ),
        ArticleDetail(
            id = 3,
            title = "DeFi 3.0: The Future of Decentralized Finance 💎",
            description = "A deep dive into next-gen protocols revolutionizing global finance.",
            imageUrl = "https://picsum.photos/seed/defi/400/200",
            date = "2024-06-08 02:30 PM",
            category = "Trending"
        ),
        ArticleDetail(
            id = 4,
            title = "AI Meets Blockchain: Smarter Finance Systems 🤖",
            description = "Discover how artificial intelligence is enhancing decentralized platforms.",
            imageUrl = "https://picsum.photos/seed/aiblockchain/400/200",
            date = "2024-06-18 06:20 PM",
            category = "Trending"
        ),
        ArticleDetail(
            id = 13,
            title = "Tokenization of Real-World Assets in 2025 🌍",
            description = "Explore how real estate and physical assets are going digital.",
            imageUrl = "https://picsum.photos/seed/tokenization/400/200",
            date = "2024-06-19 11:10 AM",
            category = "Trending"
        ),
        ArticleDetail(
            id = 14,
            title = "The Rise of AI Agents in Finance 🤯",
            description = "Autonomous AI traders are reshaping investment strategies.",
            imageUrl = "https://picsum.photos/seed/aiagents/400/200",
            date = "2024-06-20 04:50 PM",
            category = "Trending"
        ),

        // 🕒 Recent
        ArticleDetail(
            id = 5,
            title = "How to Secure Your Wallet in 2025 🔐",
            description = "Best practices to protect your digital assets from scams and hacks.",
            imageUrl = "https://picsum.photos/seed/security/400/200",
            date = "2024-06-12 08:40 AM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 6,
            title = "Top 10 Airdrops to Watch This Month 💰",
            description = "Don't miss these trending airdrops offering lucrative rewards for early users.",
            imageUrl = "https://picsum.photos/seed/airdrops/400/200",
            date = "2024-06-14 03:25 PM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 7,
            title = "NFT Market Making a Comeback in 2025 🖼️",
            description = "After a slow 2024, NFTs are back — here’s what’s driving the resurgence.",
            imageUrl = "https://picsum.photos/seed/nftcomeback/400/200",
            date = "2024-06-13 11:30 AM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 8,
            title = "Stablecoins: The Backbone of Crypto Payments 💵",
            description = "Learn how stablecoins are reshaping payment systems worldwide.",
            imageUrl = "https://picsum.photos/seed/stablecoin/400/200",
            date = "2024-06-09 09:10 PM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 15,
            title = "Ethereum Layer-3 Solutions Explained 🔗",
            description = "A closer look at the third layer of blockchain scalability and privacy.",
            imageUrl = "https://picsum.photos/seed/layer3/400/200",
            date = "2024-06-21 01:05 PM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 16,
            title = "Crypto Regulation Updates for 2025 📜",
            description = "What global regulatory changes mean for investors and startups.",
            imageUrl = "https://picsum.photos/seed/regulations/400/200",
            date = "2024-06-22 07:55 PM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 17,
            title = "How to Use Crypto for Everyday Purchases 🛒",
            description = "Step-by-step guide to paying for groceries, bills, and more using crypto.",
            imageUrl = "https://picsum.photos/seed/cryptopayments/400/200",
            date = "2024-06-23 09:30 AM",
            category = "Recent"
        ),
        ArticleDetail(
            id = 9,
            title = "Saved: Corra Finance Airdrop Details",
            description = "Full guide to participation steps and reward eligibility.",
            imageUrl = "https://picsum.photos/seed/saved1/400/200",
            date = "2024-06-15 10:30 AM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 10,
            title = "Saved: Top Crypto Wallets for Beginners 🔑",
            description = "We break down the most secure and beginner-friendly wallets for 2025.",
            imageUrl = "https://picsum.photos/seed/saved2/400/200",
            date = "2024-06-16 01:45 PM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 11,
            title = "Saved: Tax Guide for Crypto Investors in 2025 🧾",
            description = "A simple breakdown of tax regulations and how to stay compliant.",
            imageUrl = "https://picsum.photos/seed/saved3/400/200",
            date = "2024-06-17 05:10 PM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 12,
            title = "Saved: Understanding Gas Fees on Ethereum ⚡",
            description = "Tips to minimize gas fees and optimize your transactions.",
            imageUrl = "https://picsum.photos/seed/saved4/400/200",
            date = "2024-06-18 07:25 PM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 18,
            title = "Saved: How to Track Portfolio Performance 📈",
            description = "Use smart dashboards and analytics tools to monitor your crypto assets.",
            imageUrl = "https://picsum.photos/seed/saved5/400/200",
            date = "2024-06-19 09:50 AM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 19,
            title = "Saved: Decoding Bitcoin Halving Events ⛏️",
            description = "Understand the long-term impact of Bitcoin halving on market behavior.",
            imageUrl = "https://picsum.photos/seed/saved6/400/200",
            date = "2024-06-20 12:40 PM",
            category = "Saved"
        ),
        ArticleDetail(
            id = 20,
            title = "Saved: Best Crypto Podcasts to Follow 🎧",
            description = "Stay informed and entertained with top-rated crypto and finance podcasts.",
            imageUrl = "https://picsum.photos/seed/saved7/400/200",
            date = "2024-06-21 04:15 PM",
            category = "Saved"
        )
    )

    override suspend fun refreshArticles() {

    }

    override suspend fun getArticles(): List<ArticleDetail> {
        delay(500)
        return dummyArticleDetails
    }

    override suspend fun getArticlesByCategory(category: String): List<ArticleDetail> {
        delay(300)
        return dummyArticleDetails.filter { it.category.equals(category, ignoreCase = true) }
    }
}