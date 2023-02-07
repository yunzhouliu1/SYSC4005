package pojo;

public class Component {
    private String type;
    private Boolean check;


    public Component(String type, Boolean check) {
        this.type = type; // C1 C2 C3
        this.check = check;
    }

    public Component() {
    }

    @Override
    public String toString() {
        return "Component{" +
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
