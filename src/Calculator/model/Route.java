package Calculator.model;

public class Route {
    private double distance;
    private boolean enterFlag;
    private boolean exitFlag;

    public Route(double distance, boolean enterFlag, boolean exitFlag) {
        this.distance = distance;
        this.enterFlag = enterFlag;
        this.exitFlag = exitFlag;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isEnterFlag() {
        return enterFlag;
    }

    public boolean isExitFlag() {
        return exitFlag;
    }
}
