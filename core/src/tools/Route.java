package tools;

public class Route {
    public int indexAirport;
    public int indexCarrier;
    public int xStart;
    public int yStart;
    public int xEnd;
    public int yEnd;
    public int dangerLVL;


    public Route(int indexAirport, int indexCarrier, int xStart, int yStart, int xEnd, int yEnd, int dangerLVL) {
        this.indexAirport = indexAirport;
        this.indexCarrier = indexCarrier;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.dangerLVL = dangerLVL;
    }
}
