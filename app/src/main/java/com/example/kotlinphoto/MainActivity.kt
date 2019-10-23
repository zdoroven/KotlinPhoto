package com.example.kotlinphoto

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity : AppCompatActivity() {


    var currentPath: String? = null
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2
    var counter = true
    var counter2 = 1
    var counter3 = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        galleryButton.setOnClickListener(){
            dispatchGalleryIntent()
        }

        cameraButton.setOnClickListener(){
            dispatchCameraIntent()
        }

        bwButton.setOnClickListener(){
            blackWhite()
        }

        rotationButton.setOnClickListener(){
            rotationFilt()
        }

        rightToLeft.setOnClickListener(){
            rightToLeft()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){
            try{
                val file = File(currentPath)
                val uri = Uri.fromFile(file)
                imageView.setImageURI(uri)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK){
            try{
                val uri = data!!.data
                imageView.setImageURI(uri)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun dispatchGalleryIntent(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_PICTURE)
    }

    private fun dispatchCameraIntent(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            var photoFile: File? = null
             try {
                photoFile = createImage()
             }catch (e: IOException){
                 e.printStackTrace()
             }
            if(photoFile != null){
                var photoUri = FileProvider.getUriForFile(this,
                    "com.example.kotlinphoto.fileprovider", photoFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, TAKE_PICTURE)

            }
        }
    }

    private fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "JPEG" + timeStamp + "_"
        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    //Filters Code

    //Black and white filter

    private fun blackWhite(){
        if(counter) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)

            val filter = ColorMatrixColorFilter(matrix)
            imageView.setColorFilter(filter)
            counter = false
        } else {
            imageView.clearColorFilter()
            counter = true
        }
    }

    //Rotation filter

    private fun rotationFilt(){
        imageView.rotation = 90f * counter2
        counter2++
    }

    //Right to left filter

    private fun rightToLeft() {
        imageView.rotationY = 180f * counter3
        counter3++
    }
}
