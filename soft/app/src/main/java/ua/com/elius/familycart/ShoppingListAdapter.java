package ua.com.elius.familycart;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>
        implements ListItemTouchHelperAdapter {

    private static final String TAG = "ShoppingListAdapter";

    private ArrayList<String[]> mDataset;
    private final OnStartDragListener mDragStartListener;

    public ShoppingListAdapter(String[][] dataset, OnStartDragListener dragStartListener) {
        mDataset = new ArrayList<>();
        Collections.addAll(mDataset, dataset);

        mDragStartListener = dragStartListener;
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position)[0]);
        holder.quantity.setText(mDataset.get(position)[1]);
        holder.description.setText(mDataset.get(position)[2]);
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.i(TAG, "onItemMove");
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataset, i, i + 1);
                Log.i(TAG, "swap");
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataset, i, i - 1);
                Log.i(TAG, "swap");
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        Log.i(TAG, "onItemDismiss");
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView quantity;
        public TextView description;
        public ImageView handle;
        public ImageView bought;
        public ImageView wontBuy;
        public FrameLayout front;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.item_title);
            quantity = (TextView) v.findViewById(R.id.item_quantity);
            description = (TextView) v.findViewById(R.id.item_description);
            handle = (ImageView) v.findViewById(R.id.drag_handle);
            bought = (ImageView) v.findViewById(R.id.bg_icon_bought);
            wontBuy = (ImageView) v.findViewById(R.id.bg_icon_wont_by);
            front = (FrameLayout) v.findViewById(R.id.item_front);
        }
    }
}
