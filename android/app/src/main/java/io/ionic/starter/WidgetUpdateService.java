package io.ionic.starter;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.IBinder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;
import android.widget.RemoteViews;

public class WidgetUpdateService extends Service {

  private static final String PREFERENCES_NAME = "CapacitorStorage";
  private static final String FAVORITE_KEY = "deal";
  private static final long CAROUSEL_INTERVAL = 3000;


  private Handler handler;
  private Runnable runnable;
  private List<GameDeal> currentDeals  = new ArrayList<>();
  private Map<String, Store> stores = new HashMap<>();
  private int currentDealIndex = 0;
  private int currentPage = 0;
  private String lastGameId = null;
  private Bitmap backgroundImage;
  private int[] widgetIds;


  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null && intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)) {
      widgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
    }
    startPreferenceChecker();

    String gameId = getFavoriteGameId();
    if (gameId != null && !gameId.equals(lastGameId)) {
      lastGameId = gameId;
      currentDealIndex = 0;
      new FetchAllDealsTask(this).execute(gameId);
    }

    if (handler == null) {
      handler = new Handler();
      startCarousel();
    }
    return  START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (handler != null && runnable != null) {
      handler.removeCallbacks(runnable);
    }
  }

  public void onDealsLoaded(List<GameDeal> deals, Map<String, Store> stores, Bitmap backgroundImage, String title)  {
    currentDeals = deals;
    this.stores = stores;
    this.backgroundImage = backgroundImage;
    if (widgetIds == null) return;
    AppWidgetManager manager = AppWidgetManager.getInstance(this);
    RemoteViews views = new RemoteViews(getPackageName(), R.layout.game_widget);

    views.setImageViewBitmap(R.id.widget_background, backgroundImage);
    views.setTextViewText(R.id.game_title, title);

    for (int id : widgetIds) {
      manager.updateAppWidget(id, views);
    }
  }


  private String getFavoriteGameId() {
    SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    String json = preferences.getString(FAVORITE_KEY, null);
    if (json == null) {
      return  null;
    }

    try {
      JSONObject deal = new JSONObject(json);
      return deal.getString("gameID");
    } catch (JSONException e) {
      return null;
    }
  }

  private void startCarousel() {
    runnable  = new Runnable() {
      @Override
      public void run() {
        if (!currentDeals.isEmpty()) {
          showCurrentDeal();
          currentDealIndex = (currentDealIndex + 1) % currentDeals.size();
        }
        handler.postDelayed(this, CAROUSEL_INTERVAL);
      }
    };
    handler.post(runnable);
  }


  private void startPreferenceChecker() {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        String gameId = getFavoriteGameId();
        if (gameId != null && !gameId.equals(lastGameId)) {
          lastGameId = gameId;
          currentDealIndex = 0;
          new FetchAllDealsTask(WidgetUpdateService.this).execute(gameId);
        }
        handler.postDelayed(this, 2000);
      }
    }, 2000);
  }
  private void showCurrentDeal() {
    if (widgetIds == null || currentDeals.isEmpty()) return;
    GameDeal deal = currentDeals.get(currentDealIndex);
    Store store = stores.get(deal.getStoreID());
    int nextPage = currentPage == 0 ? 1 : 0;

    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    RemoteViews views = getRemoteViews(deal, store, nextPage);

    for (int widgetId : widgetIds) {
      appWidgetManager.updateAppWidget(widgetId, views);
    }

  }

  private RemoteViews getRemoteViews(GameDeal deal, Store store, int nextPage) {
    RemoteViews views = new RemoteViews(getPackageName(), R.layout.game_widget);
    views.setTextViewText(
      nextPage == 0 ? R.id.store_name_0 : R.id.store_name_1,
      store != null ? store.getStoreName() : ""
    );

    views.setTextViewText(
      nextPage == 0 ? R.id.sale_price_0 : R.id.sale_price_1,
      "$" + deal.getPrice()
    );
    views.setTextViewText(
      nextPage == 0 ? R.id.normal_price_0 : R.id.normal_price_1,
      "$" + deal.getRetailPrice()
    );
    views.setTextViewText(
      nextPage == 0 ? R.id.savings_0 : R.id.savings_1,
      "-" + deal.getSavings().split("\\.")[0] + "%"
    );

    assert store != null;
    views.setImageViewBitmap(
      nextPage == 0 ? R.id.store_logo_0 : R.id.store_logo_1,
      store.getLogoBitmap()
    );
    views.setDisplayedChild(R.id.view_flipper, nextPage);
    currentPage = nextPage;

    return views;
  }

}



