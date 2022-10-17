package Calculator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalculatorSolution1 {
    ArrayList<Location> myLocations;
    Map<String, Integer> myLocationDic;
    private static final double ROLLRATE = 0.25;
    private static final DecimalFormat distanceFormat = new DecimalFormat("0.000");
    private static final DecimalFormat costFormat = new DecimalFormat("0.00");

    public CalculatorSolution1() {
        myLocations = new ArrayList<>();
        myLocationDic = new HashMap<>();
        setup();
    }

    // read data from json file
    private void setup() {
        try{
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

    }

    // calculate cost, take two string of locations as input
    public double costOfTrip(String start, String end) {
        int indexStart = myLocationDic.getOrDefault(start, -1);
        int indexEnd = myLocationDic.getOrDefault(end, -1);

        if (indexStart == -1 || indexEnd == -1) {
            System.out.println("Input Error: location not found!");
            return -1;
        }

        if (indexStart == indexEnd) {
            printResult(0.0, 0.0);
            return 0.0;
        }

        if ((indexStart < indexEnd && !myLocations.get(indexStart).isNextEnterAllow()) ||
                (indexStart > indexEnd && !myLocations.get(indexStart).isPreEnterAllow())) {
            System.out.println("Start location not allowed to enter!");
            return -1;
        }

        double distance = countDistance(indexStart, indexEnd);
        if (distance == -1) {
            System.out.println("End location not allowed to exit!");
            return -1;
        }
        printResult(distance, distance * ROLLRATE);
        return Double.valueOf(costFormat.format(distance * ROLLRATE));
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

    // calculate distance for both directions
    private double countDistance(int indexStart, int indexEnd) {
        double distance = 0;

        if (indexStart < indexEnd) {
            for (int i = indexStart; i < indexEnd; i++) {
                if (myLocations.get(i).isInDev()) {
                    System.out.println(myLocations.get(i).getName() + " Under Dev! Distances between need to be fixed when these are built!");
                }
                if ((i + 1 == indexEnd) && (!myLocations.get(i).isNextExitAllow())) { // check exit
                    distance = -1;
                    return distance;
                }
                distance += myLocations.get(i).getToNext();
            }
        } else {
            for (int i = indexStart; i > indexEnd; i--) {
                if (myLocations.get(i).isInDev()) {
                    System.out.println(myLocations.get(i).getName() + " Under Dev! Distances between need to be fixed when these are built!");
                }
                if (i - 1 == indexEnd && !myLocations.get(i).isPreExitAllow()) { // check exit
                    distance = -1;
                    return distance;
                }
                distance += myLocations.get(i).getToPrevious();
            }
        }

        return distance;
    }

    // print results
    private void printResult(double distance, double cost) {
        System.out.println("Distance: " + distanceFormat.format(distance));
        System.out.println("Cost: " + costFormat.format(cost));
    }
}
