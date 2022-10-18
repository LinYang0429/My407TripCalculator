package Calculator;

import Calculator.model.Interchanges;
import Calculator.model.Location;
import Calculator.model.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {

    private JsonReader() {
    }

    private static class JsonReaderSingleton {
        private static final JsonReader INSTANCE = new JsonReader();
    }

    public static JsonReader getInstance() {
        return JsonReaderSingleton.INSTANCE;
    }

    public Interchanges readJson() {
        ArrayList<Location> myLocations = new ArrayList<>();
        Map<String, Integer> myLocationDic = new HashMap<>();

        try {
            URL url = getClass().getResource("./resource/interchanges.json");
            File interchangesFile = new File(url.getPath());
            JSONParser parser = new JSONParser();
            JSONObject interChanges = (JSONObject) parser.parse(new FileReader(interchangesFile));
            JSONObject locations = (JSONObject) interChanges.get("locations");
            int locationSize = 0;

            for (int i = 0; i < 46; i++) {
                JSONObject location = (JSONObject) locations.get(Integer.toString(i + 1));
                if (location == null) {
                    continue;
                }

                String name = (String) location.get("name");
                String devComment = (String) location.get("devcomment");
                JSONArray routes = (JSONArray) location.get("routes");

                Route toPre = null;
                Route toNext = null;
                if (routes != null) {
                    JSONObject next = (JSONObject) routes.get(0);
                    toNext = readRoute(next);

                    if (routes.size() > 1) {
                        JSONObject pre = (JSONObject) routes.get(1);
                        toPre = readRoute(pre);
                    }
                }

                Location l = new Location(name, toPre, toNext, devComment != null);
                myLocations.add(l);
                myLocationDic.put(name, locationSize);

                locationSize++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Interchanges data = new Interchanges(myLocations, myLocationDic);
        return data;
    }

    // read route information from json
    private Route readRoute(JSONObject o) {
        double dis = -1;
        boolean enterFlag = true;
        boolean exitFlag = true;

        if (o.get("distance") instanceof Double) {
            dis = (double) o.get("distance");
        } else {
            dis = Double.longBitsToDouble((long) o.get("distance"));
        }
        if (o.get("enter") != null) {
            enterFlag = (boolean) o.get("enter");
        }
        if (o.get("exit") != null) {
            exitFlag = (boolean) o.get("exit");
        }
        Route route = new Route(dis, enterFlag, exitFlag);
        return route;
    }
}
