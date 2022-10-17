package Calculator;

public class Main {

    public static void main(String[] args) {
        CalculatorSolution1 calculator = new CalculatorSolution1();
        if (args.length > 1) {
            System.out.println("costOfTrip from " + args[0] + " to " + args[1]);
            calculator.costOfTrip(args[0], args[1]);
        }
    }

}
