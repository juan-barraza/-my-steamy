package io.ionic.starter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Implementation of App Widget functionality.
 */
public class GameWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
      Intent intent = new Intent(context, WidgetUpdateService.class);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
      context.startService(intent);
    }

    @Override
    public void onEnabled(Context context) {
        context.startService(new Intent(context, WidgetUpdateService.class));
    }

    @Override
    public void onDisabled(Context context) {
      context.stopService(new Intent(context, WidgetUpdateService.class));
    }
}
