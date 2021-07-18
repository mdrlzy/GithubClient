package com.mordeniuss.githubclient.ui.file

import android.app.DownloadManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.mordeniuss.githubclient.mvp.model.file.FileManager
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class AndroidFileManager(val context: Context) : FileManager {

    override fun writeToDownloads(name: String, byteArray: ByteArray){
        Timber.d("write to downloads $name")
        val container = openOutputStream(name)
        val outputStream = container.os
        val file = container.file
        outputStream.write(byteArray)
        outputStream.close()
        file?.let {
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.addCompletedDownload(
                file.name,
                file.name,
                true,
                "application/zip",
                file.absolutePath,
                file.length(),
                true
            )
        }
    }

    private fun openOutputStream(name: String): Container {
        val os: OutputStream
        var file: File? = null
        val uriString: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/zip")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)!!
            uriString = uri.toString()
            os = resolver.openOutputStream(uri)!!
        } else {
            val dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()
            file = File(dir, "$name.zip")
            file.createNewFile()
            os = FileOutputStream(file)
            uriString = Uri.fromFile(file).toString()
        }
        return Container(file, os, uriString)
    }

}

private  class Container(val file: File?, val os: OutputStream, val uri: String)