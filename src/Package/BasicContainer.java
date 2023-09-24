package Package;

public class BasicContainer extends Container {
    public BasicContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for Package.BasicContainer: 2.50 per unit of weight
        return 2.50 * getWeight();
    }
}
