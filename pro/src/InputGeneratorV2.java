public class InputGeneratorV2 {
    private final double a;
    private final double c;
    private final double m;
    private double x;

    public InputGeneratorV2(double x, double a, double c, double m) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.x = x;
    }

    //based on LCM method
    public double[] generateRandomNumbers() {
        //generate 256 (2^8) random numbers
        double[] randomNumbers = new double[256];
        for (int i = 0; i < 256; i++) {
            //generate random integers
            x = (a * x + c) % m;
            //generate random numbers based on integers
            randomNumbers[i] = (double) x / m;
        }
        return randomNumbers;
    }

    public double generateRND() {
        x = (a * x + c) % m;
        double out = (double) x / m;
        return out;
    }

    public static void main(String[] args) {
        //assign values to a, c, m, x0
        //x0 = 1
        double x = 1;
        double a = 1103515245;
        double c = 12345;
        //m = 2^31
        double m = (double) Math.pow(2, 31);

        InputGeneratorV2 rng = new InputGeneratorV2(x, a, c, m);
        double[] randomNumbers = rng.generateRandomNumbers();

        for (double randomNumber : randomNumbers) {
            System.out.println(randomNumber);
        }
    }
}