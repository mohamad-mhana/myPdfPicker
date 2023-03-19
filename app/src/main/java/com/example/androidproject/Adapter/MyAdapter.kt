package com.example.androidproject.Adapter

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.example.androidproject.model.FirebaseFunction
import com.example.androidproject.model.PDFFILe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class MyAdapter(private val files: List<PDFFILe>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val file = files[position]
        holder.fileName.text = file.name
        val pdfViewHolder = holder as ViewHolder
        pdfViewHolder.bind(files[position])


    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var f = FirebaseFunction(itemView)
        val fileName: TextView = itemView.findViewById(R.id.File_Name)
        val downloadButton: FloatingActionButton = itemView.findViewById(R.id.downloadButton)
        fun bind(pdfFile: PDFFILe) {
            fileName.text = pdfFile.name
            downloadButton.setOnClickListener {
                f.downloadFile(pdfFile.path, pdfFile.name)

            }
        }


    }

}

