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

    private static double arrivalc1w1, arrivalc1w2, arrivalc1w3, arrivalc2w2, arrivalc3w3;
    private static double allNumberComp;

    private static double numberWS1;

    private static double numberWS2;

    private static double numberWS3;

    private static List ran = new ArrayList();

    private static double inspector1_blocked_time_s;
    private static double inspector1_blocked_time_e;
    private static double inspector1_blocked_time;

    private static double inspector2_blocked_time_s;
    private static double inspector2_blocked_time_e;
    private static double inspector2_blocked_time;

    private static double workstation1_idle_time;
    private static double workstation1_idle_time_s;
    private static double workstation1_idle_time_e;

    private static double workstation2_idle_time;
    private static double workstation2_idle_time_s;
    private static double workstation2_idle_time_e;

    private static double workstation3_idle_time;
    private static double workstation3_idle_time_s;
    private static double workstation3_idle_time_e;

    private static List<Double> c1w1 = new ArrayList<>();
    private static List<Double> c1w2 = new ArrayList<>();
    private static List<Double> c1w3 = new ArrayList<>();
    private static List<Double> c2w2 = new ArrayList<>();
    private static List<Double> c3w3 = new ArrayList<>();

    private static double totalDelay;

    private static double ws1Delay,ws2Delay,ws3Delay;

    public static void main(String[] args) {
        initialization();
        double allstep = 0;
        double x = 1;
        double minRandom = 10000;
        double maxRandom = 1500000000;
        double a = (double)Math.floor(Math.random() * (maxRandom - minRandom + 1) + minRandom);  //1103515245;
        double c = 12345;
        //m = 2^31
        double m = (double) Math.pow(2, 31);

        InputGeneratorV2 rng = new InputGeneratorV2(x, a, c, m);

        double[] randomNumbers = rng.generateRandomNumbers();

        for (double randomNumber : randomNumbers) {
            ran.add(randomNumber);
        }
//        System.out.println(ran);
        //Main while loop
        while ((clock <= 60*10)){
//            System.out.println("random: "+ ran.size());
//            System.out.println("Inspector1: "+ Inspector1);
//            System.out.println("Inspector2: "+ Inspector2);



            futureEvent.sort(Comparator.comparing(Event::getEventTime));

            Event imminentEvent = futureEvent.get(0);
            Event remove = futureEvent.remove(0);
            clock = imminentEvent.getEventTime();
            ProcessSimEvent(imminentEvent);

            if (Inspector1 && inspector1_blocked_time_s == 0){
                inspector1_blocked_time_s = imminentEvent.getEventTime();
//                System.out.println("inspector1_blocked_time_s" + inspector1_blocked_time_s);

            }
            if (!Inspector1){
                inspector1_blocked_time_e = imminentEvent.getEventTime();
//                System.out.println("inspector1_blocked_time_e" + inspector1_blocked_time_e);
                if (inspector1_blocked_time_s > 0) {
                    inspector1_blocked_time = inspector1_blocked_time_e - inspector1_blocked_time_s;
                    inspector1_blocked_time_s = 0;
                }
            }

            if (Inspector2 && inspector2_blocked_time_s == 0){
                inspector2_blocked_time_s = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_s" + inspector2_blocked_time_s);

            }
            if (!Inspector2){
                inspector2_blocked_time_e = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_e" + inspector2_blocked_time_e);
                if (inspector2_blocked_time_s > 0) {
                    inspector2_blocked_time = inspector2_blocked_time_e - inspector2_blocked_time_s;
                    inspector2_blocked_time_s = 0;
                }
            }

            if (w1 && workstation1_idle_time_s == 0){
                workstation1_idle_time_s = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_s" + inspector2_blocked_time_s);

            }
            if (!w1){
                workstation1_idle_time_e = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_e" + inspector2_blocked_time_e);
                if (workstation1_idle_time_s > 0) {
                    workstation1_idle_time = workstation1_idle_time_e - workstation1_idle_time_s;
                    workstation1_idle_time_s = 0;
                }
            }

            if (w2 && workstation2_idle_time_s == 0){
                workstation2_idle_time_s = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_s" + inspector2_blocked_time_s);

            }
            if (!w2){
                workstation2_idle_time_e = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_e" + inspector2_blocked_time_e);
                if (workstation2_idle_time_s > 0) {
                    workstation2_idle_time = workstation2_idle_time_e - workstation2_idle_time_s;
                    workstation2_idle_time_s = 0;
                }
            }

            if (w3 && workstation3_idle_time_s == 0){
                workstation3_idle_time_s = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_s" + inspector2_blocked_time_s);

            }
            if (!w3){
                workstation3_idle_time_e = imminentEvent.getEventTime();
//                System.out.println("inspector2_blocked_time_e" + inspector2_blocked_time_e);
                if (workstation3_idle_time_s > 0) {
                    workstation3_idle_time = workstation3_idle_time_e - workstation3_idle_time_s;
                    workstation3_idle_time_s = 0;
                }
            }

            double size_c1w1 = buff_c1w1.size();
            double l1 = size_c1w1/2;
            c1w1.add(l1);

            double size_c1w2 = buff_c1w2.size();
            double l2 = size_c1w2/2;
            c1w2.add(l2);

            double size_c1w3 = buff_c1w3.size();
            double l3 = size_c1w3/2;
            c1w3.add(l3);

            double size_c2w2 = buff_c2w2.size();
            double l4 = size_c2w2/2;
            c2w2.add(l4);

            double size_c3w3 = buff_c3w3.size();
            double l5 = size_c3w3/2;
            c3w3.add(l5);


//            System.out.println(futureEvent.size());
//            System.out.println("---------------------");
//            System.out.println("buff_c1w1: " + buff_c1w1.size());
//            System.out.println("buff_c1w2: " + buff_c1w2.size());
//            System.out.println("buff_c1w3: " + buff_c1w3.size());
//            System.out.println("buff_c2w2: " + buff_c2w2.size());
//            System.out.println("buff_c3w3: " + buff_c3w3.size());

        }
        System.out.println("Total number of arrived components: " + allNumberComp);
        System.out.println("mean arrival rate (Component arrival rate per second): " + (allNumberComp/600));
        System.out.println("Mean number of components in system is: "+ totalDelay/600);
        System.out.println("Total delay time in second: "+ totalDelay);
        System.out.println("Average latency per component in second: "+ totalDelay/allNumberComp);

        double sum1 = 0;
        for (Double aDouble : c1w1) {
            if(aDouble > 2 || aDouble < 0){
                System.out.println("error");
            }
            sum1 = sum1 + aDouble;
        }
        double size1 = c1w1.size();
        double c1 = sum1/size1;
        System.out.println("average buffer occupancy for c1w1 is: " + c1);

        double sum2 = 0;
        for (Double aDouble : c1w2) {
            if(aDouble > 2 || aDouble < 0){
                System.out.println("error");
            }
            sum2 = sum2 + aDouble;
        }
        double size2 = c1w2.size();
        double c2 = sum2/size2;
        System.out.println("average buffer occupancy for c1w2 is: " + c2);

        double sum3 = 0;
        for (Double aDouble : c1w3) {
            if(aDouble > 2 || aDouble < 0){
                System.out.println("error");
            }
            sum3 = sum3 + aDouble;
        }
        double size3 = c1w3.size();
        double c3 = sum3/size3;
        System.out.println("average buffer occupancy for c1w3 is: " + c3);

        double sum4 = 0;
        for (Double aDouble : c2w2) {
            if(aDouble > 2 || aDouble < 0){
                System.out.println("error");
            }
            sum4 = sum4 + aDouble;
        }
        double size4 = c2w2.size();
        double c4 = sum4/size4;
        System.out.println("average buffer occupancy for c2w2 is: " + c4);

        double sum5 = 0;
        for (Double aDouble : c3w3) {
            if(aDouble > 2 || aDouble < 0){
                System.out.println("error");
            }
            sum5 = sum5 + aDouble;
        }
        double size5 = c3w3.size();
        double c5 = sum5/size5;
        System.out.println("average buffer occupancy for c3w3 is: " + c5);


//        System.out.println("prodect");
        System.out.println("product p1: " + p1);
        System.out.println("product p2: " + p2);
        System.out.println("product p3: " + p3);
        double a1 = p1;
        double a2 = p2;
        double a3 = p3;
//        3x facility throughput
        System.out.println("p1 throughput as products per second: "+  (a1/600));
        System.out.println("p2 throughput as products per second: "+ (a2/600));
        System.out.println("p3 throughput as products per second: "+ (a3/600));

        System.out.println("inspector1 blocked time is: "+ inspector1_blocked_time + "second");
        System.out.println("inspector2 blocked time is: "+ inspector2_blocked_time + "second");

        System.out.println("workstation1 idle time: " + workstation1_idle_time + "second");
        System.out.println("workstation2 idle time: " + workstation2_idle_time + "second");
        System.out.println("workstation3 idle time: " + workstation3_idle_time + "second");

        System.out.println("Workstation level--------------------------");
        System.out.println("all Component arrives at workstation 1: "+numberWS1);
        System.out.println("all Component arrives at workstation 2: "+numberWS2);
        System.out.println("all Component arrives at workstation 3: "+numberWS3);
        System.out.println("Average component arrival at workstation 1: "+ (numberWS1/600));
        System.out.println("Average component arrival at workstation 2: "+ (numberWS2/600));
        System.out.println("Average component arrival at workstation 3: "+ (numberWS3/600));

        System.out.println("Delay of components entering workstation 1: "+ ws1Delay);
        System.out.println("Delay of components entering workstation 2: "+ ws2Delay);
        System.out.println("Delay of components entering workstation 3: "+ ws3Delay);


        System.out.println("Average latency of components entering workstation 1: "+ ws1Delay/numberWS1);

        System.out.println("Average latency of components entering workstation 2: " + ws2Delay/numberWS2);

        System.out.println("Average latency of components entering workstation 3: " + ws2Delay/numberWS3);

        System.out.println("Mean number of components in workstation system is: "+ ws1Delay/600);
        System.out.println("Mean number of components in workstation system is: "+ ws2Delay/600);
        System.out.println("Mean number of components in workstation system is: "+ ws2Delay/600);

        System.out.println("Average latency per component in second: "+ totalDelay/allNumberComp);

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
        allNumberComp++;
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);
        double serviceTime = (-1/0.09654)*Math.log(1-remove);
