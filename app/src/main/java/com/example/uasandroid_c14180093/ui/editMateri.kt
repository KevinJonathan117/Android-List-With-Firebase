package com.example.uasandroid_c14180093.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.uasandroid_c14180093.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_materi.*

class editMateri : AppCompatActivity() {
    var namaMateri : String = ""
    var linkWebsite : String = ""
    var nilai : String = ""
    var statusLike : String = ""

    companion object {
        const val extraText = "index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_materi)

        val dataTerima = intent.getStringExtra(extraText)

        val db = FirebaseFirestore.getInstance()
        val dbCol = "Materi"

        db.collection(dbCol)
            .get()
            .addOnSuccessListener {
                    documents ->
                for(document in documents){
                    var data = document.data as MutableMap<String, String>
                    if(data.getValue("linkWebsite").toString() == dataTerima) {
                        namaMateri = data.getValue("namaMateri").toString()
                        linkWebsite = data.getValue("linkWebsite").toString()
                        nilai = data.getValue("nilai").toString()
                        statusLike = data.getValue("statusLike").toString()
                    }
                }
                etNamaMateri1.setText(namaMateri)
                etLinkWebsite1.setText(linkWebsite)
                etNilai1.setText(nilai)
            }

        btnDelete.setOnClickListener {
            db.collection(dbCol).document(linkWebsite).delete()
            Toast.makeText(this, "Delete Success!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnEdit.setOnClickListener {
            val data = hashMapOf(
                "namaMateri" to etNamaMateri1.text.toString(),
                "linkWebsite" to etLinkWebsite1.text.toString(),
                "nilai" to etNilai1.text.toString(),
                "statusLike" to statusLike
            )
            if (dataTerima != null) {
                db.collection(dbCol).document(dataTerima).set(data as Map<String, Any>)
                Toast.makeText(this, "Edit Success!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}