package com.example.androidproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.Adapter.MyAdapter
import com.example.androidproject.databinding.ActivityMainBinding
import com.example.androidproject.model.PDFFILe
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    val storage = Firebase.storage
    val storageRef = storage.reference
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(applicationContext, UploadFile::class.java)
            startActivity(intent)

        }
        this.fetchData()


    }

    fun fetchData() {
        val folderRef = storageRef.child("pdfFile/")

        val files = mutableListOf<PDFFILe>()
        val adapter = MyAdapter(files)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        folderRef.listAll().addOnSuccessListener { result ->
            result.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val fileName = item.name
                    val fileUrl = downloadUrl.toString()
                    val file = PDFFILe(fileName,fileUrl)
                    files.add(file)
                    adapter.notifyItemInserted(files.lastIndex)

                }
            }
        }.addOnFailureListener { exception ->
            Log.e(ContentValues.TAG, "Error retrieving PDF files", exception)
        }
    }
}