//        System.out.println("ar1 serviceTime"+serviceTime);

        Component component = imminentEvent.getComponent();
        component.setCheck(true);

        Event e = new Event("LEI1",clock+serviceTime,component);
        futureEvent.add(e);
    }

    private static void ProcessAR2(Event imminentEvent) {
        allNumberComp++;
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
            Event e = new Event("LEI1",clock+5,component); // all buffer is full , delay the event
            futureEvent.add(e);

        }else {
            Inspector1 = false;
            int smallestBuffer = getSmallestBuffer();
//            System.out.println("the smallest buffer is : " + smallestBuffer);

            switch (smallestBuffer){
                case 1:
                    if (buff_c1w1.size() < 2){
                        buff_c1w1.add(component);
                        numberWS1++;
//                        arrivalc1w1++;
                        Event e = new Event("ARW1",clock,component);
                        e.getComponent().setWStime(clock);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false,clock,0);
                        Event e1= new Event("AR1",clock,component1);
                        futureEvent.add(e1);
                    }
                    break;
                case 2:
                    if (buff_c1w2.size() < 2){
                        buff_c1w2.add(component);
                        numberWS2++;
//                        arrivalc1w2++;
                        Event e = new Event("ARW2",clock,component);
                        e.getComponent().setWStime(clock);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false,clock,0);
                        Event e1= new Event("AR1",clock,component1);
                        futureEvent.add(e1);
                    }
                    break;
                case 3:
                    if (buff_c1w3.size() < 2){
                        buff_c1w3.add(component);
                        numberWS3++;
//                        arrivalc1w3++;
                        Event e = new Event("ARW3",clock,component);
                        e.getComponent().setWStime(clock);
                        futureEvent.add(e);

                        Component component1 = new Component("c1",false,clock,0);
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
                numberWS2++;
//                arrivalc2w2++;
                Event e = new Event("ARW2", clock, component);
                e.getComponent().setWStime(clock);
                futureEvent.add(e);

                Component component1 = new Component("c3",false,clock,0);
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
                    numberWS3++;
//                    arrivalc3w3++;
                    Event e = new Event("ARW3", clock, component);
                    e.getComponent().setWStime(clock);
                    futureEvent.add(e);

                    //c3 or c2 random
                    Component component1 = new Component("c2",false,clock,0);
                    Event e1 = new Event("AR2", clock, component1);
                    futureEvent.add(e1);
                }
            }
        }
     }

    private static void ProcessARW1(Event imminentEvent) {

        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);

        double serviceTime = (-1/0.217)*Math.log(1-remove);
