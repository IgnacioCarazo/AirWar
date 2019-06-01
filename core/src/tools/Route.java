package tools;

public class Route {
    private int indexAirport;
    private int indexCarrier;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int dangerLVL;


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
