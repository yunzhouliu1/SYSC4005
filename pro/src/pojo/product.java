package pojo;

public class product {
    private String type;
    private double clock;

    public product(String type, double clock) {
        this.type = type;
        this.clock = clock;
    }

    public product() {
    }

    @Override
    public String toString() {
        return "product{" +
                "type='" + type + '\'' +
                ", clock=" + clock +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getClock() {
        return clock;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }
}
