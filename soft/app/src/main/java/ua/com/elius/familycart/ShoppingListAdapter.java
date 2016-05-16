package ua.com.elius.familycart;

import android.graphics.Color;
import android.support.annotation.IntDef;
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

        @IntDef({MODE_DEFAULT, MODE_BOUGHT, MODE_WONT_BUY, MODE_TO_BUY, MODE_DELETE})
        public @interface ItemMode {}
        public static final int MODE_DEFAULT  = 0;
        public static final int MODE_BOUGHT   = 1;
        public static final int MODE_WONT_BUY = 2;
        public static final int MODE_TO_BUY   = 3;
        public static final int MODE_DELETE   = 4;

        public TextView title;
        public TextView quantity;
        public TextView description;
        public ImageView handle;
        public ImageView bought;
        public ImageView wontBuy;
        public ImageView toBuy;
        public ImageView delete;
        public FrameLayout front;

        private static int COLOR_BOUGHT   = Color.parseColor("#43A047");
        private static int COLOR_WONT_BUY = Color.parseColor("#EF6C00");
        private static int COLOR_TO_BUY   = Color.parseColor("#0288D1");
        private static int COLOR_DELETE   = Color.parseColor("#EF5350");

        private @ItemMode int mMode;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.item_title);
            quantity = (TextView) v.findViewById(R.id.item_quantity);
            description = (TextView) v.findViewById(R.id.item_description);
            handle = (ImageView) v.findViewById(R.id.drag_handle);
            bought = (ImageView) v.findViewById(R.id.bg_icon_bought);
            wontBuy = (ImageView) v.findViewById(R.id.bg_icon_wont_buy);
            toBuy = (ImageView) v.findViewById(R.id.bg_icon_to_buy);
            delete = (ImageView) v.findViewById(R.id.bg_icon_delete);
            front = (FrameLayout) v.findViewById(R.id.item_front);
        }

        public void setMode(@ItemMode int mode) {
            if (mMode == mode) {
                return;
            }
            switch (mode) {
                case MODE_DEFAULT:  setModeDefault(); break;
                case MODE_BOUGHT:   setModeBought();  break;
                case MODE_WONT_BUY: setModeWontBuy(); break;
                case MODE_TO_BUY:   setModeToBuy();   break;
                case MODE_DELETE:   setModeDelete();  break;
            }
            mMode = mode;
        }

        private void setModeDefault() {
            itemView.setBackgroundColor(Color.TRANSPARENT);
            bought.setVisibility(View.INVISIBLE);
            wontBuy.setVisibility(View.INVISIBLE);
            toBuy.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }

        private void setModeBought() {
            setModeDefault();
            itemView.setBackgroundColor(COLOR_BOUGHT);
            bought.setVisibility(View.VISIBLE);
        }

        private void setModeWontBuy() {
            setModeDefault();
            itemView.setBackgroundColor(COLOR_WONT_BUY);
            wontBuy.setVisibility(View.VISIBLE);
        }

        private void setModeToBuy() {
            setModeDefault();
            toBuy.setVisibility(View.VISIBLE);
            itemView.setBackgroundColor(COLOR_TO_BUY);
        }

        private void setModeDelete() {
            setModeDefault();
            delete.setVisibility(View.VISIBLE);
            itemView.setBackgroundColor(COLOR_DELETE);
        }
    }
}
