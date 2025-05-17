package com.darasoylu.shopappcompose.domain.usecase.remote_config

import com.darasoylu.shopappcompose.domain.model.PromotionData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import javax.inject.Inject

class FetchRemoteConfigUseCase @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    
    suspend operator fun invoke(): Pair<Boolean, PromotionData?> {
        try {
            // Fetch and activate config
            remoteConfig.fetchAndActivate().await()
            
            // Get the feature_enabled value
            val isFeatureEnabled = remoteConfig.getBoolean("feature_enabled")
            
            // If feature is enabled, parse the promotion details
            val promotionData = if (isFeatureEnabled) {
                val promotionDetailJson = remoteConfig.getString("promotion_detail")
                parsePromotionData(promotionDetailJson)
            } else {
                null
            }
            
            return Pair(isFeatureEnabled, promotionData)
            
        } catch (e: Exception) {
            // In case of error, use default values
            return Pair(false, null)
        }
    }
    
    private fun parsePromotionData(jsonString: String): PromotionData {
        return try {
            val json = JSONObject(jsonString)
            PromotionData(
                title = json.optString("title", ""),
                description = json.optString("description", ""),
                discountAmount = json.optString("discountAmount", ""),
                couponCode = json.optString("couponCode", ""),
                validityText = json.optString("validityText", "")
            )
        } catch (e: Exception) {
            // Return empty data on parse error
            PromotionData()
        }
    }
} 