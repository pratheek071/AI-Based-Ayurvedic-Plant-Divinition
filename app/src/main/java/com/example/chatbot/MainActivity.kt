package com.example.chatbot

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var button1:ImageView
    private lateinit var button2:ImageView
    private lateinit var button3:ImageView
    private lateinit var button4:ImageView
    private lateinit var button5:ImageView
    private lateinit var button6:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button1=findViewById(R.id.img1)
        button2=findViewById(R.id.img2)
        button3=findViewById(R.id.img3)
        button4=findViewById(R.id.img4)
        button5=findViewById(R.id.img)
        button6=findViewById(R.id.img6)

        button1.setOnClickListener{
            val intent = Intent(this, firstaid::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{
            val intent = Intent(this, health::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener{
            val intent = Intent(this, food::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener{
            val intent = Intent(this, beauty::class.java)
            startActivity(intent)
        }



        button5.setOnClickListener{
            val intent=Intent(this,prediction::class.java)
            startActivity(intent)
        }

        button6.setOnClickListener{
            val intent=Intent(this,chatbot::class.java)
            startActivity(intent)
        }
    }
}