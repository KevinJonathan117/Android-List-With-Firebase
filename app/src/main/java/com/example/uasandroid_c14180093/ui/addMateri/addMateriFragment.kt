package com.example.uasandroid_c14180093.ui.addMateri

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uasandroid_c14180093.R
import com.example.uasandroid_c14180093.R.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_addmateri.*
import kotlinx.android.synthetic.main.fragment_addmateri.view.*

class addMateriFragment : Fragment() {

    private lateinit var addMateriViewModel: addMateriViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(layout.fragment_addmateri, container, false)
        view.btnSubmit.setOnClickListener {
            val namaMateri = etNamaMateri.text.toString()
            val linkWebsite = etLinkWebsite.text.toString()
            val nilai = etNilai.text.toString()
            val statusLike = "false"

            val db = FirebaseFirestore.getInstance()
            val dbCol = "Materi"

            if(namaMateri != "" && linkWebsite != "" && nilai != "") {
                val data = hashMapOf(
                    "namaMateri" to namaMateri,
                    "linkWebsite" to linkWebsite,
                    "nilai" to nilai,
                    "statusLike" to statusLike
                )
                db.collection(dbCol).document(linkWebsite).set(data as Map<String, Any>)
                Toast.makeText(context, "Add Materi Success!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill all the required field!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}