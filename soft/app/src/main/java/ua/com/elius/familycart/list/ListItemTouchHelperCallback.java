package ua.com.elius.familycart.list;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import ua.com.elius.familycart.data.item.List;

import static ua.com.elius.familycart.data.item.List.BOUGHT;
import static ua.com.elius.familycart.data.item.List.DELETED;
import static ua.com.elius.familycart.data.item.List.TO_BUY;
import static ua.com.elius.familycart.data.item.List.WONT_BUY;

public class ListItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "TouchHelperCallback";

    private final ListItemTouchHelperAdapter mAdapter;
    private @ListViewHolder.ItemMode int mPositiveSwipeMode;
    private @ListViewHolder.ItemMode int mNegativeSwipeMode;

    public ListItemTouchHelperCallback(ListItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
        switch (mAdapter.getListType()) {
            case TO_BUY:
                mPositiveSwipeMode = ListViewHolder.MODE_BOUGHT;
                mNegativeSwipeMode = ListViewHolder.MODE_WONT_BUY;
                break;
            case WONT_BUY:
                mPositiveSwipeMode = ListViewHolder.MODE_TO_BUY;
                mNegativeSwipeMode = ListViewHolder.MODE_DELETE;
                break;
        }
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
        switch (mAdapter.getListType()) {
            case TO_BUY:
                if (direction == ItemTouchHelper.END) {
                    mAdapter.onChangeList(BOUGHT, viewHolder.getAdapterPosition());
                } else {
                    mAdapter.onChangeList(WONT_BUY, viewHolder.getAdapterPosition());
                }
                break;
            case WONT_BUY:
                if (direction == ItemTouchHelper.END) {
                    mAdapter.onChangeList(TO_BUY, viewHolder.getAdapterPosition());
                } else {
                    mAdapter.onChangeList(DELETED, viewHolder.getAdapterPosition());
                }
                break;
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        boolean isEnabled = false;
        switch (mAdapter.getListType()) {
            case TO_BUY: case WONT_BUY:
                isEnabled = true;
                break;
        }
        return isEnabled;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        ListViewHolder holder = (ListViewHolder) viewHolder;
        holder.setMode(ListViewHolder.MODE_DEFAULT);
        getDefaultUIUtil().clearView(holder.front);
        super.clearView(recyclerView, viewHolder);
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
