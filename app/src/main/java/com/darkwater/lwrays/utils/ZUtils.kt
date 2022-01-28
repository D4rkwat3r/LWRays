package com.darkwater.lwrays.utils

import android.util.Base64
import okhttp3.Headers
import okhttp3.RequestBody
import okio.Buffer
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object ZUtils {

    val signables = listOf(
        "rawDeviceId",
        "rawDeviceIdTwo",
        "appType",
        "appVersion",
        "osType",
        "deviceType",
        "sId",
        "countryCode",
        "reqTime",
        "User-Agent",
        "contentRegion",
        "nonce",
        "carrierCountryCodes"
    )

    fun signature(
        path: String,
        headers: Headers,
        body: RequestBody? = null
    ): String {
        val mac = Mac.getInstance("HmacSHA256")
        val keySpec = SecretKeySpec(
            Base64.decode(
                "BwXdBGhu8TySKFSThuuRZEZ/6ZsoQHi4mrlstLpsx0g=", Base64.NO_WRAP
            ),
            "HmacSHA256"
        )
        mac.init(keySpec)
        mac.update(path.toByteArray())
        signables.forEach { key ->
            val headerValue = headers[key]
            if (headerValue != null) {
                mac.update(headerValue.toByteArray())
            }
        }
        if (body != null) {
            val buffer = Buffer()
            body.writeTo(buffer)
            mac.update(buffer.readByteArray())
        }
        return Base64.encodeToString(
            Base64.decode("Ag==", Base64.NO_WRAP) + mac.doFinal(),
            Base64.NO_WRAP
        )
    }

    fun deviceId(): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val installationId = UUID.randomUUID().toString()
        val prefix = Base64.decode("Ag==", Base64.NO_WRAP) + digest.digest(installationId.toByteArray())
        return (
                prefix + digest.digest(
                    prefix + digest.digest(Base64.decode("xIgzqEh8x0nmbrk00Lp/LWCK", Base64.NO_WRAP))
                )
                ).joinToString(separator = "") { byte -> "%02x".format(byte) }
    }

    fun getPath(path: String): String {
        return path
            .replace("https://api.projz.com", "")
            .replace("https://ws.projz.com", "")
    }

}