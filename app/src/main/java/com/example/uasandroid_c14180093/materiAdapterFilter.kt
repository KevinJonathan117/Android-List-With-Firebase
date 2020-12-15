package com.example.uasandroid_c14180093

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_website.view.*


class materiAdapterFilter(private val listMateri: ArrayList<Materi>) : RecyclerView.Adapter<materiAdapterFilter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNamaMateri : TextView = itemView.namaMateri
        var txtLinkWebsite : TextView = itemView.linkWebsite
        var txtNilai : TextView = itemView.nilai
        var valueLike : Switch = itemView.likeSwitch
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_website,
            parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var materi = listMateri[position]
        val db = FirebaseFirestore.getInstance()
        val dbCol = "Materi"

        holder.txtNamaMateri.text = materi.namaMateri
        holder.txtLinkWebsite.text = materi.linkWebsite
        holder.txtNilai.text = "Nilai: " + materi.nilai + "/100"
        if(materi.statusLike == "true") {
            holder.valueLike.isChecked = true
        } else if(materi.statusLike == "false"){
            holder.valueLike.isChecked = false
        }

        holder.valueLike.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val data = hashMapOf(
                    "namaMateri" to materi.namaMateri,
                    "linkWebsite" to materi.linkWebsite,
                    "nilai" to materi.nilai,
                    "statusLike" to "true"
                )
                materi.linkWebsite?.let { db.collection(dbCol).document(it).set(data as Map<String, Any>) }
            } else {
                val data = hashMapOf(
                    "namaMateri" to materi.namaMateri,
                    "linkWebsite" to materi.linkWebsite,
                    "nilai" to materi.nilai,
                    "statusLike" to "false"
                )
                materi.linkWebsite?.let { db.collection(dbCol).document(it).set(data as Map<String, Any>) }
            }
        }
    }

    override fun getItemCount(): Int {
        return listMateri.size
    }

}