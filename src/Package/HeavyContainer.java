package Package;

public class HeavyContainer extends Container {
    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for Package.HeavyContainer: 3.00 per unit of weight
        return 3.00 * getWeight();
    }
}

