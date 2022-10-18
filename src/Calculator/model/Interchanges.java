package Calculator.model;

import Calculator.model.Location;

import java.util.ArrayList;
import java.util.Map;

public class Interchanges {
    ArrayList<Location> myLocations;
    Map<String, Integer> myLocationDic;

    public Interchanges(ArrayList<Location> myLocations, Map<String, Integer> myLocationDic) {
        this.myLocations = myLocations;
        this.myLocationDic = myLocationDic;
    }

    public ArrayList<Location> getMyLocations() {
        return myLocations;
    }

    public Map<String, Integer> getMyLocationDic() {
        return myLocationDic;
    }
}
