package pojo;

public class component {
    private String type;
    private Boolean check;


    public component(String type, Boolean check) {
        this.type = type; // C1 C2 C3
        this.check = check;
    }

    public component() {
    }

    @Override
    public String toString() {
        return "component{" +
                "type='" + type + '\'' +
                ", check=" + check +
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

}
