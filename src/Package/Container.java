package Package;

public abstract class Container {
    private int ID;
    private int weight;

    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    // Abstract method to be implemented by subclasses
    public abstract double consumption();

    public boolean equals(Container other) {
        // Check if the type, ID, and weight are the same
        return this.getClass() == other.getClass() &&
                this.ID == other.getID() &&
                this.weight == other.getWeight();
    }
}
