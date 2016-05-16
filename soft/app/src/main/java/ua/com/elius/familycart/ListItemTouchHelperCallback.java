package ua.com.elius.familycart;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class ListItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "TouchHelperCallback";

    private final ListItemTouchHelperAdapter mAdapter;

    public ListItemTouchHelperCallback(ListItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
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

        ShoppingListAdapter.ViewHolder holder = (ShoppingListAdapter.ViewHolder) viewHolder;
        holder.setMode(ShoppingListAdapter.ViewHolder.MODE_DEFAULT);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            ShoppingListAdapter.ViewHolder holder = (ShoppingListAdapter.ViewHolder) viewHolder;
            if (dX > 0) {
                holder.setMode(ShoppingListAdapter.ViewHolder.MODE_BOUGHT);
            }
            if (dX < 0){
                holder.setMode(ShoppingListAdapter.ViewHolder.MODE_WONT_BUY);
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
