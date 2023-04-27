package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaJoinActivity : AppCompatActivity() {
    var username: String = ""
    var password1: String = ""
    var password2: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_join)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)



        findViewById<TextView>(R.id.insta_login).setOnClickListener {
            startActivity(Intent(this,InstaLoginActivity::class.java))
        }

        findViewById<EditText>(R.id.id_input).doAfterTextChanged {username = it.toString()}
        findViewById<EditText>(R.id.pw_input1).doAfterTextChanged {password1 = it.toString()}
        findViewById<EditText>(R.id.pw_input2).doAfterTextChanged {password2 = it.toString()}


        findViewById<TextView>(R.id.join_btn).setOnClickListener {

            var user = HashMap<String,Any>()
            user.put("username",username)
            user.put("password1",password1)
            user.put("password2",password2)




            retrofitService.instaJoin(user).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        var sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                        var editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("token",user!!.token)
                        editor.putString("user_ID",user!!.id.toString())
                        editor.commit()
                        startActivity(Intent(this@InstaJoinActivity,InstaMainActivity::class.java))

                    }
                    Toast.makeText(this@InstaJoinActivity,
                        "가입에 성공하였습니다.",
                        Toast.LENGTH_LONG).show()
                }


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("nono","Fail" + t.message)

                    Toast.makeText(this@InstaJoinActivity,
                    "가입에 실패하였습니다.",
                    Toast.LENGTH_LONG).show()

                }

            })
            startActivity(Intent( this,InstaLoginActivity::class.java))

        }
    }
}