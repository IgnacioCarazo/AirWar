package tools;

public class Route {
    public int weight;
    public int dmgweight;
    public int index;//start
    public int indexAssigned;//end
    public int xStart;
    public int yStart;
    public int xEnd;
    public int yEnd;
    public int dangerLVL;
    public String entity;
    public String entityAssigned;
    public String identifier;


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
        this.dmgweight = 0;
        if (entityAssigned.equals("carrier")){
            weight += 5;
        }
        int width = Math.abs(xStart-xEnd);
        int height = Math.abs(yStart-yEnd);
        int hipotenuse = (int) Math.sqrt((int) Math.pow(width,2) + (int) Math.pow(height,2));
        int firstDigit = Integer.parseInt(Integer.toString(hipotenuse).substring(0, 1));
        weight += firstDigit;
    }
}
