package com.example.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainHomework : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_homework)


        val chatList = mutableListOf<Chat>()
        chatList.add(Chat("안녕하세요",false))
        chatList.add(Chat("반갑습니다",true))
        chatList.add(Chat("친구있어요?",true))
        chatList.add(Chat("없어요",false))
        chatList.add(Chat("좋아해요",false))
        chatList.add(Chat("전 싫어요",true))
        chatList.add(Chat("친구 만들었어요?",false))
        chatList.add(Chat("친구 못만들었어요 ㅠ",true))
        chatList.add(Chat("식사하셨어요?",true))
        chatList.add(Chat("냉면 먹었어요",false))

        val adapter = ChatRecyclerAdapter(
            chatList = chatList,
            inflater = LayoutInflater.from(this@MainHomework)
        )

        with(findViewById<RecyclerView>(R.id.chatrecycler)) {
            this.layoutManager = LinearLayoutManager(this@MainHomework)
            this.adapter = adapter
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            adapter.chatList.add(2,Chat("두번째 입니다.",false))
            adapter.notifyItemInserted(2)
        }


    }


}

class ChatRecyclerAdapter(
    val chatList: MutableList<Chat>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class RightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rightTextView: TextView

        init {
            rightTextView = itemView.findViewById(R.id.right)
        }
    }

    class LeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftTextView: TextView

        init {
            leftTextView = itemView.findViewById(R.id.left)
        }
    }


    override fun getItemViewType(position: Int): Int {
        when (chatList.get(position).is_right) {
            true -> return 1
            false -> return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return RightViewHolder(inflater.inflate(R.layout.sub2homework,parent,false))
            else -> return LeftViewHolder(inflater.inflate(R.layout.subhomework,parent,false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chat = chatList.get(position)
        when (chat.is_right) {
            true -> (holder as RightViewHolder).rightTextView.text = chat.Chat
            false -> (holder as LeftViewHolder).leftTextView.text = chat.Chat
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}

class Chat(val Chat : String, val is_right : Boolean)