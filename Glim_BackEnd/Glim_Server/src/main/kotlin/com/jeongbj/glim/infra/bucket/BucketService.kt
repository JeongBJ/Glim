package com.jeongbj.glim.infra.bucket

import com.oracle.bmc.Region
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import com.oracle.bmc.objectstorage.ObjectStorageClient
import com.oracle.bmc.objectstorage.model.BatchDeleteObjectIdentifier
import com.oracle.bmc.objectstorage.model.BatchDeleteObjectsDetails
import com.oracle.bmc.objectstorage.requests.BatchDeleteObjectsRequest
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest
import com.oracle.bmc.objectstorage.requests.PutObjectRequest
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*

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

    fun uploadImage(
        inputStream: InputStream,
        prefix: String,
        contentLength: Long)
    : String {
        val objectName = "$prefix/${UUID.randomUUID()}.jpg"
        val request = PutObjectRequest.builder()
            .namespaceName(ociProperties.namespace)
            .bucketName(ociProperties.bucket)
            .objectName(objectName)
            .putObjectBody(inputStream)
            .contentLength(contentLength)
            .contentType("image/jpeg")
            .build()

        try {
            inputStream.use {
                client.putObject(request)
            }
        } catch (e: Exception) {
            log.error("uploadImage failed", e)
            throw e
        }

        return "${ociProperties.cdnUrl}$objectName"
    }

    fun uploadImage(file: MultipartFile, prefix: String): String {
        return uploadImage(file.inputStream, prefix, file.size)
    }


    fun uploadImage(bytes: ByteArray, prefix: String): String {
        return uploadImage(bytes.inputStream(), prefix, bytes.size.toLong())
    }

    fun deleteImage(imageUrl: String) {
        val objectName = imageUrl.removePrefix(ociProperties.cdnUrl)
        val request = DeleteObjectRequest.builder()
            .namespaceName(ociProperties.namespace)
            .bucketName(ociProperties.bucket)
            .objectName(objectName)
            .build()
        client.deleteObject(request)
    }

    fun batchDelete(images: List<String?>) {
        if (images.isEmpty()) return
        val objects = images.filterNotNull().map {
            BatchDeleteObjectIdentifier.builder()
                .objectName(it.removePrefix(ociProperties.cdnUrl))
                .build()
        }
        val details = BatchDeleteObjectsDetails.builder()
            .objects(objects)
            .isSkipDeletedResult(false)
            .build()

        val request = BatchDeleteObjectsRequest.builder()
            .namespaceName(ociProperties.namespace)
            .bucketName(ociProperties.bucket)
            .batchDeleteObjectsDetails(details)
            .build()
        client.batchDeleteObjects(request)
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
