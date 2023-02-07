import pojo.Component;
import pojo.Event;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static double clock;
    private static final double Inspector_s_time = 5;
    private static final double Workstation_s_time = 6;

    private static List<Event> futureEvent;

    // baffer bofore workstation max = 2
    private static List<Component> buff_c1w1, buff_c1w2,buff_c1w3, buff_c2w2, buff_c3w3 ;

    // workstation is work or not
    private static boolean w1, w2, w3;

    // Inspector can work or not
    private static boolean Inspector1, Inspector2;

    // the number of product
    private static int p1, p2, p3;

    public static void main(String[] args) {
        initialization();

        //Main while loop
        while ((clock <= 30)){
            Event imminentEvent = futureEvent.get(0);
            futureEvent.remove(0);
            clock = imminentEvent.getEventTime();
            ProcessSimEvent(imminentEvent);
        }

    }

    private static void ProcessSimEvent(Event imminentEvent){
        String eventType = imminentEvent.getEventType();
        switch (eventType){
            case "AR1":
                ProcessAR1(imminentEvent);
                break;
            case "AR2":
                ProcessAR2(imminentEvent);
                break;
            case "LEI1":
                ProcessLEI1(imminentEvent);
                break;
            case "LEI2":
                ProcessLEI2(imminentEvent);
                break;
            case "ARW1":
                ProcessARW1(imminentEvent);
                break;
            case "ARW2":
                ProcessARW2(imminentEvent);
                break;
            case "ARW3":
                ProcessARW3(imminentEvent);
                break;
            case "LEW1":
                ProcessLEW1(imminentEvent);
                break;
            case "LEW2":
                ProcessLEW2(imminentEvent);
                break;
            case "LEW3":
                ProcessLEW3(imminentEvent);
                break;
        }

    }

    private static void ProcessAR1(Event imminentEvent) {
        Component component = imminentEvent.getComponent();
        component.setCheck(true);
        Event e = new Event("LEI1",clock+Inspector_s_time,component);
        futureEvent.add(e);
    }

    private static void ProcessAR2(Event imminentEvent) {
        Component component = imminentEvent.getComponent();
        component.setCheck(true);
        Event e = new Event("LEI2",clock+Inspector_s_time,component);
        futureEvent.add(e);
    }

    private static void ProcessLEI1(Event imminentEvent){
        Component component = imminentEvent.getComponent();

        if(buff_c1w1.size() == 2 && buff_c1w2.size() == 2 && buff_c1w1.size() ==2) {
            Inspector1 = true;
        }else {
            Inspector1 = false;

            int smallestBuffer = getSmallestBuffer();
            switch (smallestBuffer){
                case 1:
                    if (buff_c1w1.size() < 2){
                        buff_c1w1.add(component);
                        Event e = new Event("ARW1",clock,component);
                        futureEvent.add(e);
                    }
                    break;
                case 2:
                    if (buff_c1w2.size() < 2){
                        buff_c1w2.add(component);
                        Event e = new Event("ARW2",clock,component);
                    }
                    break;
                case 3:
                    if (buff_c1w3.size() < 2){
                        buff_c1w3.add(component);
                        Event e = new Event("ARW3",clock,component);
                    }
                    break;
            }
            Component component1 = new Component("c1",false);
            Event e= new Event("AR1",clock,component1);
            futureEvent.add(e);
        }

    }

    private static void ProcessLEI2(Event imminentEvent) {
        Component component = imminentEvent.getComponent();
        String type = component.getType();
        if (type == "c2") {
            if (buff_c2w2.size() == 2) {
                Inspector2 = true;
            } else {
            Inspector2 = false;
            if (buff_c2w2.size() < 2) {
                buff_c2w2.add(component);
                Event e = new Event("ARW2", clock, component);
                futureEvent.add(e);

                Component component1 = new Component("c2", false);
                Event e1 = new Event("AR2", clock, component1);
                futureEvent.add(e1);
                }
            }
        }else if (type == "c3"){
            if (buff_c3w3.size() == 2) {
                Inspector2 = true;
            } else {
                Inspector2 = false;
                if (buff_c3w3.size() < 2) {
                    buff_c3w3.add(component);
                    Event e = new Event("ARW3", clock, component);
                    futureEvent.add(e);

                    Component component1 = new Component("c3", false);
                    Event e1 = new Event("AR2", clock, component1);
                    futureEvent.add(e1);
                }
            }
        }
     }

    private static void ProcessARW1(Event imminentEvent) {
        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW1", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }

    private static void ProcessARW2(Event imminentEvent) {
        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW2", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }

    private static void ProcessARW3(Event imminentEvent) {
        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW3", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }


    private static void ProcessLEW1(Event imminentEvent) {
        Component remove = buff_c1w1.remove(0);
        p1++;
        Event e = new Event("AR1", clock,remove);
        futureEvent.add(e);
    }

    private static void ProcessLEW2(Event imminentEvent) {
        Component remove = buff_c2w2.remove(0);
        Component remove1 = buff_c1w2.remove(0);
        p2++;

        Event e = new Event("AR2", clock,remove);
        futureEvent.add(e);
        Event e1 = new Event("AR1", clock,remove1);
        futureEvent.add(e1);
    }

    private static void ProcessLEW3(Event imminentEvent) {
        Component remove = buff_c1w3.remove(0);
        Component remove1 = buff_c3w3.remove(0);
        p3++;

        Event e = new Event("AR1", clock,remove);
        futureEvent.add(e);
        Event e1 = new Event("AR2", clock,remove1);
        futureEvent.add(e1);
    }



    private static void initialization(){
        clock = 0;
        Inspector1 = false;
        Inspector2 = false;
        w1 = false;
        w2 = false;
        w3 = false;
        futureEvent = new ArrayList<>();
        p1 = 0;
        p2 = 0;
        p3 = 0;

        Component c1 = new Component("c1",false);
        Event evt = new Event("AR1",clock,c1);
        futureEvent.add(evt);

        Component c2 = new Component("c2",false);
        Event evt1 = new Event("AR2",clock,c2);
        futureEvent.add(evt1);
    }

    // return the buffer that has high priority
    private static int getSmallestBuffer() {
        int buff_c1w1_size = buff_c1w1.size();
        int buff_c1w2_size = buff_c1w2.size();
        int buff_c1w3_size = buff_c1w3.size();

        if (buff_c1w1_size == buff_c1w2_size && buff_c1w1_size == buff_c1w3_size) {
            return 1;
        }else if (buff_c1w2_size < buff_c1w1_size && buff_c1w2_size < buff_c1w3_size){
            return 2;
        }else if (buff_c1w3_size < buff_c1w1_size && buff_c1w3_size < buff_c1w2_size) {
            return 3;
        }else {
            return 1;
        }
    }

}
