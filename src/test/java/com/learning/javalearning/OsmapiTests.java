package com.learning.javalearning;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.capabilities.CapabilitiesDao;
import de.westnordost.osmapi.common.Handler;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.changes.SimpleMapDataChangesHandler;
import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.handler.MapDataHandler;
import de.westnordost.osmapi.traces.GpsTraceDetails;
import de.westnordost.osmapi.traces.GpsTracesDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class OsmapiTests {


    @Test
    public void testMutilMap() {
        OsmConnection osm = new OsmConnection(
                "https://api.openstreetmap.org/api/0.6/",
                "my user agent", null);
        System.out.println(osm.getApiUrl());
        MapDataDao mapDao = new MapDataDao(osm);
        MapDataHandler dataHandler = new SimpleMapDataChangesHandler();
        BoundingBox bb = new BoundingBox(37.2690844,127.095189,37.2690844,127.095189);
        mapDao.getMap(bb, dataHandler);
        System.out.println(bb);
    }


    @Test
    public void testSingleMap() {
        OsmConnection osm = new OsmConnection(
                "https://api.openstreetmap.org/api/0.6/",
                "my user agent", null);
        System.out.println(osm.getApiUrl());
        GpsTracesDao gpsTracesDao = new GpsTracesDao(osm);
        CapabilitiesDao cd = new CapabilitiesDao(osm);
        gpsTracesDao.getMine(new Handler<GpsTraceDetails>() {
            @Override
            public void handle(GpsTraceDetails gpsTraceDetails) {
                System.out.println(gpsTraceDetails.id);
            }
        });
    }

    @Test
    public void testQueryMap() throws IOException {
        String url = "https://nominatim.openstreetmap.org/search/广州市,京畿道?format=json&addressdetails=1&limit=1&polygon_svg=0";
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .addHeader("accept-language","zh-CN,zh;q=0.9")
                .build();

        Response response = client.newCall(request).execute();


        System.out.println(response.body().string());

    }

}
