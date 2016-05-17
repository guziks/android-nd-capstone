package ua.com.elius.familycart.list;

import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ua.com.elius.familycart.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    @IntDef({MODE_DEFAULT, MODE_BOUGHT, MODE_WONT_BUY, MODE_TO_BUY, MODE_DELETE})
    public @interface ItemMode {}
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_BOUGHT = 1;
    public static final int MODE_WONT_BUY = 2;
    public static final int MODE_TO_BUY = 3;
    public static final int MODE_DELETE = 4;

    public TextView title;
    public TextView quantity;
    public TextView description;
    public ImageView handle;
    public ImageView bought;
    public ImageView wontBuy;
    public ImageView toBuy;
    public ImageView delete;
    public FrameLayout front;

    private static int COLOR_BOUGHT = Color.parseColor("#43A047");
    private static int COLOR_WONT_BUY = Color.parseColor("#EF6C00");
    private static int COLOR_TO_BUY = Color.parseColor("#0288D1");
    private static int COLOR_DELETE = Color.parseColor("#EF5350");

    private @ItemMode int mMode;

    public ListViewHolder(View v) {
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
