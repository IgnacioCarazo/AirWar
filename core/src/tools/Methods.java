package tools;

import com.airwar.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import entities.Airport;
import entities.Carrier;
import grafomatriz.MatrizAdyacencia;
import grafomatriz.Recorrido;
import screens.GameScreen1;

import java.util.ArrayList;
import java.util.Random;

public class Methods {

    private static boolean verif;

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
        return (int) (Math.random() * (3));
    }

    public static int randomCarrierIndex() {
        int number = (int) (Math.random() * (2));
        return number;
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

    public static ArrayList<Route> chooseRandomRouteAirport(ArrayList<ArrayList<Route>> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    public static ArrayList<Route> chooseRandomRouteCarrier(ArrayList<ArrayList<Route>> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static void changeRouteDanger(ArrayList<Route> routes, ArrayList<Carrier> carriers, ArrayList<Airport> airports, String identifier){
        for (Route route : routes){
            if (route.identifier.equals(identifier)) {
                route.dangerLVL += 1;
            }
        }
//        for (Carrier carrier : carriers){
//            for (Route route : carrier.getRoutes()){
//                if (route.identifier.equals(identifier)) {
//                    route.dangerLVL += 1;
//                }
//            }
//        }
//        for (Airport airport : airports){
//            for (Route route : airport.getRoutes()){
//                if (route.identifier.equals(identifier)) {
//                    route.dangerLVL += 1;
//                }
//            }
//        }
    }

    public static void changeRouteDangerLVL(ArrayList<Route> routes, ArrayList<Carrier> carriers, ArrayList<Airport> airports) {
        for (Route route: routes){
            if (route.dangerLVL < 0){
                route.dangerLVL -= 1;
                route.dmgweight = 0;
            }


        }
        for (Carrier carrier : carriers){
            for (Route route : carrier.getRoutes()){
                if (route.dangerLVL > 0){
                    route.dangerLVL -= 1;
                    route.dmgweight = 0;
                }
            }
        }
        for (Airport airport : airports){
            for (Route route : airport.getRoutes()){
                if (route.dangerLVL > 0){
                    route.dangerLVL -= 1;
                    route.dmgweight = 0;

                }
            }
        }
    }

    private static void createCarrierRouteA1(Carrier carrier, Carrier carrierAssigned, ArrayList<Route> routes){
        Route route = new Route("carrier","carrier",carrier.getIndex(),carrierAssigned.getIndex(),carrier.getX(),carrier.getY(),carrierAssigned.getX(),carrierAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(carrier.identifier).append("-").append(carrierAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(GameScreen1.existingRoutes,route)) {
            routes.add(route);
            GameScreen1.existingRoutes.add(route);
            GameScreen1.routeIdentifier += 1;
        }
    }
    private static void createAirportRouteA1(Carrier carrier, Airport airportAssigned, ArrayList<Route> routes){
        Route route = new Route("carrier","airport",carrier.getIndex(),airportAssigned.getIndex(), carrier.getX(),carrier.getY(),airportAssigned.getX(),airportAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(carrier.identifier).append("-").append(airportAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(GameScreen1.existingRoutes,route)){
            routes.add(route);
            GameScreen1.existingRoutes.add(route);
            GameScreen1.routeIdentifier += 1;
        }
    }

    private static void createCarrierRouteB1(Airport airport, Carrier carrierAssigned, ArrayList<Route> routes){
        Route route = new Route("airport","carrier",airport.getIndex(),carrierAssigned.getIndex(),airport.getX(),airport.getY(),carrierAssigned.getX(),carrierAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(airport.identifier).append("-").append(carrierAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(GameScreen1.existingRoutes,route)) {
            routes.add(route);
            GameScreen1.existingRoutes.add(route);
            GameScreen1.routeIdentifier += 1;
        }
    }
    private static void createAirportRouteB1(Airport airport, Airport airportAssigned, ArrayList<Route> routes){
        Route route = new Route("airport","airport",airport.getIndex(),airportAssigned.getIndex(), airport.getX(),airport.getY(),airportAssigned.getX(),airportAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(airport.identifier).append("-").append(airportAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(GameScreen1.existingRoutes,route)){
            routes.add(route);
            GameScreen1.existingRoutes.add(route);
            GameScreen1.routeIdentifier += 1;
        }
    }

    public static ArrayList<Route> assignCarrierRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, Carrier carrier, Main game){

        int unavailable = carrier.getIndex();
        int routesToAssign = 3 + (int) (Math.random() * ((4 - 3) + 1));
//        int routesToAssign = 1;
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
                if (randomCarrierIndex != unavailable) {
                    createCarrierRouteA1(carrier, carrierAssigned, routes);
                }
            } else {
                Airport airportAssigned = airports.get(randomAirportIndex);
                createAirportRouteA1(carrier,airportAssigned,routes);
                }
            if (routes.size() != routesToAssign -1){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                if (randomCarrierIndex != unavailable) {
                    createCarrierRouteA1(carrier, carrierAssigned, routes);
                }
                Airport airportAssigned = airports.get(randomAirportIndex);
                createAirportRouteA1(carrier,airportAssigned,routes);
            }
            }
        return routes;
    }

    public static ArrayList<Route> assignAirportRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, Airport airport, Main game){

        int unavailable = airport.getIndex();
        int routesToAssign = 3 + (int) (Math.random() * ((4 - 3) + 1));
//        int routesToAssign = 1;
        ArrayList<Route> routes = new ArrayList<Route>();
        ArrayList<String> ports = new ArrayList<String>();


        ports.add("carriers");
        ports.add("airports");

        for (int i = 0; i < routesToAssign; i++) {

            int randomAirportIndex = randomAirportIndex();
            int randomCarrierIndex = randomCarrierIndex();

            String port = ports.get(randomCarrierIndex);

            if (port.equals("carriers")){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                createCarrierRouteB1(airport, carrierAssigned, routes);
            } else {
                Airport airportAssigned = airports.get(randomAirportIndex);
                if (randomAirportIndex != unavailable){
                    createAirportRouteB1(airport,airportAssigned,routes);
                }
            }
            if (routes.size() != routesToAssign -1){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                createCarrierRouteB1(airport, carrierAssigned, routes);
                Airport airportAssigned = airports.get(randomAirportIndex);
                if (randomAirportIndex != unavailable){
                    createAirportRouteB1(airport,airportAssigned,routes);
                }
            }
        }
        return routes;
    }

    private static boolean canCreate(ArrayList<Route> routes, Route routeToCheck){
        if (!verif){
            verif = true;
            return true;
        } else {
                for (Route route : routes) {
                    if (routeToCheck.identifier.equals(route.identifier)) {
                        return false;
                    }
                }
        }
        return true;
    }



    public static char assignRouteIdentifier(int index)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return AlphaNumericString.charAt(index);
    }

    public static void addroute (MatrizAdyacencia grafo, ArrayList<Route> routes){
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            try{
                grafo.agregarArco(route.index,route.indexAssigned, route.weight);
            }catch (Exception e){
                System.out.println("error al agregar ruta al grafo:");
                System.out.println("error de arco de carrier");
                System.out.println("error en core.src.screens.GameScreen1(ln 206)");
            }
        }
    }

    public static void addRouteArrayList(MatrizAdyacencia grafo, ArrayList<Carrier> carriers,ArrayList<Airport> airports, ArrayList<Route> existingRoutes, Recorrido actualPaths){
        try {
            actualPaths =  new Recorrido(grafo);
            int[][] matriz = actualPaths.matrizWarshall;

            for(Carrier carrier: carriers){
//                System.out.println("destinos de " +carrier.intidentifier+":");
                for (int j = 0; j < matriz.length ; j++) {
                    ArrayList<Route> route = new ArrayList<Route>();
                    if(matriz[j][carrier.getIndex()] == 1 ){
                        if (j != carrier.getIndex()){
                            ArrayList<Integer> temp =  actualPaths.obtenerCaminoMasCorto(carrier.getIndex(),j);
                            while(temp.size()>=2){
                                for (Route existingRoute:existingRoutes){
                                    if (existingRoute.index==temp.get(0)&&existingRoute.indexAssigned==temp.get(1)){
                                        route.add(existingRoute);
                                    }
                                }
                                temp.remove(0);
                            }
                        }
                        if (route.size() >= 1) {

                            for (Route route1 : route) {
                            }
                            carrier.destiny.add(route);
                        }
                    }

                }
            }
            for(Airport airport: airports){
                for (int j = 0; j < matriz.length ; j++) {
                    ArrayList<Route> route = new ArrayList<Route>();
                    if(matriz[j][airport.getIndex()] == 1 ){
                        if (j != airport.getIndex()){
                            ArrayList<Integer> temp =  actualPaths.obtenerCaminoMasCorto(airport.getIndex(),j);
                            while(temp.size()>=2){
                                for (Route existingRoute:existingRoutes){
                                    if (existingRoute.index==temp.get(0)&&existingRoute.indexAssigned==temp.get(1)){
                                        route.add(existingRoute);
                                    }
                                }
                                temp.remove(0);
                            }
                        }
                        if (route.size() >= 1) {

                            for (Route route1 : route) {
                            }
                            airport.destiny.add(route);
                        }
                    }
                }

            }
        }catch(Exception a){
            System.out.println("Error al generar los caminos mas cortos 1st Time");
        }

        }

        public static String printRoutes(ArrayList<Route> routes){
        String string = "";
        for (Route route : routes){
            string += route.identifier + " ";
        }
        return string;
        }
}
