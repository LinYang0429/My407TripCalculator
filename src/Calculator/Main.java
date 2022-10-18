package Calculator;

import java.text.DecimalFormat;

public class Main {
    public static final double ROLLRATE = 0.25;
    public static final DecimalFormat distanceFormat = new DecimalFormat("0.000");
    public static final DecimalFormat costFormat = new DecimalFormat("0.00");

    public static void main(String[] args) {
        CalculatorSolution1 calculator = new CalculatorSolution1();
        if (args.length > 1) {
            System.out.println("costOfTrip from " + args[0] + " to " + args[1]);
            calculator.costOfTrip(args[0], args[1]);
        }
    }

}
