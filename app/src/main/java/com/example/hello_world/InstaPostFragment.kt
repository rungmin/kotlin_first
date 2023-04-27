package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class InstaPostFragment : Fragment() {
    var imageUri:Uri? = null
    var contentInput: String = ""
    lateinit var selectedImageView: ImageView
    lateinit var selectedContent: EditText
    lateinit var upload: TextView
    lateinit var ImagePickerLauncher: ActivityResultLauncher<Intent>

    fun makePost() {
        ImagePickerLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                this.type = MediaStore.Images.Media.CONTENT_TYPE
            }
        )
        selectedContent.doAfterTextChanged {
            contentInput = it.toString()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        upload.setOnClickListener {
            val file = getRealFile(imageUri!!)
            val requestFile = RequestBody.create(
                MediaType.parse(
                    (activity as InstaMainActivity).contentResolver.getType(imageUri!!)
                ), file
            )
            val body = MultipartBody.Part.createFormData("image", file!!.name, requestFile)
            val content = RequestBody.create(
                MultipartBody.FORM, //숙제 :  각각 1줄씩 구글링 해서 해석해오기
                contentInput
            )
            val header = HashMap<String, String>()
            val sp = (activity as InstaMainActivity).getSharedPreferences(
                "userInfo",
                Context.MODE_PRIVATE
            )
            val token = sp.getString("token", "")
            header.put("Authorization", "token " + token!!)


            retrofitService.uploadPost(header, body, content).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("Instaa", "abcdef")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }

            })
        }
    }
    private fun getRealFile(uri:Uri): File? {
        var uri: Uri? = uri
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        if(uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        var cursor: Cursor? = (activity as InstaMainActivity).contentResolver.query(
            uri!!,
            projection,
            null,
            null,
            MediaStore.Images.Media.DATE_MODIFIED
        )
        if(cursor == null || cursor.columnCount < 1) {
            return null
        }
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path: String = cursor.getString(column_index)
        if (cursor != null) {
            cursor.close()
            cursor = null
        }
        return File(path)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_post_fragment,container,false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glide = Glide.with(activity as InstaMainActivity)
        selectedContent = view.findViewById(R.id.selected_content)
        upload = view.findViewById(R.id.upload)
        selectedImageView = view.findViewById(R.id.selected_img)


        ImagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            imageUri = it.data!!.data
            glide.load(imageUri).into(selectedImageView)


        }
    }
}
