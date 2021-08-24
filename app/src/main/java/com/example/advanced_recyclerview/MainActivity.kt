package com.example.advanced_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var mPositionDraggedFrom = -1
    private var mPositionDraggedTo = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: ArrayList<Model> = ArrayList()

        list.add(Model(1, "Chandler Bing"))
        list.add(Model(2, "Ross Geller"))
        list.add(Model(3, "Joey Tribianni"))
        list.add(Model(4, "Phoebe Buffay"))
        list.add(Model(5, "Monica Geller"))
        list.add(Model(6, "Rachel Green"))

        recycler_view.layoutManager = LinearLayoutManager(this)
        val itemAdapter = RecyclerViewAdapter(this, list)
        recycler_view.adapter = itemAdapter

        swipeHandler(list)

        drag(list, itemAdapter)
    }

    private fun swipeHandler(list: ArrayList<Model>) {
        val editSwipeHandler = object: SwipeToEdit(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                //TODO: Create edit function to edit
            }
        }

        val editItemAdapter = ItemTouchHelper(editSwipeHandler)
        editItemAdapter.attachToRecyclerView(recycler_view)

        val deleteSwipeHandler = object: SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                list.removeAt(position)
            }
        }

        val deleteItemAdapter = ItemTouchHelper(deleteSwipeHandler)
        deleteItemAdapter.attachToRecyclerView(recycler_view)
    }

    private fun drag(list: ArrayList<Model>, adapter: RecyclerViewAdapter) {
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                0
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val draggedPosition = viewHolder.adapterPosition
                    val targetPosition = target.adapterPosition

                    if (mPositionDraggedFrom == -1) {
                        mPositionDraggedFrom = draggedPosition
                    }

                    mPositionDraggedTo = targetPosition
                    Collections.swap(list, draggedPosition, targetPosition)
                    adapter.notifyItemMoved(draggedPosition, targetPosition)

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)

                    mPositionDraggedFrom = -1
                    mPositionDraggedTo = -1
                }
            }
        )
        helper.attachToRecyclerView(recycler_view)
    }
}