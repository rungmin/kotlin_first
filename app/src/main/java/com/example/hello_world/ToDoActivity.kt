package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.zip.Inflater


class ToDoActivity : AppCompatActivity() {
    lateinit var todoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        findViewById<ImageView>(R.id.write).setOnClickListener {
            startActivity(Intent(this,ToDoWriteActivity::class.java))
        }
        todoRecyclerView = findViewById<RecyclerView>(R.id.todo_list)

        getToDoList()

        findViewById<EditText>(R.id.search_edittext).doAfterTextChanged {
            searchToDoList(it.toString())
        }




    }


    fun searchToDoList(keyword: String){

        val header = HashMap<String,String>()
        val sp = this.getSharedPreferences(
            "userInfo",
            Context.MODE_PRIVATE
        )

        val token = sp.getString("token","")
        header.put("Authorization","token " + token!!)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.searchToDoList(header,keyword).enqueue(object : Callback<ArrayList<ToDo>>{
            override fun onResponse(
                call: Call<ArrayList<ToDo>>,
                response: Response<ArrayList<ToDo>>
            ) {
                Log.d("keyworrrd",response.message())
               if(response.isSuccessful) {
                   val todolist = response.body()
                   makeToDoList(todolist!!)


               }


            }

            override fun onFailure(call: Call<ArrayList<ToDo>>, t: Throwable) {


            }
        })

    }









    fun makeToDoList(todoList: ArrayList<ToDo>){
        todoRecyclerView.adapter = ToDoListRecyclerViewAdapter(
            todoList!!,
            LayoutInflater.from(this),
            this
        )
    }

    fun changeToDoComplete(todoId: Int,activity: ToDoActivity) {
        val header = HashMap<String,String>()
        val sp = this.getSharedPreferences(
            "userInfo",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token","")
        header.put("Authorization","token " + token!!)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.changeToDoComplete(header,todoId).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                Log.d("abcccc","들어왔니")
                activity.getToDoList()
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

                Log.d("abcccc","안들어왔다")
                activity.getToDoList()
            }
        })


    }



    fun getToDoList(){
        val header = HashMap<String,String>()
        val sp = this.getSharedPreferences(
            "userInfo",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token", "")
        header.put("Authorization", "token " + token!!)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getToDoList(header).enqueue(object : Callback<ArrayList<ToDo>>{
            override fun onResponse(
                call: Call<ArrayList<ToDo>>,
                response: Response<ArrayList<ToDo>>
            ) {

                if(response.isSuccessful) {
                    val todolist = response.body()
                    Log.d("todooo",todolist!!.size.toString())
                    makeToDoList(todolist)
                }
            }

            override fun onFailure(call: Call<ArrayList<ToDo>>, t: Throwable) {

            }
        })

    }
}

class ToDoListRecyclerViewAdapter(
    val todoList: ArrayList<ToDo>,
    val inflater: LayoutInflater,
    val activity: ToDoActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var previousDate: String = "" //이전 날짜






    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        when(viewType) {
            1 -> return DateViewHolder(inflater.inflate(R.layout.todo_date,parent,false))
            else -> return ContentViewHolder(inflater.inflate(R.layout.todo_content,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val todo = todoList.get(position)
        val tempDate = todo.created.split("T")[0] // 임시날짜
        if (tempDate == previousDate) return 0
        else{
            previousDate = tempDate
            return 1
        }
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView
        init {
            dateTextView = itemView.findViewById(R.id.date)
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView
        val isComplete: ImageView
        init {
            content = itemView.findViewById(R.id.content)
            isComplete = itemView.findViewById(R.id.is_complete)

            isComplete.setOnClickListener {
//                isComplete.setImageResource(R.drawable.img)
                activity.changeToDoComplete(todoList.get(adapterPosition).id ,activity)

            }
        }

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 4/13 숙제

        val todo = todoList.get(position)
        if (holder is DateViewHolder) {
            (holder as DateViewHolder).dateTextView.text = todo.created.split("T")[0]

            Log.d("todooo",(holder as DateViewHolder).dateTextView.text.toString())
        }

        else {

            (holder as ContentViewHolder).content.text = todo.content

            if (todo.is_complete) {
                (holder as ContentViewHolder).isComplete.setImageDrawable(
                    activity.resources.getDrawable(R.drawable.img,activity.theme)
                )
            }
            else {
                (holder as ContentViewHolder).isComplete.setImageDrawable(
                    activity.resources.getDrawable(R.drawable.img_1,activity.theme)
                )
            }
        }








    }


    override fun getItemCount(): Int {
        return todoList.size
    }













}



