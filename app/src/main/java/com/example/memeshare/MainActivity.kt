@file:Suppress("DEPRECATION")

package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity(){

    var currentImageUrl:String?=null

    private lateinit var  memepic:ImageView
    private lateinit var  progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memepic=findViewById(R.id.memeImage)
        progressBar=findViewById(R.id.progressBar)




        loadMeme()


    }


    fun loadMeme() {

        // Instantiate the RequestQueue.
        progressBar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url= "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl=response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }



                }).into(memepic)
            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)



    }


    fun shareMeme(view: View) {

        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this meme using ...")
        startActivity(chooser)



    }

    fun nextMeme(view: View) {
        loadMeme()
    }
}



/*open class MyUrlRequestCallback : UrlRequest.Callback() {
     override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
         Log.i(TAG, "onRedirectReceived method called.")
         // You should call the request.followRedirect() method to continue
         // processing the request.
         request?.followRedirect()
     }

     override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
         Log.i(TAG, "onResponseStarted method called.")

         // You should call the request.read() method before the request can be
         // further processed. The following instruction provides a ByteBuffer object
         // with a capacity of 102400 bytes for the read() method. The same buffer
         // with data is passed to the onReadCompleted() method.
         request?.read(ByteBuffer.allocateDirect(102400))
     }

     override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
         Log.i(TAG, "onReadCompleted method called.")
         // You should keep reading the request until there's no more data.
         byteBuffer?.clear()
         request?.read(byteBuffer)
     }

     override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
         Log.i(TAG, "onSucceeded method called.")
     }

     override fun onFailed(
         request: UrlRequest?,
         info: UrlResponseInfo?,
         error: CronetException?
     ) {
         TODO("Not yet implemented")
     }
 }*/

