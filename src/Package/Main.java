package Package;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Port[] ports = new Port[10];
        int portCount = 0;
        Ship[] ships = new Ship[10];
        Container[] containers = new Container[20];

        int shipCount = 0;
        int containerCount = 0;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Create a container");
            System.out.println("2. Create a ship");
            System.out.println("3. Create a port");
            System.out.println("4. Load a container to a ship");
            System.out.println("5. Unload a container from a ship");
            System.out.println("6. Sail ship to another port");
            System.out.println("7. Refuel Package.Ship");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter container ID: ");
                    int containerID = scanner.nextInt();
                    System.out.print("Enter container weight: ");
                    int containerWeight = scanner.nextInt();
                    System.out.print("Enter 'R' for Refrigerated, 'L' for Liquid, or any other key for Basic: ");
                    String containerType = scanner.next();

                    if (containerType.equals("R")) {
                        containers[containerCount] = new RefrigeratedContainer(containerID, containerWeight);
                    } else if (containerType.equals("L")) {
                        containers[containerCount] = new LiquidContainer(containerID, containerWeight);
                    } else {
                        containers[containerCount] = new BasicContainer(containerID, containerWeight);
                    }
                    containerCount++;
                    break;

                case 2:
                    // Create a ship (you can customize ship creation)
                    System.out.print("Enter ship ID: ");
                    int shipID = scanner.nextInt();
                    System.out.print("Enter ship fuel capacity: ");
                    double fuelCapacity = scanner.nextDouble();
                    System.out.print("Enter ship fuel consumption per km: ");
                    double fuelConsumptionPerKM = scanner.nextDouble();
                    System.out.print("Enter ship total weight capacity: ");
                    int totalWeightCapacity = scanner.nextInt();
                    System.out.print("Enter max number of all containers on the ship: ");
                    int maxNumAllContainers = scanner.nextInt();
                    System.out.print("Enter max number of heavy containers on the ship: ");
                    int maxNumHeavyContainers = scanner.nextInt();
                    System.out.print("Enter max number of refrigerated containers on the ship: ");
                    int maxNumRefrigeratedContainers = scanner.nextInt();
                    System.out.print("Enter max number of liquid containers on the ship: ");
                    int maxNumLiquidContainers = scanner.nextInt();

                    Port initialPort = selectPort(ports, portCount, scanner);
                    ships[shipCount] = new Ship(
                            shipID, initialPort, totalWeightCapacity, maxNumAllContainers,
                            maxNumHeavyContainers, maxNumRefrigeratedContainers,
                            maxNumLiquidContainers, fuelConsumptionPerKM
                    );
                    shipCount++;
                    break;

                case 3:
                    // Create a port
                    System.out.print("Enter port ID: ");
                    int portID = scanner.nextInt();
                    System.out.print("Enter latitude: ");
                    double latitude = scanner.nextDouble();
                    System.out.print("Enter longitude: ");
                    double longitude = scanner.nextDouble();

                    ports[portCount] = new Port(portID, latitude, longitude);
                    portCount++;
                    break;

                case 4:
                    // Load a container to a ship
                    loadContainerToShip(ships, shipCount, containers, containerCount, ports, portCount, scanner);
                    break;

                case 5:
                    // Unload a container from a ship
                    unloadContainerFromShip(ships, shipCount, containers, containerCount, ports, portCount, scanner);
                    break;

                case 6:
                    // Sail ship to another port
                    sailShipToPort(ships, shipCount, ports, portCount, scanner);
                    break;

                case 7:
                    // Refuel a ship
                    refuelShip(ships, shipCount, scanner);
                    break;

                case 8:
                    // Exit the program
                    System.out.println("Exiting the Package.Port Management System.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void loadContainerToShip(Ship[] ships, int shipCount, Container[] containers, int containerCount, Port[] ports, int portCount, Scanner scanner) {
        Port loadingPort = selectPort(ports, portCount, scanner);

        System.out.print("Enter ship ID: ");
        int shipID = scanner.nextInt();
        System.out.print("Enter container ID: ");
        int containerID = scanner.nextInt();

        Ship targetShip = null;
        for (int i = 0; i < shipCount; i++) {
            if (ships[i].getID() == shipID) {
                targetShip = ships[i];
                break;
            }
        }

        if (targetShip == null) {
            System.out.println("Package.Ship not found.");
            return;
        }

        Container selectedContainer = null;
        for (int i = 0; i < containerCount; i++) {
            if (containers[i].getID() == containerID) {
                selectedContainer = containers[i];
                break;
            }
        }

        if (selectedContainer == null) {
            System.out.println("Package.Container not found.");
            return;
        }

        if (loadingPort != targetShip.getCurrentPort()) {
            System.out.println("Loading port does not match the current port of the ship.");
        } else {
            boolean loaded = targetShip.load(selectedContainer);
            if (loaded) {
                System.out.println("Package.Container loaded successfully.");
            } else {
                System.out.println("Loading container failed.");
            }
        }
    }



    private static void unloadContainerFromShip(Ship[] ships, int shipCount, Container[] containers, int containerCount, Port[] ports, int portCount, Scanner scanner) {
        Port unloadingPort = selectPort(ports, portCount, scanner);

        System.out.print("Enter ship ID: ");
        int shipID = scanner.nextInt();
        System.out.print("Enter container ID: ");
        int containerID = scanner.nextInt();

        Ship targetShip = null;
        for (int i = 0; i < shipCount; i++) {
            if (ships[i].getID() == shipID) {
                targetShip = ships[i];
                break;
            }
        }

        if (targetShip == null) {
            System.out.println("Package.Ship not found.");
            return;
        }

        Container selectedContainer = null;
        for (int i = 0; i < containerCount; i++) {
            if (containers[i].getID() == containerID) {
                selectedContainer = containers[i];
                break;
            }
        }

        if (selectedContainer == null) {
            System.out.println("Package.Container not found.");
            return;
        }

        if (unloadingPort != targetShip.getCurrentPort()) {
            System.out.println("Unloading port does not match the current port of the ship.");
        } else {
            boolean unloaded = targetShip.unLoad(selectedContainer);
            if (unloaded) {
                System.out.println("Package.Container unloaded successfully.");
            } else {
                System.out.println("Unloading container failed.");
            }
        }
    }


    private static void sailShipToPort(Ship[] ships, int shipCount, Port[] ports, int portCount, Scanner scanner) {
        System.out.print("Enter ship ID: ");
        int shipID = scanner.nextInt();
        System.out.print("Enter destination port ID: ");
        int destinationPortID = scanner.nextInt();

        Ship targetShip = null;
        for (int i = 0; i < shipCount; i++) {
            if (ships[i].getID() == shipID) {
                targetShip = ships[i];
                break;
            }
        }

        if (targetShip == null) {
            System.out.println("Package.Ship not found.");
            return;
        }

        Port destinationPort = null;
        for (int i = 0; i < portCount; i++) {
            if (ports[i].getID() == destinationPortID) {
                destinationPort = ports[i];
                break;
            }
        }

        if (destinationPort == null) {
            System.out.println("Destination port not found.");
            return;
        }

        boolean sailed = targetShip.sailTo(destinationPort);
        if (sailed) {
            System.out.println("Package.Ship sailed successfully to Package.Port " + destinationPort.getID());
        } else {
            System.out.println("Package.Ship could not sail to the destination port.");
        }
    }

    private static void refuelShip(Ship[] ships, int shipCount, Scanner scanner) {
        System.out.print("Enter ship ID: ");
        int shipID = scanner.nextInt();
        System.out.print("Enter amount of fuel to add: ");
        double fuelToAdd = scanner.nextDouble();

        Ship targetShip = null;
        for (int i = 0; i < shipCount; i++) {
            if (ships[i].getID() == shipID) {
                targetShip = ships[i];
                break;
            }
        }

        if (targetShip == null) {
            System.out.println("Package.Ship not found.");
            return;
        }

        targetShip.reFuel(fuelToAdd);
        System.out.println("Package.Ship refueled successfully. Current fuel level: " + targetShip.getFuelLevel());
    }



    private static Port selectPort(Port[] ports, int portCount, Scanner scanner) {
        if (portCount == 0) {
            System.out.println("No ports available.");
            return null;
        }

        int selectedPortID;
        Port selectedPort = null;

        // Display available ports
        System.out.println("Available Ports:");
        for (int i = 0; i < portCount; i++) {
            System.out.println(i + ": " + ports[i].getID());
        }

        // Get user input for port selection
        do {
            System.out.print("Select the port (0 to " + (portCount - 1) + "): ");
            selectedPortID = scanner.nextInt();

            if (selectedPortID >= 0 && selectedPortID < portCount) {
                selectedPort = ports[selectedPortID];
            } else {
                System.out.println("Invalid port selection. Please enter a valid port ID.");
            }
        } while (selectedPort == null);

        return selectedPort;
    }






}
