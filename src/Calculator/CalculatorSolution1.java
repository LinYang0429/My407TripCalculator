package Calculator;

import Calculator.model.Interchanges;
import Calculator.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Calculator.Main.*;

public class CalculatorSolution1 {
    ArrayList<Location> myLocations;
    Map<String, Integer> myLocationDic;

    public CalculatorSolution1() {
        myLocations = new ArrayList<>();
        myLocationDic = new HashMap<>();
        setup();
    }

    // read data from json file
    private void setup() {
        JsonReader reader = new JsonReader();
        Interchanges data = reader.readJson();
        myLocations = data.getMyLocations();
        myLocationDic = data.getMyLocationDic();
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
