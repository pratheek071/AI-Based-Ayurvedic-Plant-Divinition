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

class beauty : AppCompatActivity() {
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
        setContentView(R.layout.activity_beauty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageList = arrayOf(
            R.drawable.aloevera,
            R.drawable.turmeric,
            R.drawable.sandalwood,
            R.drawable.neem,
        R.drawable.rose,
            R.drawable.hibiscus,
            R.drawable.brahmi,
            R.drawable.gotukola,
        R.drawable.fenugreek,
            R.drawable.coconut,
            R.drawable.avocado,
            R.drawable.oatmeal,
        R.drawable.lemon,
            R.drawable.cucumber,
            R.drawable.almond,
            R.drawable.papaya,
            R.drawable.greentea,
            R.drawable.carrot,
            R.drawable.rosemary,
        R.drawable.mint,
            R.drawable.amla,
            R.drawable.manjistha,
            R.drawable.chandan,
            R.drawable.lodhra )
        titleList = arrayOf(
            "AloeVera_for_beauty",
            "Turmeric_for_beauty",
            "Sandalwood",
            "Neem_for_beauty",
        "Rose",
            "Hibiscus_for_beauty",
        "Brahmi_for_beauty",
        "GotuKola_for_beauty",
        "Fenugreek_for_beauty",
        "Coconut",
        "Avocado",
        "Oatmeal",
        "Lemon",
        "Cucumber",
        "Almond",
        "Papaya",
        "GreenTea",
        "Carrot",
        "Rosemary",
        "Mint_for_beauty",
        "Amla_for_beauty",
        "Manjistha",
        "Chandan",
        "Lodhra")
        descList = arrayOf(
            getString(R.string.AloeVera_for_beauty),
            getString(R.string.Turmeric_for_beauty),
            getString(R.string.Sandalwood),
            getString(R.string.Neem_for_beauty),
            getString(R.string.Rose),
        getString(R.string.Hibiscus_for_beauty),
        getString(R.string.Brahmi_for_beauty),

        getString(R.string.GotuKola_for_beauty),
        getString(R.string.Fenugreek_for_beauty),
        getString(R.string.Coconut),
        getString(R.string.Avocado),
        getString(R.string.Oatmeal),
        getString(R.string.Lemon),
        getString(R.string.Cucumber),
        getString(R.string.Almond),
        getString(R.string.Papaya),
        getString(R.string.GreenTea),
        getString(R.string.Carrot),
        getString(R.string.Rosemary),
            getString(R.string.Mint_for_beauty),
            getString(R.string.Amla_for_beauty),
        getString(R.string.Manjistha),
        getString(R.string.Chandan),
        getString(R.string.Lodhra))

        detailImageList = arrayOf(
            R.drawable.aloevera, R.drawable.turmeric, R.drawable.sandalwood, R.drawable.neem, R.drawable.rose, R.drawable.hibiscus, R.drawable.brahmi,
            R.drawable.gotukola, R.drawable.fenugreek, R.drawable.coconut, R.drawable.avocado, R.drawable.oatmeal, R.drawable.lemon, R.drawable.cucumber,
            R.drawable.almond, R.drawable.papaya, R.drawable.greentea, R.drawable.carrot, R.drawable.rosemary, R.drawable.mint,
            R.drawable.amla, R.drawable.manjistha, R.drawable.chandan, R.drawable.lodhra )

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

