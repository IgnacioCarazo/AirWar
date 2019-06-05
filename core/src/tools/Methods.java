package tools;

import com.airwar.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import entities.Airport;
import entities.Carrier;
import screens.GameScreen1;

import java.util.ArrayList;
import java.util.Random;

public class Methods {


    public static void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, int x1, int y1, int x2, int y2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for(int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        shapeRenderer.end();
    }

    public static int randomAirportIndex() {
        int number = (int) (Math.random() * (3));
        return (int) (Math.random() * (3));
    }
    public static int randomRoute() {
        return (int) (Math.random() * (4));
    }

    public static int randomCarrierIndex() {
        return (int) (Math.random() * (2));
    }

    public static void drawTerrain(int xMin,int xMax, int yMin, int yMax, Texture texture, Main game){
        int temp = xMin;
        for (int i = yMin; i < yMax;) {
            while (xMin < xMax) {
                game.batch.draw(texture, xMin, i);
                xMin+=texture.getWidth();
            }
            xMin = temp;
            i += texture.getHeight();
        }
    }

    public static Route chooseRandomRoute(ArrayList<Route> routes){
        Random random = new Random();
        return routes.get(random.nextInt(routes.size()));
    }

    public static ArrayList<Route> assignCarrierRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, Carrier carrier){

        int unavailable = carrier.getIndex();
        int routesToAssign = 2 + (int) (Math.random() * ((4 - 2) + 1));

        ArrayList<Route> routes = new ArrayList<Route>();
        ArrayList<String> ports = new ArrayList<String>();


        ports.add("carriers");
        ports.add("airports");

        for (int i = 0; i < routesToAssign; i++) {
            // Lists of airports/carriers occupied so there cannot be 2 routes exactly the same
            ArrayList<Integer> airportsOccupied = new ArrayList<Integer>();
            ArrayList<Integer> carriersOccupied = new ArrayList<Integer>();

            int randomAirportIndex = randomAirportIndex();
            int randomCarrierIndex = randomCarrierIndex();

            String port = ports.get(randomCarrierIndex);

            if (port.equals("carriers")){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                if (randomCarrierIndex != unavailable){
                    if (!carriersOccupied.contains(carrierAssigned.getIndex())){
                        Route route = new Route("carrier","carrier",carrier.getIndex(),carrierAssigned.getIndex(),carrier.getX(),carrier.getY(),carrierAssigned.getX(),carrierAssigned.getY(),0);
                        StringBuilder ID = new StringBuilder().append(carrier.identifier).append(carrierAssigned.identifier);
                        route.identifier = String.valueOf(ID);
                        routes.add(route);
                        GameScreen1.existingRoutes.add(route);
                        GameScreen1.routeIdentifier += 1;
                        carriersOccupied.add(carrierAssigned.getIndex());
                    }
                }
            } else {
                Airport airportAssigned = airports.get(randomAirportIndex);
                if (!airportsOccupied.contains(airportAssigned.getIndex())){
                    Route route = new Route("carrier","airport",carrier.getIndex(),airportAssigned.getIndex(), carrier.getX(),carrier.getY(),airportAssigned.getX(),airportAssigned.getY(),0);
                    StringBuilder ID = new StringBuilder().append(carrier.identifier).append(airportAssigned.identifier);
                    route.identifier = String.valueOf(ID);
                    routes.add(route);
                    GameScreen1.existingRoutes.add(route);
                    GameScreen1.routeIdentifier += 1;
                    airportsOccupied.add(airportAssigned.getIndex());
                }


            }
        }
        return routes;
    }

    public static char assignRouteIdentifier(int index)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return AlphaNumericString.charAt(index);
    }
}
