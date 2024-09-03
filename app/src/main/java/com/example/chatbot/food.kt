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

class food : AppCompatActivity() {
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
        setContentView(R.layout.activity_food)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageList = arrayOf(
            R.drawable.cilantro,
            R.drawable.tarragon,
            R.drawable.ajwain,
            R.drawable.amaranth,
        R.drawable.drumstickleaves,
            R.drawable.spinach,
            R.drawable.brahmi,
        R.drawable.dill,
            R.drawable.oregano,
            R.drawable.chives,
            R.drawable.bayleaves,
        R.drawable.amla,
            R.drawable.turmeric,
            R.drawable.ginger,
            R.drawable.cinnamon,
        R.drawable.clove,
            R.drawable.peppermint,
            R.drawable.lemongrass,
        R.drawable.fenugreek,
            R.drawable.coriander,
            R.drawable.mint,
            R.drawable.curryleaves,
            R.drawable.basil,
            R.drawable.parsley,
           R.drawable.thyme)
        titleList = arrayOf(
            "Cilantro",
            "Tarragon",
            "Ajwain",
            "Amaranth",
        "Drumstick_Leaves",
            "Spinach",
            "Brahmi_for_food",
        "Dill",
            "Oregano",
            "Chives",
            "Bay_Leaves",
        "Amla_for_food",
            "Turmeric_for_food",
            "Ginger_for_food",
            "Cinnamon_for_food",
        "Clove_for_food",
            "Peppermint_for_food",
            "Lemongrass_for_food",
        "Fenugreek_for_food",
            "Coriander",
            "Mint_for_food",
            "Curry_Leaves",
            "Basil_for_food",
            "Parsley",
           "Thyme" )
        descList = arrayOf(
            getString(R.string.Cilantro),
            getString(R.string.Tarragon),
            getString(R.string.Ajwain),
            getString(R.string.Amaranth),
         getString(R.string.Drumstick_Leaves),
            getString(R.string.Spinach),
            getString(R.string.Brahmi_for_food),
         getString(R.string.Dill),
            getString(R.string.Oregano),
            getString(R.string.Chives),
            getString(R.string.Bay_Leaves),
         getString(R.string.Amla_for_food),
            getString(R.string.Turmeric_for_food),
            getString(R.string.Ginger_for_food),
            getString(R.string.Cinnamon_for_food),
         getString(R.string.Clove_for_food),
            getString(R.string.Peppermint_for_food),
            getString(R.string.Lemongrass_for_food),
         getString(R.string.Fenugreek_for_food),
            getString(R.string.Coriander),
            getString(R.string.Mint_for_food),
            getString(R.string.Curry_Leaves),
         getString(R.string.Basil_for_food),
            getString(R.string.Parsley),
            getString(R.string.Thyme))

        detailImageList = arrayOf(
            R.drawable.cilantro,
            R.drawable.tarragon,
            R.drawable.ajwain,
            R.drawable.amaranth,
        R.drawable.drumstickleaves,
            R.drawable.spinach,
            R.drawable.brahmi,
        R.drawable.dill,
            R.drawable.oregano,
            R.drawable.chives,
            R.drawable.bayleaves,
        R.drawable.amla,
            R.drawable.turmeric,
            R.drawable.ginger,
            R.drawable.cinnamon,
        R.drawable.clove,
            R.drawable.peppermint,
            R.drawable.lemongrass,
        R.drawable.fenugreek,
            R.drawable.coriander,
            R.drawable.mint,
            R.drawable.curryleaves,
            R.drawable.basil,
            R.drawable.parsley,
           R.drawable.thyme)

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

