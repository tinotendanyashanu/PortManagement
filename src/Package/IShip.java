package Package;

interface IShip {
    boolean sailTo(Port p);
    void reFuel(double newFuel);
    boolean load(Container cont);
    boolean unLoad(Container cont);
}