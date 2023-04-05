import pojo.Component;
import pojo.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class test {

    public static List<Event> futureEvent;

    public static void main(String[] args) {
        futureEvent = new ArrayList<>();

        Component component1 = new Component("c2", false,1,0);
        Event e1 = new Event("AR2", 1.0, component1);
        futureEvent.add(e1);

        Event e2 = new Event("AR2", 3.0, component1);
        futureEvent.add(e2);

        Event e3 = new Event("AR2", 2.0, component1);
        futureEvent.add(e3);
        System.out.println(futureEvent);

        futureEvent.sort(Comparator.comparing(Event::getEventTime));
        System.out.println(futureEvent);

    }
}
