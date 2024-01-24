package com.example.kotlinbase

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinbase.databinding.ActivityGalleryBinding
import java.io.File


class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_gallery)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

     //   val directory = File(externalMediaDirs[0].absolutePath)
       // val aDirArray = ContextCompat.getExternalFilesDirs(this, null)


     //   val secStore = Environment.getExternalStorageDirectory()
       // //val file = File("$secStore/DCIM/Camera/")
        //val listFiles = file.listFiles()
      //  val files = directory.listFiles() as Array<File>
// array is reversed to ensure last taken photo appears first.
     /*   val adapter = GalleryAdapter(files.reversedArray())
        binding.viewPager.adapter = adapter
        Log.d("chek",directory.toString())*/
        //val directory = File("storage/emulated/0/Pictures/CameraX-image")
        val directory = File(externalMediaDirs[0].absolutePath)
        val dir=  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val files = directory.listFiles() as Array<File>



// array is reversed to ensure last taken photo appears first.
        val adapter = GalleryAdapter(files.reversedArray(),this)
        binding.rcListImgsr.adapter=adapter
        binding.rcListImgsr.layoutManager=LinearLayoutManager(this)
        binding.rcListImgsr.hasFixedSize( )
        Log.d("chek",files.size.toString())
    }
}