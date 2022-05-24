package com.example.tour_planner.utils.api;

// Tour class

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

//JSONObject
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import javafx.scene.image.Image;
import org.json.JSONObject;

import javax.imageio.ImageIO;

public class mapAPI
{
    public static int sendRequest(String oldName, String start, String end, String transport,String title,String description, String mode) throws IOException {
        System.out.println(end);
        System.out.println(start);

        // URL for the MAP API
        String url = "https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from="+start+"&to="+end+"&transportMode="+transport+"&routeType="+"";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // set it as a GET request
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        // add that to the Logger instead of a system out print
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        // get the Response => response is a JSON object
        System.out.println("Response : " + response);

        // get the json object
        JSONObject myResponse = new JSONObject(response.toString());
        float distance = myResponse.getJSONObject("route").getFloat("distance");
        String duration = myResponse.getJSONObject("route").getString("formattedTime");
        String session = myResponse.getJSONObject("route").getString("sessionId");
        if(distance == 0) return 0;

        String RouteUrl = "https://www.mapquestapi.com/staticmap/v5/map?key="+ session +"&size=600,400@2x";

        // save Route Img
        saveRouteImage(RouteUrl, title);

        // if distance is 0 ==> it is not possible to take the route that way , let the user re-enter everything
        Tour newTour = new Tour(title, description, start, end, transport, distance, duration);
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        if(Objects.equals(mode, "create")) handler.createTour(newTour);
        if(Objects.equals(mode, "edit")) handler.modifyTour(oldName, newTour);
        return 1; // cause everything is alright

    }

    public static void saveRouteImage(String url, String name) throws IOException {
        BufferedImage BufImg = null;
        URL obj = new URL(url);
        BufImg = ImageIO.read(obj);
        FileOutputStream fout =  new FileOutputStream("src/main/java/com/example/tour_planner/utils/maps/"+ name + "_map.jpg");
        ImageIO.write( BufImg, "png", fout);
    }
}
