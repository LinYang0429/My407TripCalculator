package Calculator;

import Calculator.model.Interchanges;
import Calculator.model.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static Calculator.Main.*;

public class CalculatorSolution2 {
    double[][] distances;
    Map<String, Integer> myLocationDic;

    public CalculatorSolution2() {
        this.myLocationDic = new HashMap<>();
        this.distances = new double[44][44];
        for (double[] row : distances) {
            Arrays.fill(row, 0.0);
        }
        setup();
    }

    // read data from json file
    private void setup() {
        JsonReader reader = new JsonReader();
        Interchanges data = reader.readJson();
        ArrayList<Location> myLocations = data.getMyLocations();
        this.myLocationDic = data.getMyLocationDic();

        // add distance of first location to the distance 2D array
        for (int i = 1; i < myLocations.size(); i++) {
            Location location = myLocations.get(0);
            distances[0][i] += location.getToNext();
        }

        // add the rest distance to the distance 2D array
        for (int i = 1; i < myLocations.size(); i++) {
            Location location = myLocations.get(i);
            double toPre = location.getToPrevious();
            double toNext = myLocations.get(i).getToNext();
            for (int j = 0; j < i; j++) {
                for (int k = i; k < myLocations.size(); k++) {
                    distances[k][j] += toPre;
                }
            }
            for (int j = i + 1; j < myLocations.size(); j++) {
                for (int k = 0; k < i + 1; k++) {
                    distances[k][j] += toNext;
                }
            }
        }

        // handle the enter and exit false situation
        for (int i = 1; i < myLocations.size(); i++) {
            Location location = myLocations.get(i);
            boolean preEnterFlag = location.isPreEnterAllow();
            boolean preExitFlag = location.isPreExitAllow();
            boolean nextEnterFlag = location.isNextEnterAllow();
            boolean nextExitFlag = location.isNextExitAllow();

            if (!preEnterFlag) {
                for (int j = 0; j < i; j++) {
                    distances[i][j] = -1;
                }
            }
            if (!nextEnterFlag) {
                for (int j = i + 1; j < myLocations.size(); j++) {
                    distances[i][j] = -1;
                }
            }
            if (!preExitFlag) {
                for (int j = i; j < myLocations.size(); j++) {
                    distances[j][i - 1] = -2;
                }
            }
            if (!nextExitFlag) {
                for (int j = 0; j < i + 1; j++) {
                    distances[j][i + 1] = -2;
                }
            }
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

        if (distances[indexStart][indexEnd] == -1) {
            System.out.println("Start location not allowed to enter!");
            return -1;
        }

        if (distances[indexStart][indexEnd] == -2) {
            System.out.println("End location not allowed to exit!");
            return -1;
        }

        if ((indexStart < 41 && indexEnd > 38) || (indexEnd < 41 && indexStart > 38)) {
            System.out.println("Sideline 26, 22 Under Dev! Distances between need to be fixed when these are built!");
        }

        printResult(distances[indexStart][indexEnd], distances[indexStart][indexEnd] * ROLLRATE);
        return Double.valueOf(costFormat.format(distances[indexStart][indexEnd] * ROLLRATE));
    }

    // print results
    private void printResult(double distance, double cost) {
        System.out.println("Distance: " + distanceFormat.format(distance));
        System.out.println("Cost: " + costFormat.format(cost));
    }
}
