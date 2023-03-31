package pojo;

public class Component {
    private String type;
    private Boolean check;
    private double time;

    public Component(String type, Boolean check, double time) {
        this.type = type;
        this.check = check;
        this.time = time;
    }

    public Component() {
    }

    @Override
    public String toString() {
        return "Component{" +
                "type='" + type + '\'' +
                ", check=" + check +
                ", time=" + time +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
