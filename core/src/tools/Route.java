package tools;

public class Route {
    public int index;
    public int indexAssigned;
    public int xStart;
    public int yStart;
    public int xEnd;
    public int yEnd;
    public int dangerLVL;
    public String entity;
    public String entityAssigned;
    public char identifier;


    public Route(String entity, String entityAssigned, int index, int indexAssigned, int xStart, int yStart, int xEnd, int yEnd, int dangerLVL) {
        this.entityAssigned = entityAssigned;
        this.indexAssigned = indexAssigned;
        this.index = index;
        this.entity = entity;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.dangerLVL = dangerLVL;
    }
}
