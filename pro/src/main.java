import pojo.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import pojo.component;
import pojo.event;
import pojo.product;

public class main {

    private static double clock;
    private static final double Inspector_s_time = 5;
    private static final double Workstation_s_time = 6;

    private static List<event> FutureEvent;

    // baffer bofore workstation max = 2
    private static List<component> buff_c1w1, buff_c1w2,buff_c1w3, buff_c2w2, buff_c3w3 ;

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
            event imminentEvent = FutureEvent.get(0);
            FutureEvent.remove(0);
            clock = imminentEvent.getEventTime();
            ProcessSimEvent(imminentEvent);
        }

    }

    private static void ProcessSimEvent(event imminentEvent){
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

    private static void ProcessAR1(event imminentEvent) {
        component component = imminentEvent.getComponent();
        component.setCheck(true);
        event e = new event("LEI1",clock+Inspector_s_time,component);
        FutureEvent.add(e);
    }

    private static void ProcessAR2(event imminentEvent) {
        component component = imminentEvent.getComponent();
        component.setCheck(true);
        event e = new event("LEI2",clock+Inspector_s_time,component);
        FutureEvent.add(e);
    }

    private static void ProcessLEI1(event imminentEvent){
        component component = imminentEvent.getComponent();

        if(buff_c1w1.size() == 2 && buff_c1w2.size() == 2 && buff_c1w1.size() ==2) {
            Inspector1 = true;
        }else {
            Inspector1 = false;

            int smallestBuffer = getSmallestBuffer();
            switch (smallestBuffer){
                case 1:
                    if (buff_c1w1.size() < 2){
                        buff_c1w1.add(component);
                        event e = new event("ARW1",clock,component);
                        FutureEvent.add(e);
                    }
                    break;
                case 2:
                    if (buff_c1w2.size() < 2){
                        buff_c1w2.add(component);
                        event e = new event("ARW2",clock,component);
                    }
                    break;
                case 3:
                    if (buff_c1w3.size() < 2){
                        buff_c1w3.add(component);
                        event e = new event("ARW3",clock,component);
                    }
                    break;
            }
            component component1 = new component("c1",false);
            event e= new event("AR1",clock,component1);
            FutureEvent.add(e);
        }

    }

    private static void ProcessLEI2(event imminentEvent) {
        component component = imminentEvent.getComponent();
        String type = component.getType();
        if (type == "c2") {
            if (buff_c2w2.size() == 2) {
                Inspector2 = true;
            } else {
            Inspector2 = false;
            if (buff_c2w2.size() < 2) {
                buff_c2w2.add(component);
                event e = new event("ARW2", clock, component);
                FutureEvent.add(e);

                component component1 = new component("c2", false);
                event e1 = new event("AR2", clock, component1);
                FutureEvent.add(e1);
                }
            }
        }else if (type == "c3"){
            if (buff_c3w3.size() == 2) {
                Inspector2 = true;
            } else {
                Inspector2 = false;
                if (buff_c3w3.size() < 2) {
                    buff_c3w3.add(component);
                    event e = new event("ARW3", clock, component);
                    FutureEvent.add(e);

                    component component1 = new component("c3", false);
                    event e1 = new event("AR2", clock, component1);
                    FutureEvent.add(e1);
                }
            }
        }
     }

    private static void ProcessARW1(event imminentEvent) {
        component c = imminentEvent.getComponent();
        event e = new event("LEW1", clock+Workstation_s_time,c);
        FutureEvent.add(e);
    }

    private static void ProcessARW2(event imminentEvent) {
        component c = imminentEvent.getComponent();
        event e = new event("LEW2", clock+Workstation_s_time,c);
        FutureEvent.add(e);
    }

    private static void ProcessARW3(event imminentEvent) {
        component c = imminentEvent.getComponent();
        event e = new event("LEW3", clock+Workstation_s_time,c);
        FutureEvent.add(e);
    }


    private static void ProcessLEW1(event imminentEvent) {
        component remove = buff_c1w1.remove(0);
        p1++;
        event e = new event("AR1", clock,remove);
        FutureEvent.add(e);
    }

    private static void ProcessLEW2(event imminentEvent) {
        component remove = buff_c2w2.remove(0);
        component remove1 = buff_c1w2.remove(0);
        p2++;

        event e = new event("AR2", clock,remove);
        FutureEvent.add(e);
        event e1 = new event("AR1", clock,remove1);
        FutureEvent.add(e1);
    }

    private static void ProcessLEW3(event imminentEvent) {
        component remove = buff_c1w3.remove(0);
        component remove1 = buff_c3w3.remove(0);
        p3++;

        event e = new event("AR1", clock,remove);
        FutureEvent.add(e);
        event e1 = new event("AR2", clock,remove1);
        FutureEvent.add(e1);
    }



    private static void initialization(){
        clock = 0;
        Inspector1 = false;
        Inspector2 = false;
        w1 = false;
        w2 = false;
        w3 = false;
        FutureEvent = new ArrayList<>();
        p1 = 0;
        p2 = 0;
        p3 = 0;

        component c1 = new component("c1",false);
        event evt = new event("AR1",clock,c1);
        FutureEvent.add(evt);

        component c2 = new component("c2",false);
        event evt1 = new event("AR2",clock,c2);
        FutureEvent.add(evt1);
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
