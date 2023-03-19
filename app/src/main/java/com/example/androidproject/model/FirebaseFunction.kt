package com.example.androidproject.model

import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class FirebaseFunction(itemView: View): RecyclerView.ViewHolder(itemView) {


    fun downloadFile(url: String, name: String) {
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url)

        storageRef.getFile(initPdf(name))
            .addOnSuccessListener {

                Toast.makeText(itemView.context, "PDF file downloaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {

                Toast.makeText(itemView.context, "PDF file download failed", Toast.LENGTH_SHORT).show()
            }
    }
     fun initPdf(name: String): File {
        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsFolder, name)
        pdfFile.createNewFile()
        return pdfFile
    }

}