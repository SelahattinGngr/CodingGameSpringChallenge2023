import java.util.*;
import java.io.*;
import java.math.*;


class Player {

    int index;
    int type;// 0 for empty, 1 for eggs, 2 for crystal
    int initialResources;// the initial amount of eggs/crystals on this cell
    int neigh0;// the index of the neighbouring cell for each direction
    int neigh1;
    int neigh2;
    int neigh3;
    int neigh4;
    int neigh5;

    int resources;// the current amount of eggs/crystals on this cell
    int myAnts; // the amount of your ants on this cell
    int oppAnts; // the amount of opponent ants on this cell

    public Player(int index, int type, int initialResources, int neigh0, int neigh1, int neigh2, int neigh3, int neigh4, int neigh5, int resources, int myAnts, int oppAnts) {
        this.index = index;
        this.type = type;
        this.initialResources = initialResources;
        this.neigh0 = neigh0;
        this.neigh1 = neigh1;
        this.neigh2 = neigh2;
        this.neigh3 = neigh3;
        this.neigh4 = neigh4;
        this.neigh5 = neigh5;
        this.resources = resources;
        this.myAnts = myAnts;
        this.oppAnts = oppAnts;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getInitialResources() {
        return initialResources;
    }

    public void setInitialResources(int initialResources) {
        this.initialResources = initialResources;
    }

    public int getNeigh0() {
        return neigh0;
    }

    public void setNeigh0(int neigh0) {
        this.neigh0 = neigh0;
    }

    public int getNeigh1() {
        return neigh1;
    }

    public void setNeigh1(int neigh1) {
        this.neigh1 = neigh1;
    }

    public int getNeigh2() {
        return neigh2;
    }

    public void setNeigh2(int neigh2) {
        this.neigh2 = neigh2;
    }

    public int getNeigh3() {
        return neigh3;
    }

    public void setNeigh3(int neigh3) {
        this.neigh3 = neigh3;
    }

    public int getNeigh4() {
        return neigh4;
    }

    public void setNeigh4(int neigh4) {
        this.neigh4 = neigh4;
    }

    public int getNeigh5() {
        return neigh5;
    }

    public void setNeigh5(int neigh5) {
        this.neigh5 = neigh5;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getMyAnts() {
        return myAnts;
    }

    public void setMyAnts(int myAnts) {
        this.myAnts = myAnts;
    }

    public int getOppAnts() {
        return oppAnts;
    }

    public void setOppAnts(int oppAnts) {
        this.oppAnts = oppAnts;
    }

    @Override
    public String toString() {
        return "Hex{" +
                "index=" + index +
                ", type=" + type +
                ", initialResources=" + initialResources +
                ", neigh0=" + neigh0 +
                ", neigh1=" + neigh1 +
                ", neigh2=" + neigh2 +
                ", neigh3=" + neigh3 +
                ", neigh4=" + neigh4 +
                ", neigh5=" + neigh5 +
                ", resources=" + resources +
                ", myAnts=" + myAnts +
                ", oppAnts=" + oppAnts +
                '}';
    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        List<Player> hexList = new ArrayList<>();
        int myBaseIndex = 0;
        int numberOfCells = in.nextInt(); // amount of hexagonal cells in this map
        for (int i = 0; i < numberOfCells; i++) {
            int type = in.nextInt(); // 0 for empty, 1 for eggs, 2 for crystal
            int initialResources = in.nextInt(); // the initial amount of eggs/crystals on this cell
            int neigh0 = in.nextInt(); // the index of the neighbouring cell for each direction
            int neigh1 = in.nextInt();
            int neigh2 = in.nextInt();
            int neigh3 = in.nextInt();
            int neigh4 = in.nextInt();
            int neigh5 = in.nextInt();
            Player hex = new Player(i, type, initialResources, neigh0, neigh1, neigh2, neigh3, neigh4, neigh5, 0, 0, 0);
            hexList.add(hex);
        }
        int numberOfBases = in.nextInt();
        for (int i = 0; i < numberOfBases; i++) {
            myBaseIndex = in.nextInt();
        }
        for (int i = 0; i < numberOfBases; i++) {
            int oppBaseIndex = in.nextInt();
        }

        // game loop
        while (true) {
            for (int i = 0; i < numberOfCells; i++) {
                int resources = in.nextInt(); // the current amount of eggs/crystals on this cell
                int myAnts = in.nextInt(); // the amount of your ants on this cell
                int oppAnts = in.nextInt(); // the amount of opponent ants on this cell

                //check if hex is in list
                for (Player hex : hexList) {
                    if (hex.getIndex() == i) {
                        hex.setResources(resources);
                        hex.setMyAnts(myAnts);
                        hex.setOppAnts(oppAnts);
                    }
                }

            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            //if resources is zero remove from list
            for (int i = 0; i < hexList.size(); i++) {
                if (hexList.get(i).getResources() == 0) {
                    hexList.remove(i);
                }
            }

            int strength = 10 / hexList.size();


            String output = "";
            for (Player hex : hexList) {
                if (hex.getResources() > 0) {
                    //put LINE on hex commands and separate with ;
                    output += "LINE " + (myBaseIndex) + " " + (hex.getIndex()) + " " + (strength) + ";";
                }
            }

            if(hexList.isEmpty()){
                System.out.println("WAIT");
            }else{
                System.out.println(output);
            }

            // WAIT | LINE <sourceIdx> <targetIdx> <strength> | BEACON <cellIdx> <strength> | MESSAGE <text>

        }
    }
}