package com.example.androidproject

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidproject.databinding.ActivityMainBinding
import com.example.androidproject.databinding.ActivityUploadFileBinding
import com.google.firebase.storage.FirebaseStorage

class UploadFile : AppCompatActivity() {
    lateinit var binding: ActivityUploadFileBinding
    val REQUEST_CODE = 5
    var uri: Uri? = null
    private val storge = FirebaseStorage.getInstance().reference
    private val refe = storge.child("pdfFile/${System.currentTimeMillis()}.pdf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadButton.setOnClickListener {
            chooseFile()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.data
            this.uploadStuts()
        }
    }

    fun chooseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, REQUEST_CODE)

    }

    fun uploadStuts() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        refe.putFile(uri!!)
            .addOnSuccessListener {

                progressDialog.dismiss()
                Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->

                progressDialog.dismiss()
                Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { snapshot ->

                val progress = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
                progressDialog.setMessage("Uploading ${progress.toInt()}%")
            }
    }

}