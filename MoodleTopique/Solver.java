package MoodleTopique;

import java.util.*;
import java.util.stream.*;

class Pass {
    private String name;
    private int age;

    public Pass(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public boolean isPriority() {
        return age >= 65;
    }

    public String getName() {
        return this.name;
    }
    public String toString(){
        return this.name + ":" + this.age;
    }

}

class Topic {
    private List<Pass> prioritySeats;
    private List<Pass> normalSeats;

    public Topic(int capacity, int qtdPriority) {
        prioritySeats = new ArrayList<Pass>(Collections.nCopies(qtdPriority, null));
        normalSeats = new ArrayList<Pass>(Collections.nCopies(capacity - qtdPriority, null));
    }

    private static int findFirstFreePos(List<Pass> list) {
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null) return i;
        }
        return -1;
    }

    private static int findByName(String name, List<Pass> list) {
        for(int i = 0; i < list.size();i++){
            if(list.get(i) != null){
                if(list.get(i).getName().equals(name)){
                    return i;
                }

            }
        }
        return -1;
    }

    private static boolean insertOnList(Pass pass, List<Pass> list) {
        int index = findFirstFreePos(list);
        if(index == -1) return false;
        list.set(index, pass);
        return true;
    }

    private static boolean removeFromList(String name, List<Pass> list) {
        int index = findByName(name, list);
        if(index == -1) return false;
        list.set(index, null);
        return true;
    }


    public boolean insert(Pass pass) {
        if ( findByName( pass.getName(), normalSeats ) != -1 ) {
            System.out.println("fail: " + pass.getName() + " ja esta na topic");
            return false;
        }
        if ( findByName( pass.getName(), prioritySeats ) != -1 ) {
            System.out.println("fail: " + pass.getName() + " ja esta na topic");
            return false;
        }

        if ( pass.isPriority() ) {
            if ( insertOnList( pass, prioritySeats ) ) {
                return true;
            }
            if ( insertOnList( pass, normalSeats ) ) {
                return true;
            }
        } else {
            if ( insertOnList( pass, normalSeats ) ) {
                return true;
            }
            if ( insertOnList( pass, prioritySeats ) ) {
                return true;
            }
        }
        System.out.println("fail: topic lotada");
        return false;
    }

    public boolean remove(String name) {
        if ( removeFromList( name, normalSeats ) ) {
            return true;
        }
        if ( removeFromList( name, prioritySeats ) ) {
            return true;
        }
        System.out.println("fail: " + name + " nao esta na topic");
        return false;
    }
    public String toString() {
        return "[" + Stream.concat(
                        this.prioritySeats.stream().map(p -> ("@" + ((p == null)?(""):("" + p)))),
                        this.normalSeats.stream().map(p -> ("=" + ((p == null)?(""):("" + p)))))
                .collect(Collectors.joining(" ")) + "]";
    }
}

class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Topic topic = new Topic(0, 0);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(line.equals("end")) {
                break;
            } else if(ui[0].equals("init")) { //capacity qtdPriority
                topic = new Topic(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            } else if(ui[0].equals("show")) {
                System.out.println(topic);
            } else if(ui[0].equals("in")) {
                topic.insert(new Pass(ui[1], Integer.parseInt(ui[2])));
            } else if(ui[0].equals("out")) {//value value
                topic.remove(ui[1]);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
