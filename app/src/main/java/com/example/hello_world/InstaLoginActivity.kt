package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaLoginActivity : AppCompatActivity() {
    var username:String = ""
    var password:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_login)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        findViewById<TextView>(R.id.insta_join).setOnClickListener {
            startActivity(Intent(this, InstaJoinActivity::class.java))
        }


        findViewById<EditText>(R.id.id_input).doAfterTextChanged { username = it.toString() }
        findViewById<EditText>(R.id.pw_input).doAfterTextChanged { password = it.toString() }

        findViewById<TextView>(R.id.login_btn).setOnClickListener {
            var user = HashMap<String, Any>()
            user.put("username", username)
            user.put("password", password)

            retrofitService.instaLogin(user).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        val user: User = response.body()!!
                        var sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE)
                        var editor:SharedPreferences.Editor = sharedPreferences.edit()
                        Log.d("Todooo",user.token)
                        editor.putString("token",user.token)
                        editor.putString("user_ID",user.id.toString())
                        editor.commit()
                        Log.d("instaa",user.token)

                        startActivity(Intent(this@InstaLoginActivity,InstaMainActivity::class.java))

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("nono", "Fail" + t.message)

                }


            })


        }
    }
}