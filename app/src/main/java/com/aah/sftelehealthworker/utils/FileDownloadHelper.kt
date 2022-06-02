package com.aah.sftelehealthworker.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import java.io.File

class FileDownloadHelper(var context: Context) {
    var downloadReference: Long = 0
    var downloadManager: DownloadManager
    fun downloadFile(
        url: String?,
        title: String?,
        description: String?,
        documentId: Int,
        documentExtension: String
    ) {
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)

        //Restrict the types of networks over which this download may proceed.
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(true)

        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle(title)

        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription(description)

        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, documentId + ".pdf");
        request.setDestinationInExternalFilesDir(
            context,
            null,
            "documents/$documentId.$documentExtension"
        )

        // File temporaryWritableFile = new File(getExternalFilesDirectory() + "/" + documentId + ".pdf");
        // request.setDestinationUri(Uri.fromFile(temporaryWritableFile));

        // Allow file to be indexed by media scanner
        // request.allowScanningByMediaScanner();

        // Notification must be visible when the download is complete
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        //Enqueue a new download and same the referenceId
        downloadReference = downloadManager.enqueue(request)
    }

    val externalFilesDirectory: String
        get() = context.getExternalFilesDir(null)!!.absolutePath

    fun isFileDownloaded(documentId: Int): Boolean {
        val internalDocumentsDirectory = File(externalFilesDirectory, "documents")
        var documentFile: File
        documentFile = File(internalDocumentsDirectory.absolutePath + "/" + documentId + ".pdf")
        // check if pdf file is already downloaded in the documents folder
        if (documentFile.exists() && !documentFile.isDirectory) {
            return true
        }
        documentFile = File(internalDocumentsDirectory.absolutePath + "/" + documentId + ".jpg")
        // check if pdf file is already downloaded in the documents folder
        if (documentFile.exists() && !documentFile.isDirectory) {
            return true
        }
        documentFile = File(internalDocumentsDirectory.absolutePath + "/" + documentId + ".jpeg")
        // check if pdf file is already downloaded in the documents folder
        return if (documentFile.exists() && !documentFile.isDirectory) {
            true
        } else false
    }

    fun getInternalFilePath(filename: String, fromDocumentObject: Boolean): String {
        val internalDocumentsDirectory = File(externalFilesDirectory, "documents")
        val documentFile = File(internalDocumentsDirectory.absolutePath + "/" + filename)

        //context.getExternalFilesDir(null);
        //File internalDocumentsDirectory = new File(Uri.parse(filename).getPath()); // new File(context.getExternalFilesDir(null), "documents"); //context.getDir("documents", MODE_PRIVATE);
        return documentFile.absolutePath
    }

    init {
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
}