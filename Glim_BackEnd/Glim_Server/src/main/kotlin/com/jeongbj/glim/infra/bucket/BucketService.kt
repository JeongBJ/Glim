package com.jeongbj.glim.infra.bucket

import com.oracle.bmc.Region
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import com.oracle.bmc.objectstorage.ObjectStorageClient
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest
import com.oracle.bmc.objectstorage.requests.PutObjectRequest
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.util.UUID

@Service
class BucketService(
    private val ociProperties: OciProperties
) {
    private val client: ObjectStorageClient by lazy {
        val provider = ConfigFileAuthenticationDetailsProvider(ociProperties.configPath, ociProperties.profile)
        ObjectStorageClient.builder()
            .region(Region.fromRegionId(ociProperties.region))
            .build(provider)
    }

    fun uploadImage(file: MultipartFile, prefix: String): String {
        val extension = file.originalFilename?.substringAfterLast(".", "") ?: "jpg"
        val objectName = "$prefix/${UUID.randomUUID()}.$extension"
        val inputStream = BufferedInputStream(file.inputStream)

        val request = PutObjectRequest.builder()
            .namespaceName(ociProperties.namespace)
            .bucketName(ociProperties.bucket)
            .objectName(objectName)
            .putObjectBody(inputStream)
            .contentLength(file.size)
            .contentType(file.contentType)
            .build()

        try {
            inputStream.use {
                client.putObject(request)
            }
        } catch (e: Exception) {
            log.error("uploadImage failed", e)
            throw e
        }

        return "https://objectstorage.${ociProperties.region}.oraclecloud.com/n/${ociProperties.namespace}/b/${ociProperties.bucket}/o/$objectName"
    }

    fun deleteImage(imageUrl: String) {
        val objectName = imageUrl.substringAfterLast("/o/")
        val request = DeleteObjectRequest.builder()
            .namespaceName(ociProperties.namespace)
            .bucketName(ociProperties.bucket)
            .objectName(objectName)
            .build()
        client.deleteObject(request)
    }

    @PreDestroy
    private fun destroy() {
        client.close()
    }

    companion object {
        const val PROFILE = "profile"
        const val IMAGE = "image"
        private val log = LoggerFactory.getLogger(BucketService::class.java)
    }
}
