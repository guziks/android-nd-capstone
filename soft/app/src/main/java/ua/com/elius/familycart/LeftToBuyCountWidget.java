package ua.com.elius.familycart;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.List;

public class LeftToBuyCountWidget extends AppWidgetProvider {

    private static final String TAG = "LeftToBuyCountWidget";

    public static final String ACTION_WIDGET_UPDATE = "ua.com.elius.familycart.action.APPWIDGET_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String count) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.left_to_buy_count_widget);
        views.setTextViewText(R.id.count_text_view, count);

        Intent intent = new Intent(context, LauncherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.cart_image, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UpdateAsyncTask task = new UpdateAsyncTask();
        task.context = context;
        task.appWidgetManager = appWidgetManager;
        task.appWidgetIds = appWidgetIds;
        task.execute();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_WIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, LeftToBuyCountWidget.class));
            this.onUpdate(context, appWidgetManager, appWidgetIds);
        } else {
            super.onReceive(context, intent);
        }
    }

    class UpdateAsyncTask extends AsyncTask<Void, Void, String> {

        public Context context;
        public AppWidgetManager appWidgetManager;
        public int[] appWidgetIds;

        @Override
        protected String doInBackground(Void... params) {
            String count = null;
            Cursor cursor = context.getContentResolver().query(
                    ItemColumns.CONTENT_URI,
                    ItemColumns.ALL_COLUMNS,
                    ItemColumns.LIST + " = ?",
                    new String[]{String.valueOf(List.TO_BUY.ordinal())},
                    null
            );
            if (cursor != null) {
                count = String.valueOf(cursor.getCount());
                cursor.close();
            }
            return count;
        }

        @Override
        protected void onPostExecute(String count) {
            if (context == null || appWidgetManager == null || appWidgetIds == null) {
                return;
            }

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, count);
            }
        }
    }
}