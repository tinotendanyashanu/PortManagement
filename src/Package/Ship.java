package Package;

import java.util.ArrayList;
import java.util.Comparator;

class Ship implements IShip {
    private int ID;
    private double fuelLevel;
    private Port currentPort;
    private int totalWeightCapacity;
    private int maxNumberOfAllContainers;
    private int maxNumberOfHeavyContainers;
    private int maxNumberOfRefrigeratedContainers;
    private int maxNumberOfLiquidContainers;
    private double fuelConsumptionPerKM;
    private ArrayList<Container> containers;

    public Ship(int ID, Port currentPort, int totalWeightCapacity, int maxNumberOfAllContainers,
                int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers,
                int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
        this.ID = ID;
        this.currentPort = currentPort;
        this.totalWeightCapacity = totalWeightCapacity;
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
        this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.fuelLevel = 0.0;
        this.containers = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public ArrayList<Container> getCurrentContainers() {
        ArrayList<Container> sortedContainers = new ArrayList<>(containers);
        sortedContainers.sort(Comparator.comparing(Container::getID));
        return sortedContainers;
    }

    @Override
    public boolean sailTo(Port destinationPort) {
        double distance = currentPort.getDistance(destinationPort);
        double fuelRequired = distance * fuelConsumptionPerKM;

        if (fuelRequired <= fuelLevel) {
            currentPort.outgoingShip(this);
            currentPort = destinationPort;
            fuelLevel -= fuelRequired;
            return true;
        } else {
            System.out.println("Insufficient fuel to make the journey.");
            return false;
        }
    }

    @Override
    public void reFuel(double newFuel) {
        if (newFuel > 0) {
            fuelLevel += newFuel;
            System.out.println("Fuel added: " + newFuel + " liters");
        } else {
            System.out.println("Invalid fuel amount. Fuel must be greater than 0.");
        }
    }

    @Override
    public boolean load(Container cont) {
        if (containers.size() < maxNumberOfAllContainers &&
                (cont instanceof BasicContainer || cont instanceof HeavyContainer)) {
            containers.add(cont);
            return true;
        } else if (containers.size() < maxNumberOfHeavyContainers) {
            if (cont instanceof RefrigeratedContainer || cont instanceof LiquidContainer) {
                containers.add(cont);
                return true;
            }
        }
        System.out.println("Loading container failed. Check ship capacity and container type.");
        return false;
    }

    @Override
    public boolean unLoad(Container cont) {
        if (containers.contains(cont)) {
            containers.remove(cont);
            return true;
        } else {
            System.out.println("Package.Container not found on the ship.");
            return false;
        }
    }
}