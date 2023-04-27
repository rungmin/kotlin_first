package com.example.hello_world

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.zip.Inflater


class MelonDetailActivity : AppCompatActivity() {

    lateinit var melonItemList:  ArrayList<MelonItem>
    lateinit var playPauseButton: ImageView
    lateinit var mediaPlayer: MediaPlayer
    var position = 0
        set(value) {
            if(value <= 0) field = 0
            else if(value >= melonItemList.size) field = melonItemList.size -1
            else field = value
        }

    var is_playing: Boolean = true
        set(value){
            if (value == true) {
                playPauseButton.setImageDrawable(this.resources.getDrawable(R.drawable.pause,this.theme))
            }
            else{
                playPauseButton.setImageDrawable(this.resources.getDrawable(R.drawable.play,this.theme))
            }
            field = value
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melon_detail)

        melonItemList = intent.getSerializableExtra("melon_item_list") as ArrayList<MelonItem>
        position = intent.getIntExtra("position",0)

        playMelonItem(melonItemList.get(position))
        changeThumbnail(melonItemList.get(position))




        playPauseButton = findViewById(R.id.play)
        playPauseButton.setOnClickListener{
            if (is_playing == true) {
                is_playing = false
                mediaPlayer.stop()
            }
            else {
                is_playing = true
                playMelonItem(melonItemList.get(position))
            }
        }

        val back = findViewById<ImageView>(R.id.back)
        val next = findViewById<ImageView>(R.id.next)


        back.setOnClickListener{
            mediaPlayer.stop()
            position = position -1
            playMelonItem(melonItemList.get(position))
            changeThumbnail(melonItemList.get(position))
        }

        next.setOnClickListener{
            mediaPlayer.stop()
            position = position +1
            playMelonItem(melonItemList.get(position))
            changeThumbnail(melonItemList.get(position))
        }


        // 여기다가 findView 사용해서 back, next  썸네일 변경, 미디어 플레이어 스탑을 한 뒤 미디어 플레이어 재생

    }

    fun playMelonItem(melonItem: MelonItem) {
        mediaPlayer = MediaPlayer.create(this, Uri.parse(melonItem.song))
        mediaPlayer.start()
    }

    fun changeThumbnail(melonItem: MelonItem){
        findViewById<ImageView>(R.id.thumbnail).apply {
            val glide = Glide.with(this@MelonDetailActivity)
            glide.load(melonItem.thumbnail).into(this)
        }
    }
}

