package Calculator;

public class Location {
    private final String name;
    private double toPrevious;
    private double toNext;
    private boolean inDev;
    private boolean preEnterFlag;
    private boolean preExitFlag;
    private boolean nextEnterFlag;
    private boolean nextExitFlag;

    public Location(String name, Route toPrevious, Route toNext, boolean inDev) {
        this.name = name;
        this.inDev = inDev;

        if (toPrevious != null) {
            this.toPrevious = toPrevious.getDistance();
            this.preEnterFlag = toPrevious.isEnterFlag();
            this.preExitFlag = toPrevious.isExitFlag();
        }

        if (toNext != null) {
            this.toNext = toNext.getDistance();
            this.nextEnterFlag = toNext.isEnterFlag();
            this.nextExitFlag = toNext.isExitFlag();
        }
    }

    public String getName() {
        return name;
    }

    public double getToPrevious() {
        return toPrevious;
    }

    public double getToNext() {
        return toNext;
    }

    public boolean isInDev() {
        return inDev;
    }

    public boolean isPreEnterAllow() {
        return preEnterFlag;
    }

    public boolean isPreExitAllow() {
        return preExitFlag;
    }

    public boolean isNextEnterAllow() {
        return nextEnterFlag;
    }

    public boolean isNextExitAllow() {
        return nextExitFlag;
    }
}
