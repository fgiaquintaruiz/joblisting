package com.fgiaquintaruiz.config

// Use this code snippet in your app.
// If you need more information about configurations or implementing the sample
// code, visit the AWS docs:
// https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html
// Make sure to import the following packages in your code
import com.fgiaquintaruiz.models.AwsSecrets
import com.google.gson.Gson
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse

class AwsSecretsHandler {

    companion object Secrets {
        fun getSecret() : AwsSecrets {
            val secretName = "prod/joblisting/mongo"
            val region = Region.of("eu-north-1")

            // Create a Secrets Manager client
            val client = SecretsManagerClient.builder()
                .region(region)
                .build()

            val getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build()

            val getSecretValueResponse: GetSecretValueResponse

            try {
                getSecretValueResponse = client.getSecretValue(getSecretValueRequest)
            } catch (e: Exception) {
                // For a list of exceptions thrown, see
                // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
                throw e
            }
            val gson = Gson()
            return gson.fromJson(getSecretValueResponse.secretString(), AwsSecrets::class.java)
        }
    }
}