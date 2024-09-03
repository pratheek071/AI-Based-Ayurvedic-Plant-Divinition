package com.example.chatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class firstaid : AppCompatActivity() {
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
        setContentView(R.layout.activity_firstaid)
        imageList = arrayOf(
             R.drawable.aloevera,
            R.drawable.ginger,
            R.drawable.cinnamon,
            R.drawable.clove,
        R.drawable.garlic,
            R.drawable.eucalyptus,
            R.drawable.peppermint,
            R.drawable.licorice,
        R.drawable.lemongrass,
            R.drawable.fenugreek,
            R.drawable.basil,
            R.drawable.chamomile,
        R.drawable.calendula,
            R.drawable.comfrey,
            R.drawable.plantain,
            R.drawable.yarrow,
        R.drawable.teatree,
            R.drawable.cayennepepper,
            R.drawable.ginger,
            R.drawable.onion,
        R.drawable.horsetail,
            R.drawable.tulsi,
            R.drawable.neem,
            R.drawable.trikatu,
            R.drawable.hibiscus,
            R.drawable.mint,
            R.drawable.blackpepper)
        titleList = arrayOf(
            "AloeVera_for_firstaid",
            "Ginger",
            "Cinnamon",
            "Clove",
        "Garlic",
            "Eucalyptus",
            "Peppermint",
            "Licorice",
            "Lemongrass",
            "Fenugreek",
        "Basil",
        "Chamomile",
        "Calendula",
        "Comfrey",
        "Plantain",
        "Yarrow",
        "TeaTree",
        "CayennePepper",
        "Ginger_for_firstaid",
        "Onion",
        "Horsetail",
        "Tulsi_for_firstaid",
        "Neem_for_firstaid",
        "Trikatu",
        "Hibiscus",
        "Mint",
        "BlackPepper")
        descList = arrayOf(
            getString(R.string.AloeVera_for_firstaid),
            getString(R.string.Ginger),
            getString(R.string.Cinnamon),
            getString(R.string.Clove),
        getString(R.string.Garlic),
            getString(R.string.Eucalyptus),
            getString(R.string.Peppermint),
            getString(R.string.Licorice),
        getString(R.string.Lemongrass),
            getString(R.string.Fenugreek),
            getString(R.string.Basil),
            getString(R.string.Chamomile),
        getString(R.string.Calendula),
            getString(R.string.Comfrey),
            getString(R.string.Plantain),
            getString(R.string.Yarrow),
        getString(R.string.TeaTree),
            getString(R.string.CayennePepper),
            getString(R.string.Ginger_for_firstaid),
            getString(R.string.Onion),
        getString(R.string.Horsetail),
            getString(R.string.Tulsi_for_firstaid),
            getString(R.string.Neem_for_firstaid),
            getString(R.string.Trikatu),
       getString(R.string.Hibiscus),
       getString(R.string.Mint),
        getString(R.string.BlackPepper))
        detailImageList = arrayOf(
             R.drawable.aloevera,
            R.drawable.ginger,
            R.drawable.cinnamon,
            R.drawable.clove,
        R.drawable.garlic,
            R.drawable.eucalyptus,
            R.drawable.peppermint,
            R.drawable.licorice,
        R.drawable.lemongrass,
            R.drawable.fenugreek,
            R.drawable.basil,
            R.drawable.chamomile,
        R.drawable.calendula,
            R.drawable.comfrey,
            R.drawable.plantain,
            R.drawable.yarrow,
        R.drawable.teatree,
            R.drawable.cayennepepper,
            R.drawable.ginger,
            R.drawable.onion,
        R.drawable.horsetail,
            R.drawable.tulsi,
            R.drawable.neem,
            R.drawable.trikatu,
            R.drawable.hibiscus,
            R.drawable.mint,
            R.drawable.blackpepper)
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
