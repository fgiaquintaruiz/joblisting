package com.fgiaquintaruiz.models

import com.google.gson.annotations.SerializedName

data class AwsSecrets(@SerializedName("mongodb_user") val mongoDbUser: String,
                      @SerializedName("mongodb_password") val mongoDbPassword: String) {
}