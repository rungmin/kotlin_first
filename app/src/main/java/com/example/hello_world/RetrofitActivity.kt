package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        // 서버 -> 읽을 수 없는 데이터 -> JSON -> Gson
        // GSON -> 읽을 수 없는 데이터를 코틀린 객체로 바꿔준다
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getStudentList().enqueue(object : Callback<ArrayList<StudentFromServer>> {
            override fun onResponse(
                call: Call<ArrayList<StudentFromServer>>,
                response: Response<ArrayList<StudentFromServer>>
            ) {
                if (response.isSuccessful) {
                    val studentList = response.body()

                    findViewById<RecyclerView>(R.id.studentsRecyclerview).apply {
                        this.adapter = RetrofitAdapter(
                            studentList!!,
                            LayoutInflater.from(this@RetrofitActivity)
                        )
                        this.layoutManager = LinearLayoutManager(this@RetrofitActivity)
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<StudentFromServer>>, t: Throwable) {
            }
        })

        findViewById<TextView>(R.id.createStudent).setOnClickListener {
            val student = HashMap<String,Any>()
            student.put("name","코카콜라")
            student["Intro"] = "펩시"
            student["age"] = 100
            retrofitService.createStudent(student).enqueue(object : Callback<StudentFromServer>{
                override fun onResponse(
                    call: Call<StudentFromServer>,
                    response: Response<StudentFromServer>
                ) {
                    Log.d("testt","응답실패")
                    if (response.isSuccessful) {
                        val student = response.body()
                        Log.d("testt","등록한 학생 : " + student!!.name)
                    }
                }

                override fun onFailure(call: Call<StudentFromServer>, t: Throwable) {
                    Log.d("testt","요청실패")
                }
            })

        }

        findViewById<TextView>(R.id.easyCreateStudent).setOnClickListener {
            val student = StudentFromServer("서울",200,"wellcom to seoul")

            retrofitService.easyCreateStudent(student).enqueue(object : Callback<StudentFromServer>{
                override fun onResponse(
                    call: Call<StudentFromServer>,
                    response: Response<StudentFromServer>
                ) {
                    Log.d("testt","응답실패")
                    if (response.isSuccessful) {
                        val student = response.body()
                        Log.d("testt","등록한 학생 : " + student!!.name)
                    }
                }

                override fun onFailure(call: Call<StudentFromServer>, t: Throwable) {
                    Log.d("testt","요청실패")
                }
            })

        }

    }
}

class RetrofitAdapter(
    var studentList: ArrayList<StudentFromServer>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<RetrofitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView
        val studentAge: TextView
        val studentIntro: TextView

        init {
            studentName = itemView.findViewById(R.id.student_name)
            studentAge = itemView.findViewById(R.id.student_age)
            studentIntro = itemView.findViewById(R.id.student_intro)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.students_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.studentName.text = studentList.get(position).name
        holder.studentAge.text = studentList.get(position).age.toString()
        holder.studentIntro.text = studentList.get(position).intro
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}