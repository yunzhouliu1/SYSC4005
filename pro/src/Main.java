import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;
import pojo.Component;
import pojo.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class main {

    private static double clock;
    private static final double Inspector_s_time = 5;
    private static final double Workstation_s_time = 6;

    private static List<Event> futureEvent;


    // baffer bofore workstation max = 2
    private static List<Component> buff_c1w1 = new ArrayList<>();
    private static List<Component> buff_c1w2 = new ArrayList<>();
    private static List<Component> buff_c1w3 = new ArrayList<>();
    private static List<Component> buff_c2w2 = new ArrayList<>();
    private static List<Component> buff_c3w3 = new ArrayList<>();
    // workstation is work or not
    private static boolean w1, w2, w3;

    // Inspector can work or not
    private static boolean Inspector1, Inspector2;

    // the number of product
    private static int p1, p2, p3;

    private static List ran = new ArrayList();

    public static void main(String[] args) {
        initialization();

        double x = 1;
        double a = 1103515245;
        double c = 12345;
        //m = 2^31
        double m = (double) Math.pow(2, 31);

        InputGeneratorV2 rng = new InputGeneratorV2(x, a, c, m);

        double[] randomNumbers = rng.generateRandomNumbers();

        for (double randomNumber : randomNumbers) {
            ran.add(randomNumber);
        }
        System.out.println(ran);
        //Main while loop
        while ((clock <= 100)){

            futureEvent.sort(Comparator.comparing(Event::getEventTime));

            Event imminentEvent = futureEvent.get(0);
            Event remove = futureEvent.remove(0);
            clock = imminentEvent.getEventTime();
            ProcessSimEvent(imminentEvent);

//            System.out.println(futureEvent.size());
//            System.out.println("---------------------");
//            System.out.println("buff_c1w1: " + buff_c1w1.size());
//            System.out.println("buff_c1w2: " + buff_c1w2.size());
//            System.out.println("buff_c1w3: " + buff_c1w3.size());
//            System.out.println("buff_c2w2: " + buff_c2w2.size());
//            System.out.println("buff_c3w3: " + buff_c3w3.size());

        }
//        System.out.println("prodect");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

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
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);
        double serviceTime = (-1/0.09654)*Math.log(1-remove);
//        System.out.println("serviceTime"+serviceTime);

        Component component = imminentEvent.getComponent();
        component.setCheck(true);

        Event e = new Event("LEI1",clock+serviceTime,component);
        futureEvent.add(e);
    }

    private static void ProcessAR2(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);

        Component component = imminentEvent.getComponent();
        component.setCheck(true);
