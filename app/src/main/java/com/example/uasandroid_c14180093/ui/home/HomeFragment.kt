package com.example.uasandroid_c14180093.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uasandroid_c14180093.Materi
import com.example.uasandroid_c14180093.R
import com.example.uasandroid_c14180093.materiAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var adapter : materiAdapter

    private lateinit var dtNamaMateri : Array<String>
    private lateinit var dtLinkWebsite : Array<String>
    private lateinit var dtNilai : Array<String>
    private lateinit var dtStatusLike : Array<String>

    private var arMateri = arrayListOf<Materi>()

    private fun prepareMateri() {
        val db = FirebaseFirestore.getInstance()
        val dbCol = "Materi"

        var dtNamaMateri_ : Array<String> = arrayOf()
        var dtLinkWebsite_ : Array<String> = arrayOf()
        var dtNilai_ : Array<String> = arrayOf()
        var dtStatusLike_ : Array<String> = arrayOf()

        db.collection(dbCol)
            .get()
            .addOnSuccessListener {
                    documents ->
                for(document in documents){
                    var data = document.data as MutableMap<String, String>
                    dtNamaMateri_ += data.getValue("namaMateri").toString()
                    dtLinkWebsite_ += data.getValue("linkWebsite").toString()
                    dtNilai_ += data.getValue("nilai").toString()
                    dtStatusLike_ += data.getValue("statusLike").toString()
                }
                dtNamaMateri = dtNamaMateri_
                dtLinkWebsite = dtLinkWebsite_
                dtNilai = dtNilai_
                dtStatusLike = dtStatusLike_
                addMateri()
                showMateri()
            }
    }

    private fun addMateri() {
        for(position in dtNamaMateri.indices) {
            val data = Materi(dtNamaMateri[position], dtLinkWebsite[position], dtNilai[position], dtStatusLike[position])
            arMateri.add(data)
        }
    }

    private fun showMateri() {
        rvListWebsite.layoutManager = LinearLayoutManager(HomeFragment().context)
        adapter = materiAdapter(arMateri)
        rvListWebsite.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        arMateri.clear()
        prepareMateri()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}