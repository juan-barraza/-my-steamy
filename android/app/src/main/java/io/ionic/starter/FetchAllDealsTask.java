package io.ionic.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FetchAllDealsTask {

  private static final String BASE_URL = "https://www.cheapshark.com/api/1.0";
  private WeakReference<WidgetUpdateService>  serviceRef;
  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private Handler mainHandler = new Handler(Looper.getMainLooper());


  public FetchAllDealsTask(WidgetUpdateService service) {
    this.serviceRef = new WeakReference<>(service);
  }

  public void execute(String gameId) {
    executor.execute(() -> {
      try {
        String gameUrl = BASE_URL + "/games?id=" + gameId;
        String gameResponse = fetchURL(gameUrl);
        JSONObject gameJson = new JSONObject(gameResponse);

        JSONObject info = gameJson.getJSONObject("info");
        String title = info.getString("title");
        String thumb = info.getString("thumb");
        Bitmap backgroundImage = downloadBitmap(thumb);
        JSONArray dealsArray = gameJson.getJSONArray("deals");
        List<GameDeal> deals = new ArrayList<>();
        for (int i = 0; i < dealsArray.length(); i++) {
          JSONObject dealJson = dealsArray.getJSONObject(i);
          GameDeal deal = new GameDeal();
          deal.setStoreID(dealJson.getString("storeID"));
          deal.setPrice(dealJson.getString("price"));
          deal.setRetailPrice(dealJson.getString("retailPrice"));
          deal.setSavings(dealJson.getString("savings"));
          deal.setTitle(title);
          deals.add(deal);
        }
        String storesUrl = BASE_URL + "/stores";
        String storesResponse = fetchURL(storesUrl);
        Map<String, Store> storesMap = getStringStoreMap(storesResponse);
        mainHandler.post(() -> {
          WidgetUpdateService service = serviceRef.get();
          if (service != null) {
            service.onDealsLoaded(deals, storesMap, backgroundImage, title);
          }
        });
      } catch (Exception e) {
          Log.e("FetchAllDealsTask", "Error fetching deals", e);
      }
    });

  }

  @NonNull
  private  Map<String, Store> getStringStoreMap(String storesResponse) throws JSONException {
    JSONArray storesArray = new JSONArray(storesResponse);
    Map<String, Store> storesMap = new HashMap<>();
    for (int i = 0; i < storesArray.length(); i++) {
      JSONObject s = storesArray.getJSONObject(i);
      JSONObject imagesJson = s.getJSONObject("images");
      Store store = new Store();
      store.setStoreID(s.getString("storeID"));
      store.setStoreName(s.getString("storeName"));
      Images image = new Images();
      image.setIcon("https://www.cheapshark.com" + imagesJson.getString("icon"));
      Bitmap icon = downloadBitmap(image.getIcon());
      store.setImages(image);
      store.setLogoBitmap(icon);
      storesMap.put(store.getStoreID(), store);

    }
    return storesMap;
  }


  private String fetchURL(String urlStr) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    BufferedReader bufferedReader = new BufferedReader(
      new InputStreamReader(connection.getInputStream())
    );
    StringBuilder result = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      result.append(line);
    }
    bufferedReader.close();
    return result.toString();

  }
  private Bitmap downloadBitmap(String url) {
    try {
      URL imageUrl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
      connection.setDoInput(true);
      connection.connect();
      InputStream input = connection.getInputStream();
      return BitmapFactory.decodeStream(input);
    } catch (Exception e) {
      return null;
    }
  }
}
