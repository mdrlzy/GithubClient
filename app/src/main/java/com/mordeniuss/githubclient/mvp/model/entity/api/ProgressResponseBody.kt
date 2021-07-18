package com.mordeniuss.githubclient.mvp.model.entity.api

import okhttp3.ResponseBody
import okio.*

class ProgressResponseBody(
    val responseBody: ResponseBody,
    val progressListener: ProgressListener
) : ResponseBody() {

    private val bufferedSource: BufferedSource by lazy {
        Okio.buffer(source(responseBody.source()))
    }

    override fun contentType() = responseBody.contentType()

    override fun contentLength() = responseBody.contentLength()

    override fun source() = bufferedSource

    private fun source(source: Source): Source = object : ForwardingSource(source) {
        var totalBytesRead = 0L

        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            totalBytesRead += if (bytesRead != -1L) bytesRead else 0
            progressListener.update(
                totalBytesRead,
                responseBody.contentLength(),
                bytesRead == -1L
            )
            return bytesRead
        }
    }

}