//            System.out.println(serviceTime);

        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW1", clock+serviceTime,c);
        futureEvent.add(e);
    }

    private static void ProcessARW2(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);

        double serviceTime = (-1/0.09)*Math.log(1-remove);

        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW2", clock+serviceTime,c);
        futureEvent.add(e);
    }

    private static void ProcessARW3(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        double remove = (double) ran.remove(0);

        double serviceTime = (-1/0.115)*Math.log(1-remove);

        Component c = imminentEvent.getComponent();
        Event e = new Event("LEW3", clock+serviceTime,c);
        futureEvent.add(e);
    }


    private static void ProcessLEW1(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;
        if (buff_c1w1.size() > 0){
            w1 = false;
//            System.out.println("w1"+ w1);
            Component remove = buff_c1w1.remove(0);
            p1++;

            Component c = imminentEvent.getComponent();
            double startTime = c.getTime();
            double ws1Start = c.getWStime();
            totalDelay = totalDelay + eventTime - startTime;

            ws1Delay = ws1Delay + eventTime - ws1Start;


        }else{
            w1 = true;
//            System.out.println("w1: "+ w1);
            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW1", clock+3,c);
            futureEvent.add(e);
        }


    }

    private static void ProcessLEW2(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        if (buff_c2w2.size() > 0 && buff_c1w2.size() > 0){
            w2 = false;
//            System.out.println("w2"+ w2);

            Component remove = buff_c2w2.remove(0);
            Component remove1 = buff_c1w2.remove(0);
            p2++;

            double stime = remove.getTime();
            double stime1 = remove1.getTime();
            double ws2Start1 = remove.getWStime();
            double ws2Start2 = remove1.getWStime();
            totalDelay = totalDelay + (eventTime - stime) + (eventTime - stime1);
            ws2Delay = ws2Delay + (eventTime - ws2Start1) + (eventTime - ws2Start2);


        }else{
            w2 = true;

            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW2", clock+3,c);
            futureEvent.add(e);
        }



    }

    private static void ProcessLEW3(Event imminentEvent) {
        Double eventTime = imminentEvent.getEventTime();
        clock = eventTime;

        if (buff_c1w3.size() > 0 && buff_c3w3.size() > 0){
            w3 = false;
//            System.out.println("w3" + w3);
            Component remove = buff_c1w3.remove(0);
            Component remove1 = buff_c3w3.remove(0);
            p3++;

            double stime = remove.getTime();
            double stime1 = remove1.getTime();
            double ws3Start1 = remove.getWStime();
            double ws3Start2 = remove1.getWStime();
            totalDelay = totalDelay + (eventTime - stime) + (eventTime - stime1);

            ws3Delay = ws3Delay + (eventTime - ws3Start1) + (eventTime - ws3Start2);


        }else{
            w3 = true;

            Component c = imminentEvent.getComponent();
            Event e = new Event("LEW3", clock+3,c);
            futureEvent.add(e);
        }


    }



    private static void initialization(){
        clock = 0.0;
        Inspector1 = false;
        Inspector2 = false;
        w1 = false;
        w2 = false;
        w3 = false;
        futureEvent = new ArrayList<>();
        p1 = 0;
        p2 = 0;
        p3 = 0;

        numberWS1 = 0;
        numberWS2 = 0;
        numberWS3 = 0;

        ws1Delay = 0;
        ws2Delay = 0;
        ws3Delay = 0;

        arrivalc1w1 = 0;
        arrivalc1w2 = 0;
        arrivalc1w3 = 0;
        arrivalc2w2 = 0;
        arrivalc3w3 = 0;

        allNumberComp = 0;

        totalDelay = 0;

        inspector1_blocked_time = 0;
        inspector1_blocked_time_e = 0;
        inspector1_blocked_time_s = 0;

        inspector2_blocked_time = 0;
        inspector2_blocked_time_e = 0;
        inspector2_blocked_time_s = 0;

        workstation1_idle_time = 0;
        workstation1_idle_time_s = 0;
        workstation1_idle_time_e = 0;

        workstation2_idle_time = 0;
        workstation2_idle_time_s = 0;
        workstation2_idle_time_e = 0;

        workstation3_idle_time = 0;
        workstation3_idle_time_s = 0;
        workstation3_idle_time_e = 0;

        Component c11 = new Component("c1",false,clock,0);
        Event evt = new Event("AR1",clock,c11);
        futureEvent.add(evt);

        Component c22 = new Component("c2",false,clock,0);
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
