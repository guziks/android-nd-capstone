package ua.com.elius.familycart;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class ListItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "TouchHelperCallback";

    private final ListItemTouchHelperAdapter mAdapter;
    private @ListViewHolder.ItemMode int mPositiveSwipeMode;
    private @ListViewHolder.ItemMode int mNegativeSwipeMode;

    public ListItemTouchHelperCallback(ListItemTouchHelperAdapter adapter,
           @ListViewHolder.ItemMode int positiveSwipeMode,
           @ListViewHolder.ItemMode int negativeSwipeMode) {
        mAdapter = adapter;
        mPositiveSwipeMode = positiveSwipeMode;
        mNegativeSwipeMode = negativeSwipeMode;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i(TAG, "onMove");
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(TAG, "onSwiped");
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        ListViewHolder holder = (ListViewHolder) viewHolder;
        holder.setMode(ListViewHolder.MODE_DEFAULT);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            ListViewHolder holder = (ListViewHolder) viewHolder;
            if (dX > 0) {
                holder.setMode(mPositiveSwipeMode);
            }
            if (dX < 0){
                holder.setMode(mNegativeSwipeMode);
            }
            getDefaultUIUtil().onDraw(c, recyclerView,
                    holder.front,
                    dX, dY, actionState, isCurrentlyActive);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive);
        }
    }
}
