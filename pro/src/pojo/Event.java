package pojo;

public class Event {
    //Event type
    // AR1: Component enters Inspector 1
    // AR2: Component enters Inspector 2
    // LEI1: The Component leaves Inspector 1
    // LEI2: The Component leaves Inspector 2
    // 262 main
    // ARW1: Component into workstation1 buffer
    // ARW2: Component into workstation2 buffer
    // ARW3: Component into workstation3 buffer

    
    // LEW1: product leave workstation1 buffer
    // LEW2: product leave workstation2 buffer
    // LEW3: product leave workstation3 buffer
    private String eventType;
    private Double eventTime;
    private Component component;

    public Event() {
    }

    public Event(String eventType, Double eventTime, Component component) {
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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", Component=" + component +
                '}';
    }
}
