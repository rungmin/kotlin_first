package com.example.hello_world

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getYoutubeItemList().enqueue(object : Callback<ArrayList<YoutubeItem>> {
            override fun onResponse(
                call: Call<ArrayList<YoutubeItem>>,
                response: Response<ArrayList<YoutubeItem>>
            ) {
                if (response.isSuccessful) {
                    val youtubeItemList = response.body()

                    findViewById<RecyclerView>(R.id.youtube_recycler).apply {
                        this.adapter = YoutubeListAdapter(
                            youtubeItemList!!,
                            LayoutInflater.from(this@YoutubeActivity),
                            Glide.with(this@YoutubeActivity),
                            this@YoutubeActivity
                        )
                        this.layoutManager = LinearLayoutManager(this@YoutubeActivity)
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<YoutubeItem>>, t: Throwable) {
                Log.d("nono","Fail" + t.message)
            }

        })

    }
}


class YoutubeListAdapter(
    val youtubeItemList: ArrayList<YoutubeItem>,
    val inflater: LayoutInflater,
    val glide: RequestManager,
    val context: Context
) : RecyclerView.Adapter<YoutubeListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView
        val title: TextView
        val content: TextView

        init {
            thumbnail = itemView.findViewById(R.id.thumbnail)
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)

            itemView.setOnClickListener {
                val intent = Intent(context,YoutubeItemActivity::class.java)
                intent.putExtra("videoUrl",youtubeItemList.get(adapterPosition).video)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.youtube_list_item, parent, false)
        return ViewHolder(view)


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        glide.load((youtubeItemList.get(position).thumbnail)).centerCrop().into(holder.thumbnail)
        holder.title.text = youtubeItemList.get(position).title
        holder.content.text = youtubeItemList.get(position).content
    }

    override fun getItemCount(): Int {
        return youtubeItemList.size
    }
}