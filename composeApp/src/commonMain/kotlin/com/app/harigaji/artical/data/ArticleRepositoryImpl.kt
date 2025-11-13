package com.app.harigaji.artical.data

import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.artical.domain.ArticleRepository
import kotlinx.coroutines.delay

class ArticleRepositoryImpl : ArticleRepository {

    val dummyArticleDetails = listOf(
        // 🔥 Trending
        ArticleDetail(
            id = 1,
            title = "Participate in the Corra Finance Airdrop & Earn Rewards 🎉",
            description = """
                Corra Finance has announced its latest community engagement program offering up to $50 in CORRA tokens to early adopters. 
                Participants can earn rewards by completing simple verification steps, following Corra’s social media handles, and referring friends to the ecosystem. 
                The initiative aims to boost awareness around decentralized crowdfunding in the DeFi space and support creators looking to launch tokenized campaigns. 
                According to company officials, the airdrop will remain open until the end of the quarter, with users encouraged to verify their wallets to ensure eligibility.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/airdrop/400/200",
            date = "2024-06-15 09:45 AM",
            category = "Trending"
        ),

        ArticleDetail(
            id = 2,
            title = "Web3 Trends: What’s Next for 2025 🚀",
            description = """
                The Web3 ecosystem continues to expand as businesses integrate blockchain technology into mainstream applications. 
                Analysts predict that by 2025, over 40% of Fortune 500 companies will deploy decentralized identity or tokenization services. 
                The transition from speculative crypto assets to practical blockchain infrastructure is already underway, with projects in supply chain, gaming, and digital identity gaining traction. 
                Experts say that mass adoption depends on simplifying user experience and improving cross-chain interoperability.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/web3/400/200",
            date = "2024-06-10 10:15 AM",
            category = "Trending"
        ),

        ArticleDetail(
            id = 3,
            title = "DeFi 3.0: The Future of Decentralized Finance 💎",
            description = """
                The third wave of DeFi innovation—dubbed DeFi 3.0—is redefining how liquidity and yield optimization work. 
                Unlike earlier versions, which focused heavily on staking and farming, this evolution is introducing AI-driven strategies that automatically rebalance portfolios and hedge against risk. 
                Platforms like Yearn V3 and GMX V2 are at the forefront, integrating on-chain data analytics with predictive algorithms. 
                Institutional investors are increasingly exploring these protocols for transparent and high-yield alternatives to traditional banking systems.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/defi/400/200",
            date = "2024-06-08 02:30 PM",
            category = "Trending"
        ),

        ArticleDetail(
            id = 4,
            title = "AI Meets Blockchain: Smarter Finance Systems 🤖",
            description = """
                Artificial Intelligence and blockchain are merging to create intelligent financial systems that learn from data while preserving privacy. 
                Startups in Singapore and Silicon Valley are building AI models that analyze blockchain transaction histories to detect fraud, automate compliance, and predict asset price movements. 
                This new synergy is already being used by decentralized exchanges to improve liquidity forecasting. 
                Experts believe that within the next five years, AI-integrated blockchains could reduce transaction fraud by up to 80%.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/aiblockchain/400/200",
            date = "2024-06-18 06:20 PM",
            category = "Trending"
        ),

        ArticleDetail(
            id = 13,
            title = "Tokenization of Real-World Assets in 2025 🌍",
            description = """
                The financial world is witnessing a paradigm shift as physical assets like real estate, gold, and art are being tokenized on blockchain networks. 
                Tokenization allows fractional ownership, making traditionally expensive assets accessible to a larger pool of investors. 
                Major financial institutions, including BlackRock and HSBC, are piloting tokenized bond programs to test liquidity models in decentralized environments. 
                The trend is expected to unlock trillions of dollars in previously illiquid markets, creating new avenues for cross-border investing.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/tokenization/400/200",
            date = "2024-06-19 11:10 AM",
            category = "Trending"
        ),

        // 🕒 Recent
        ArticleDetail(
            id = 5,
            title = "How to Secure Your Wallet in 2025 🔐",
            description = """
                As digital assets grow in popularity, so do cyber threats targeting crypto users. 
                Recent reports show a 30% increase in phishing scams aimed at wallet recovery phrases. 
                Experts recommend using hardware wallets like Ledger or Trezor, enabling two-factor authentication, and avoiding browser-based extensions for storing private keys. 
                With multi-chain wallets becoming common, users must also ensure permissions are managed correctly to prevent malicious smart contract approvals.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/security/400/200",
            date = "2024-06-12 08:40 AM",
            category = "Recent"
        ),

        ArticleDetail(
            id = 6,
            title = "Top 10 Airdrops to Watch This Month 💰",
            description = """
                Airdrops are back in the spotlight as projects compete for user attention ahead of major token launches. 
                Popular DeFi platforms such as ZetaChain, Scroll, and StarkNet are rumored to distribute tokens soon. 
                Early community members engaging with testnets, governance proposals, and staking mechanisms stand to earn significant rewards. 
                Always verify authenticity before connecting your wallet, as fake airdrops remain a major phishing vector in the crypto community.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/airdrops/400/200",
            date = "2024-06-14 03:25 PM",
            category = "Recent"
        ),

        ArticleDetail(
            id = 15,
            title = "Ethereum Layer-3 Solutions Explained 🔗",
            description = """
                While Layer-2 networks like Arbitrum and Optimism scaled Ethereum transactions, Layer-3 aims to optimize specific use cases—such as gaming, privacy, and enterprise applications. 
                Experts describe it as an ‘application-specific scaling layer’, allowing developers to fine-tune security and cost trade-offs. 
                Coinbase’s Base network and zkSync’s upcoming L3 are among those pioneering this space, promising lower fees and faster settlement times for mainstream adoption.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/layer3/400/200",
            date = "2024-06-21 01:05 PM",
            category = "Recent"
        ),

        // 💾 Saved
        ArticleDetail(
            id = 9,
            title = "Saved: Corra Finance Airdrop Details",
            description = """
                The Corra Finance Airdrop has attracted over 50,000 participants worldwide within its first week. 
                The platform is promoting content creators who tokenize their work and allow fans to invest directly. 
                Users can complete in-app challenges, share referral links, and engage in staking pools to maximize their airdrop eligibility. 
                According to the team, this event marks the beginning of Corra’s mission to democratize creator economies through blockchain integration.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/saved1/400/200",
            date = "2024-06-15 10:30 AM",
            category = "Saved"
        ),

        ArticleDetail(
            id = 10,
            title = "Saved: Top Crypto Wallets for Beginners 🔑",
            description = """
                For newcomers entering the crypto space, choosing the right wallet is crucial. 
                Hardware wallets like Ledger Nano X remain the gold standard for security, while software options like MetaMask and Trust Wallet offer convenience for everyday transactions. 
                Beginners should start with small amounts, avoid public Wi-Fi when making transfers, and always keep their seed phrases offline. 
                The article also explores decentralized identity wallets, a growing trend among Web3 users.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/saved2/400/200",
            date = "2024-06-16 01:45 PM",
            category = "Saved"
        ),

        ArticleDetail(
            id = 20,
            title = "Saved: Best Crypto Podcasts to Follow 🎧",
            description = """
                Staying informed is essential for crypto investors. 
                Podcasts like “The Defiant,” “Unchained” by Laura Shin, and “Bankless” continue to provide top-tier discussions around blockchain trends, regulation, and innovation. 
                Whether you’re a developer, trader, or simply curious, these shows break down complex concepts into digestible insights. 
                Newcomers are especially encouraged to listen weekly as the industry’s narrative changes rapidly.
            """.trimIndent(),
            imageUrl = "https://picsum.photos/seed/saved7/400/200",
            date = "2024-06-21 04:15 PM",
            category = "Saved"
        )
    )

    override suspend fun refreshArticles() {
        delay(500)
    }

    override suspend fun getArticles(): List<ArticleDetail> {
        delay(400)
        return dummyArticleDetails
    }

    override suspend fun getArticlesByCategory(category: String): List<ArticleDetail> {
        delay(300)
        return dummyArticleDetails.filter { it.category.equals(category, ignoreCase = true) }
    }
}