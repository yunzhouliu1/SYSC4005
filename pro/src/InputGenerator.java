public class InputGenerator {
    public static double a;
    public static double c;
    public static double m;
    public static double x0;

    public static double x;
    public static double r;
    
    public static double test1;
    public static double test2;
    public static double test3;

    public InputGenerator() {

    }

    public static double getFirstRandomNumber(){
        //generate random integers
        x = ((a*x0)+c) % m;
        //generate random numbers
        r = x/m;
        double randomNumber =  r;
        return randomNumber;
    }    

    public static double getRandomNumber(){
        //generate random integers
        x = ((a*x)+c) % m;
        //generate random numbers
        r = x/m;
        double randomNumber =  r;
        return randomNumber;
    }   

    public static void main(String[] args) {
        double test1;
        double test2;
        double test3;

        startInputGenerator();

        test1 = getFirstRandomNumber();
        System.out.println("test1 is: " + test1);

        test2 = getRandomNumber();
        System.out.println("test2 is: " + test2);

        test3 = getRandomNumber();
        System.out.println("test3 is: " + test3);
    }

    private static void startInputGenerator() {
        //assign values for a, c, m, x0
        a = 13540;
        c = 12;
        m = 256; // between 200-300 (2^8 = 256)
        x0 = 13547;
    }
}
