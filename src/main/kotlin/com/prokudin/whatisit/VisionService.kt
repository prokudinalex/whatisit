package com.prokudin.whatisit

import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Feature.Type
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.ArrayList

private const val CREDS = "GOOGLE_APPLICATION_CREDENTIALS"

@Service
class VisionService {
    private val log: Logger = LoggerFactory.getLogger(VisionService::class.java)

    fun getCredentialsEnv() {
        log.info("Google credentials will be loaded from: {}", System.getenv(CREDS))
    }

    fun checkImage() {
        log.info("Hello from VisionService!")
        ImageAnnotatorClient.create().use({ vision ->
            // Reads the image file into memory
            val data = ClassLoader.getSystemResource("wakeupcat.jpg").readBytes()
            val imgBytes = ByteString.copyFrom(data)

            // Builds the image annotation request
            val requests = ArrayList<AnnotateImageRequest>()
            val img = Image.newBuilder().setContent(imgBytes).build()
            val feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build()
            val request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build()
            requests.add(request)

            // Performs label detection on the image file
            val response = vision.batchAnnotateImages(requests)
            val responses = response.getResponsesList()

            for (res in responses) {
                if (res.hasError()) {
                    log.error("Error: {}", res.getError().getMessage())
                    return
                }
                log.info(res.toString())
            }
        })
    }
}
