package pojo;

public class event {
    //event type
    // AR1: Component enters Inspector 1
    // AR2: Component enters Inspector 2
    // LEI1: The component leaves Inspector 1
    // LEI2: The component leaves Inspector 2
    // ARW1: component into workstation1 buffer
    // ARW2: component into workstation2 buffer
    // ARW3: component into workstation3 buffer
    // LEW1: product leave workstation1 buffer
    // LEW2: product leave workstation2 buffer
    // LEW3: product leave workstation3 buffer
    private String eventType;
    private Double eventTime;
    private component component;

    public event() {
    }

    public event(String eventType, Double eventTime, pojo.component component) {
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.component = component;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Double getEventTime() {
        return eventTime;
    }

    public void setEventTime(Double eventTime) {
        this.eventTime = eventTime;
    }

    public pojo.component getComponent() {
        return component;
    }

    public void setComponent(pojo.component component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "event{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", component=" + component +
                '}';
    }
}
