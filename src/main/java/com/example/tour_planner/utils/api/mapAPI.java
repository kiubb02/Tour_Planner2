package com.example.tour_planner.utils.api;

// Tour class

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.logging.LogManager;

//JSONObject
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import javafx.scene.image.Image;
import org.json.JSONObject;

import javax.imageio.ImageIO;

public class mapAPI

{

    private static TourDaoImpl handler = new TourDaoImpl();
    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public static int sendRequest(String oldName, String start, String end, String transport,String title,String description, String mode) throws IOException {
        logger.debug(start);
        logger.debug(end);

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
        logger.debug("\nSending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        // get the Response => response is a JSON object
        logger.debug("Response : " + response);

        // get the json object
        JSONObject myResponse = new JSONObject(response.toString());
        float distance = myResponse.getJSONObject("route").getFloat("distance");
        String duration = myResponse.getJSONObject("route").getString("formattedTime");
        String session = myResponse.getJSONObject("route").getString("sessionId");
        if(distance == 0) return 0;

        String RouteUrl = "https://www.mapquestapi.com/staticmap/v5/map?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&session="+ session +"&size=600,400@2x";

        // if distance is 0 ==> it is not possible to take the route that way , let the user re-enter everything
        Tour newTour = new Tour(title, description, start, end, transport, distance, duration);
        if(Objects.equals(mode, "create"))
        {
            handler.createTour(newTour);
            saveRouteImage(RouteUrl, title, null);
        }
        if(Objects.equals(mode, "edit"))
        {
            handler.modifyTour(oldName, newTour);
            saveRouteImage(RouteUrl, title, oldName);
        }
        return 1; // cause everything is alright

    }

    public static void saveRouteImage(String url, String name, String oldName) throws IOException {
        BufferedImage BufImg = null;
        URL obj = new URL(url);
        BufImg = ImageIO.read(obj.openStream());
        FileOutputStream fout = null;
        if (oldName != null)
        {
            File file =  new File("src/main/java/com/example/tour_planner/utils/maps/"+ oldName + "_map.jpg");
            File newFile = new File("src/main/java/com/example/tour_planner/utils/maps/"+ name + "_map.jpg");
            boolean flag = file.renameTo(newFile);
            if(flag) System.out.println("NOT CHANGED");
        }
        fout =  new FileOutputStream("src/main/java/com/example/tour_planner/utils/maps/"+ name + "_map.jpg");
        ImageIO.write( BufImg, "jpg", fout);
    }

    public static void deleteRouteImage(String name)
    {
        File RouteImg = new File("src/main/java/com/example/tour_planner/utils/maps/"+ name + "_map.jpg");
        if (RouteImg.delete()) { System.out.println("Route Image of " + name + "successfully deleted"); }
        else { System.out.println("Route Image of " + name + " could not be deleted"); }
    }
}
