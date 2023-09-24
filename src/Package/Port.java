package Package;

import java.util.ArrayList;

public class Port implements IPort {
    private int ID;
    private double latitude;
    private double longitude;
    private ArrayList<Container> containers;
    private ArrayList<Ship> history;
    private ArrayList<Ship> current;

    public Port(int ID, double latitude, double longitude) {
        this.ID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.containers = new ArrayList<>();
        this.history = new ArrayList<>();
        this.current = new ArrayList<>();
    }

    // Getters and setters for private variables (if needed)
    public int getID() {
        return ID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<Ship> getHistory() {
        return history;
    }

    public ArrayList<Ship> getCurrent() {
        return current;
    }

    @Override
    public void incomingShip(Ship s) {
        current.add(s);
    }

    @Override
    public void outgoingShip(Ship s) {
        current.remove(s);
        history.add(s);
    }

    public double getDistance(Port other) {
        // Calculate the distance between two ports based on their latitude and longitude
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(other.latitude);
        double lon2 = Math.toRadians(other.longitude);

        // Haversine formula for calculating distance
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radius = 6371; // Radius of the Earth in kilometers
        return radius * c;
    }
}
