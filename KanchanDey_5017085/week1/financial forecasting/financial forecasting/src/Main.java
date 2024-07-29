import java.util.Scanner;

 class FinancialForecaster {

    public static double calculateFutureValue(double initialValue, double growthRate, int years) {
        if (years == 0) {
            return initialValue;
        } else {
            return calculateFutureValue(initialValue * (1 + growthRate), growthRate, years - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the initial financial value:");
        double initialValue = scanner.nextDouble();

        double[] pastGrowthRates = new double[10];
        System.out.println("Enter the past growth rates (one per year):");
        for (int i = 0; i < 10; i++) {
            pastGrowthRates[i] = scanner.nextDouble();
        }

        System.out.println("Enter the number of years for prediction:");
        int predictionYears = scanner.nextInt();

        double averageGrowthRate = calculateAverageGrowthRate(pastGrowthRates);

        System.out.println("Predicted future value after " + predictionYears + " years:");
        System.out.println(calculateFutureValue(initialValue, averageGrowthRate, predictionYears));

        scanner.close();
    }

    private static double calculateAverageGrowthRate(double[] growthRates) {
        double sum = 0.0;
        for (double rate : growthRates) {
            sum += rate;
        }
        return sum / growthRates.length;
    }
}
