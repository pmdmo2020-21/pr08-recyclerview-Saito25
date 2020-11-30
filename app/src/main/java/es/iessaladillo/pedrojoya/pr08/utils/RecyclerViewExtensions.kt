package es.iessaladillo.pedrojoya.pr08.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setOnSwipeListener(
        swipeDirs: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        onSwipe: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit =
                { _, _ -> }) {
    val itemTouchHelper = ItemTouchHelper(
            object  : ItemTouchHelper.SimpleCallback(0, swipeDirs) {
                override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    onSwipe(viewHolder, direction)
                }
            })
    itemTouchHelper.attachToRecyclerView(this)
}