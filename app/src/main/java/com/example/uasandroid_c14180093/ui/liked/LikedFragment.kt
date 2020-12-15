package com.example.uasandroid_c14180093.ui.liked

import android.os.Bundle
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
import com.example.uasandroid_c14180093.materiAdapterFilter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvListWebsite
import kotlinx.android.synthetic.main.fragment_liked.*

class LikedFragment : Fragment() {

    private lateinit var likedViewModel: LikedViewModel

    private lateinit var adapter : materiAdapterFilter

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
                    if(data.getValue("statusLike").toString() == "true") {
                        dtNamaMateri_ += data.getValue("namaMateri").toString()
                        dtLinkWebsite_ += data.getValue("linkWebsite").toString()
                        dtNilai_ += data.getValue("nilai").toString()
                        dtStatusLike_ += data.getValue("statusLike").toString()
                    }
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
        rvListWebsiteFilter.layoutManager = LinearLayoutManager(LikedFragment().context)
        adapter = materiAdapterFilter(arMateri)
        rvListWebsiteFilter.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareMateri()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_liked, container, false)
        return root
    }
}