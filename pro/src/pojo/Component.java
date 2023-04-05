package pojo;

public class Component {
    private String type;
    private Boolean check;
    private double time;
    private double WStime;

    public Component(String type, Boolean check, double time, double WStime) {
        this.type = type;
        this.check = check;
        this.time = time;
        this.WStime = WStime;
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

    public double getWStime() {
        return WStime;
    }

    public void setWStime(double WStime) {
        this.WStime = WStime;
    }
}