//        System.out.println(component);

        if (component.getType().equals("c2")){
            double serviceTime = (-1/0.0644)*Math.log(1-remove);
//            System.out.println(serviceTime);
            Event e = new Event("LEI2",clock+serviceTime,component);
            futureEvent.add(e);
        }else {
            double serviceTime = (-1/0.0485)*Math.log(1-remove);
//            System.out.println(serviceTime);
            Event e = new Event("LEI2",clock+serviceTime,component);
            futureEvent.add(e);
        }


    }

    // generate AR1
    private static void ProcessLEI1(Event imminentEvent){
        Component component = imminentEvent.getComponent();
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        if(buff_c1w1.size() == 2 && buff_c1w2.size() == 2 && buff_c1w3.size() ==2) {
            Inspector1 = true;
            Event e = new Event("LEI1",clock+3,component); // all buffer is full , delay the event
            futureEvent.add(e);

        }else {
            Inspector1 = false;

            int smallestBuffer = getSmallestBuffer();
//            System.out.println("the smallest buffer is : " + smallestBuffer);
            switch (smallestBuffer){
                case 1:
                    if (buff_c1w1.size() < 2){
                        buff_c1w1.add(component);
                        Event e = new Event("ARW1",clock,component);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false);
                        Event e1= new Event("AR1",clock,component1);
                        futureEvent.add(e1);
                    }
                    break;
                case 2:
                    if (buff_c1w2.size() < 2){
                        buff_c1w2.add(component);
                        Event e = new Event("ARW2",clock,component);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false);
                        Event e1= new Event("AR1",clock,component1);
                        futureEvent.add(e1);
                    }
                    break;
                case 3:
                    if (buff_c1w3.size() < 2){
                        buff_c1w3.add(component);
                        Event e = new Event("ARW3",clock,component);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false);
                        Event e1= new Event("AR1",clock,component1);
                        futureEvent.add(e1);
                    }
                    break;
            }

        }

    }

    private static void ProcessLEI2(Event imminentEvent) {
        Component component = imminentEvent.getComponent();

        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;


        String type = component.getType();
        if (type.equals("c2")) {
//            System.out.println("c2");
            if (buff_c2w2.size() == 2) {
                Inspector2 = true;
                // 重设LEI2
                Event e = new Event("LEI2",clock+3,component); //  buffer c2 is full , delay the event
                futureEvent.add(e);
            } else {
                Inspector2 = false;

                buff_c2w2.add(component);
                Event e = new Event("ARW2", clock, component);
                futureEvent.add(e);

                Component component1 = new Component("c3", false);
                Event e1 = new Event("AR2", clock, component1);
                futureEvent.add(e1);

            }
        }else if (type.equals("c3")){
//            System.out.println("c3");
            if (buff_c3w3.size() == 2) {
                Inspector2 = true;
                // 重设LEI2
                Event e = new Event("LEI2",clock+3,component); //  buffer c2 is full , delay the event
                futureEvent.add(e);
            } else {
                Inspector2 = false;
                if (buff_c3w3.size() < 2) {
                    buff_c3w3.add(component);
                    Event e = new Event("ARW3", clock, component);
                    futureEvent.add(e);

                    //c3 or c2 random
                    Component component1 = new Component("c2", false);
                    Event e1 = new Event("AR2", clock, component1);
                    futureEvent.add(e1);
                }
            }
        }
     }

    private static void ProcessARW1(Event imminentEvent) {

        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;


        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW1", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }

    private static void ProcessARW2(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW2", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }

    private static void ProcessARW3(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW3", clock+Workstation_s_time,c);
        futureEvent.add(e);
    }


    private static void ProcessLEW1(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;
        if (buff_c1w1.size() > 0){
            Component remove = buff_c1w1.remove(0);
            p1++;
        }else{
            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW1", clock+5,c);
            futureEvent.add(e);
        }


    }

    private static void ProcessLEW2(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        if (buff_c2w2.size() > 0 && buff_c1w2.size() > 0){
            Component remove = buff_c2w2.remove(0);
            Component remove1 = buff_c1w2.remove(0);
            p2++;
        }else{
            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW2", clock+5,c);
            futureEvent.add(e);
        }



    }

    private static void ProcessLEW3(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        if (buff_c1w3.size() > 0 && buff_c3w3.size() > 0){
            Component remove = buff_c1w3.remove(0);
            Component remove1 = buff_c3w3.remove(0);
            p3++;
        }else{
            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW3", clock+5,c);
            futureEvent.add(e);
        }


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
//        Component c1 = new Component("c1",false);
//        Component c2 = new Component("c2",false);
//        Component c3 = new Component("c3",false);
//
//        buff_c1w1.add(c1);
//        buff_c1w2.add(c1);
//        buff_c1w3.add(c1);
//        buff_c3w3.add(c3);
//        buff_c2w2.add(c2);

        double x = 1;
        double a = 1103515245;
        double c = 12345;
        //m = 2^31
        double m = (double) Math.pow(2, 31);

        InputGeneratorV2 rng = new InputGeneratorV2(x, a, c, m);
        double[] doubles = rng.generateRandomNumbers();


        Component c11 = new Component("c1",false);
        Event evt = new Event("AR1",clock,c11);
        futureEvent.add(evt);

        Component c22 = new Component("c2",false);
        Event evt1 = new Event("AR2",clock,c22);
        futureEvent.add(evt1);
    }

    // return the buffer that has high priority
    private static int getSmallestBuffer() {
        int buff_c1w1_size = buff_c1w1.size();
        int buff_c1w2_size = buff_c1w2.size();
        int buff_c1w3_size = buff_c1w3.size();

        if (buff_c1w1_size == buff_c1w2_size && buff_c1w1_size == buff_c1w3_size && buff_c1w1_size < 2) {
            return 1;
        }else if (buff_c1w2_size < buff_c1w3_size && buff_c1w2_size == buff_c1w1_size && buff_c1w1_size < 2){
            return 1;
        }else if (buff_c1w2_size == buff_c1w3_size && buff_c1w2_size < 2) {
            return 2;
        }else if (buff_c1w2_size < buff_c1w3_size && buff_c1w2_size < buff_c1w1_size && buff_c1w2_size < 2){
            return 2;
        }else if (buff_c1w3_size < buff_c1w1_size && buff_c1w3_size < buff_c1w2_size && buff_c1w3_size <2) {
            return 3;
        }else if(buff_c1w3_size == buff_c1w1_size && buff_c1w1_size < 2){
            return 1;
        }

        return 4; // there are no buffer for comp 1, stop insp 1

    }

    public static double getClock() {
        return clock;
    }

    public static void setClock(double clock) {
        main.clock = clock;
    }

    public static double getInspector_s_time() {
        return Inspector_s_time;
    }

    public static double getWorkstation_s_time() {
        return Workstation_s_time;
    }

    public static List<Event> getFutureEvent() {
        return futureEvent;
    }

    public static void setFutureEvent(List<Event> futureEvent) {
        main.futureEvent = futureEvent;
    }

    public static List<Component> getBuff_c1w1() {
        return buff_c1w1;
    }

    public static void setBuff_c1w1(List<Component> buff_c1w1) {
        main.buff_c1w1 = buff_c1w1;
    }

    public static List<Component> getBuff_c1w2() {
        return buff_c1w2;
    }

    public static void setBuff_c1w2(List<Component> buff_c1w2) {
        main.buff_c1w2 = buff_c1w2;
    }

    public static List<Component> getBuff_c1w3() {
        return buff_c1w3;
    }

    public static void setBuff_c1w3(List<Component> buff_c1w3) {
        main.buff_c1w3 = buff_c1w3;
    }

    public static List<Component> getBuff_c2w2() {
        return buff_c2w2;
    }

    public static void setBuff_c2w2(List<Component> buff_c2w2) {
        main.buff_c2w2 = buff_c2w2;
    }

    public static List<Component> getBuff_c3w3() {
        return buff_c3w3;
    }

    public static void setBuff_c3w3(List<Component> buff_c3w3) {
        main.buff_c3w3 = buff_c3w3;
    }

    public static boolean isW1() {
        return w1;
    }

    public static void setW1(boolean w1) {
        main.w1 = w1;
    }

    public static boolean isW2() {
        return w2;
    }

    public static void setW2(boolean w2) {
        main.w2 = w2;
    }

    public static boolean isW3() {
        return w3;
    }

    public static void setW3(boolean w3) {
        main.w3 = w3;
    }

    public static boolean isInspector1() {
        return Inspector1;
    }

    public static void setInspector1(boolean inspector1) {
        Inspector1 = inspector1;
    }

    public static boolean isInspector2() {
        return Inspector2;
    }

    public static void setInspector2(boolean inspector2) {
        Inspector2 = inspector2;
    }

    public static int getP1() {
        return p1;
    }

    public static void setP1(int p1) {
        main.p1 = p1;
    }

    public static int getP2() {
        return p2;
    }

    public static void setP2(int p2) {
        main.p2 = p2;
    }

    public static int getP3() {
        return p3;
    }

    public static void setP3(int p3) {
        main.p3 = p3;
    }
}
