package com.example.advanced_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_main.view.*
import java.util.*
import kotlin.collections.ArrayList

open class RecyclerViewAdapter
constructor(
    private val context: Context,
    private val list: ArrayList<Model>
): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_main,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.itemView.reg_no.text = model.reg.toString()
        holder.itemView.name.text = model.name
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder constructor(view: View): RecyclerView.ViewHolder(view)
}