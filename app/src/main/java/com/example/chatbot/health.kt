package com.example.chatbot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class health : AppCompatActivity() {
     private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var descList: Array<String>
    lateinit var detailImageList: Array<Int>
    private lateinit var myAdapter: AdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<DataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_health)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageList = arrayOf(
             R.drawable.ashwagandha,
            R.drawable.amla,
            R.drawable.turmeric,
            R.drawable.brahmi,
        R.drawable.giloy,
            R.drawable.neem,
            R.drawable.triphala,
            R.drawable.guduchi,
        R.drawable.tulsi,
            R.drawable.moringa,
            R.drawable.shatavari,
            R.drawable.haritaki,
            R.drawable.bhringraj,
        R.drawable.kutki,
            R.drawable.vidanga,
            R.drawable.musta,
            R.drawable.bhumiamla,
        R.drawable.vacha,
            R.drawable.gokshura,
            R.drawable.shankhpushpi,
            R.drawable.kapikacchu,
        R.drawable.punarnava,
            R.drawable.yashtimadhu,
            R.drawable.kutaja,
            R.drawable.bael,
            R.drawable.gotukola,
            R.drawable.pippali,
            R.drawable.bhumyamalaki)
        titleList = arrayOf(
            "Ashwagandha",
            "Amla",
            "Turmeric",
            "Brahmi",
            "Giloy",
            "Neem",
        "Triphala",
        "Guduchi",
        "Tulsi",
        "Moringa",
        "Shatavari",
        "Haritaki",
        "Bhringraj",
        "Kutki",
        "Vidanga",
        "Musta",
        "Bhumiamla",
        "Vacha",
        "Gokshura",
        "Shankhpushpi",
        "Kapikacchu",
        "Punarnava",
        "Yashtimadhu",
        "Kutaja",
            "Bael",
            "GotuKola",
        "Pippali",
        "Bhumyamalaki")
        descList = arrayOf(
            getString(R.string.Ashwagandha),
            getString(R.string.Amla),
            getString(R.string.Turmeric),
            getString(R.string.Brahmi),
            getString(R.string.Giloy),
            getString(R.string.Neem),
            getString(R.string.Triphala),
            getString(R.string.Guduchi),
        getString(R.string.Tulsi),
        getString(R.string.Moringa),
        getString(R.string.Shatavari),
        getString(R.string.Haritaki),
        getString(R.string.Bhringraj),
        getString(R.string.Kutki),
        getString(R.string.Vidanga),
        getString(R.string.Musta),
        getString(R.string.Bhumiamla),
        getString(R.string.Vacha),
        getString(R.string.Gokshura),
        getString(R.string.Shankhpushpi),
        getString(R.string.Kapikacchu),
        getString(R.string.Punarnava),
            getString(R.string.Yashtimadhu),
            getString(R.string.Kutaja),
            getString(R.string.Bael),
            getString(R.string.GotuKola),
            getString(R.string.Pippali),
            getString(R.string.Bhumyamalaki))
        detailImageList = arrayOf(
            R.drawable.ashwagandha,
            R.drawable.amla,
            R.drawable.turmeric,
            R.drawable.brahmi,
        R.drawable.giloy,
            R.drawable.neem,
            R.drawable.triphala,
            R.drawable.guduchi,
        R.drawable.tulsi,
            R.drawable.moringa,
            R.drawable.shatavari,
            R.drawable.haritaki,
            R.drawable.bhringraj,
        R.drawable.kutki,
            R.drawable.vidanga,
            R.drawable.musta,
            R.drawable.bhumiamla,
        R.drawable.vacha,
            R.drawable.gokshura,
            R.drawable.shankhpushpi,
            R.drawable.kapikacchu,
        R.drawable.punarnava,
            R.drawable.yashtimadhu,
            R.drawable.kutaja,
            R.drawable.bael,
            R.drawable.gotukola,
            R.drawable.pippali,
            R.drawable.bhumyamalaki)
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        dataList = arrayListOf<DataClass>()
        searchList = arrayListOf<DataClass>()
        getData()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    dataList.forEach{
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        myAdapter = AdapterClass(searchList)
        recyclerView.adapter = myAdapter
        myAdapter.onItemClick = {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }
    private fun getData(){
        for (i in imageList.indices){
            val dataClass = DataClass(imageList[i], titleList[i], descList[i], detailImageList[i])
            dataList.add(dataClass)
        }
        searchList.addAll(dataList)
        recyclerView.adapter = AdapterClass(searchList)
    }
}

