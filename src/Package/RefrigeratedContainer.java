package Package;

public class RefrigeratedContainer extends HeavyContainer {
    public RefrigeratedContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for Package.RefrigeratedContainer: 5.00 per unit of weight
        return 5.00 * getWeight();
    }
}
