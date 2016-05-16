package ua.com.elius.familycart;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder>
        implements ListItemTouchHelperAdapter {

    private static final String TAG = "ListAdapter";

    private ArrayList<String[]> mDataset;
    private final OnStartDragListener mDragStartListener;

    public ListAdapter(String[][] dataset, OnStartDragListener dragStartListener) {
        mDataset = new ArrayList<>();
        Collections.addAll(mDataset, dataset);

        mDragStartListener = dragStartListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
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

}
