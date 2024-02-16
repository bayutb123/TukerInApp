package com.bayutb123.tukerin.core.okhttp

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import java.io.IOException

class RemoveQuotesRequestBody(
    private val originalRequestBody: RequestBody
) : RequestBody() {

    override fun contentType(): MediaType? {
        // Return the original content type
        return originalRequestBody.contentType()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        // Return the original content length
        return originalRequestBody.contentLength()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val buffer = Buffer()
        originalRequestBody.writeTo(buffer)
        val bodyString = buffer.readUtf8()
        val modifiedBodyString = bodyString.replace("\"", "")
        sink.writeString(modifiedBodyString, charset("UTF-8"))
    }
}