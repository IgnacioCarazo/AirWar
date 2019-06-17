package tools;

import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import entities.*;
import grafomatriz.MatrizAdyacencia;
import grafomatriz.Recorrido;
import java.util.ArrayList;
import java.util.Random;

import static screens.GameScreen2.existingRoutes;

public class Methods {

    private static boolean verif;
    public static void assignRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, ArrayList<Route> existingRoutes, int routeIdentifier, MatrizAdyacencia grafo){
        for (Carrier carrier: carriers){
            carrier.setRoutes(assignCarrierRoutes(carriers,airports,carrier, existingRoutes, routeIdentifier));
            ArrayList<Route> routes =  carrier.getRoutes();
            addroute(grafo,routes); //Adds every single DIRECT carrie's route
        }
        for (Airport airport: airports){
            airport.setRoutes(assignAirportRoutes(carriers,airports,airport, existingRoutes, routeIdentifier));
            ArrayList<Route> routes =  airport.getRoutes();
            addroute(grafo,routes); //Adds every single DIRECT airport's route
        }
    }

    public static void addRouteArrayList(MatrizAdyacencia grafo, ArrayList<Carrier> carriers,ArrayList<Airport> airports, ArrayList<Route> existingRoutes, Recorrido actualPaths){
        try {
            actualPaths =  new Recorrido(grafo);
            int[][] matriz = actualPaths.matrizWarshall;
            for(Carrier carrier: carriers){
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
                            carrier.destiny.clear();
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
                            airport.destiny.clear();
                            airport.destiny.add(route);
                        }
                    }
                }
            }
        }catch(Exception a){
            System.out.println("Error al generar los caminos mas cortos");
        }
    }

    public static void refreshRoute(MatrizAdyacencia grafo, Route refreshRoute,Integer newWeight, ArrayList<Carrier> carriers,ArrayList<Airport> airports, ArrayList<Route> existingRoutes, Recorrido actualPaths) {
        for(Route route :existingRoutes){
            if (route.identifier.equals(refreshRoute.identifier)){
                try{
                    grafo.agregarArco(route.index,route.indexAssigned, newWeight);
                }catch (Exception e){
                    System.out.println("error al agregar ruta al grafo:refresh");
                }
            }
        }
        addRouteArrayList(grafo, carriers,airports, existingRoutes, actualPaths);
    }

    public static void aircraftSpawn(ArrayList<String> ports, ArrayList<Carrier> carriers, ArrayList<Airport> airports, ArrayList<Aircraft> aircrafts, int aircraftNumber){
        int randomAirportIndex = Methods.randomAirportIndex();
        int randomCarrierIndex = Methods.randomCarrierIndex();

        if (ports.get(randomCarrierIndex).equals("carriers")){
            if (randomAirportIndex > 1){
                randomCarrierIndex = 1;
            }
            ArrayList<Route> randomRoute = Methods.chooseRandomRouteCarrier(carriers.get(randomCarrierIndex).getDestiny());
            aircrafts.add(new Aircraft(randomRoute,aircraftNumber));
        } else {
            ArrayList<Route> randomRoute = Methods.chooseRandomRouteAirport(airports.get(randomAirportIndex).getDestiny());
            aircrafts.add(new Aircraft(randomRoute,aircraftNumber));

        }
    }

    public static void aircraftUpdate(ArrayList<Aircraft> aircrafts,ArrayList<Aircraft> aircraftsToRemove,float aircraftWaitTimer, float delta, float AIRCRAFT_ENTITY_MIN_WAIT_TIME, float AIRCRAFT_ENTITY_MAX_WAIT_TIME){
        for (Aircraft aircraft: aircrafts) {
            aircraft.update(delta, aircraft.route);
            if (aircraft.getX() == aircraft.route.xEnd && aircraft.getY() == aircraft.route.yEnd){
                if (aircraft.routeIndex < aircraft.routesToFollow.size()){
                    aircraft.invincible = true;
                    if (aircraftWaitTimer <=0 ){
                        aircraft.route = aircraft.routesToFollow.get(aircraft.routeIndex);
                        aircraft.invincible = false;
                        aircraft.routeIndex ++;
                    }
                } else {
                    aircraft.destination = true;
                }
            }
            if (aircraft.remove){
                aircraftsToRemove.add(aircraft);
            }
        }
    }

    public static void explosionsUpdate(ArrayList<Explosion> explosions, ArrayList<Explosion> explosionsToRemove, float delta){
        for (Explosion explosion: explosions) {
            explosion.update(delta);
            if (explosion.remove){
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);
    }

    public static void bulletsUpdate(ArrayList<Bullet> bullets, ArrayList<Bullet> bulletsToRemove, float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove) {
                bulletsToRemove.add(bullet);
            }
        }
    }

    public static void rocketsUpdate(ArrayList<Rocket> rockets, ArrayList<Rocket> rocketsToRemove, float delta){
        for (Rocket rocket : rockets) {
            rocket.update(delta);
            if (rocket.remove) {
                rocketsToRemove.add(rocket);
            }
        }
    }

    public static void changeRouteDanger(ArrayList<Route> routes, ArrayList<Carrier> carriers, ArrayList<Airport> airports, String identifier){
        for (Route route : routes){
            if (route.identifier.equals(identifier)) {
                route.dangerLVL += 1;
            }
        }
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

    public static void destroyAircraft(ArrayList<Bullet> bulletsToRemove, Bullet bullet, ArrayList<Aircraft> aircraftsToRemove, Aircraft aircraft, ArrayList<Explosion> explosions, ArrayList<Route> existingRoutes, ArrayList<Carrier> carriers, ArrayList<Airport> airports){
        bulletsToRemove.add(bullet);
        aircraftsToRemove.add(aircraft);
        explosions.add(new Explosion(aircraft.getX(), aircraft.getY()));
        changeRouteDanger(existingRoutes,carriers,airports, aircraft.route.identifier);
    }

    public static void addDMGtoWeight(ArrayList<Route> routes, Aircraft aircraft){
        for (Route route : routes){
            if (aircraft.route.identifier.equals(route.identifier)){
                if (route.dangerLVL >= 2){
                    route.dmgweight += 5000;
                }
                if (route.dangerLVL == 1 ){
                    route.dmgweight += 300;
                }
            }

        }
    }

    private static int randomAirportIndex() {
        return (int) (Math.random() * (3));
    }

    private static int randomCarrierIndex() {
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

    private static ArrayList<Route> chooseRandomRouteAirport(ArrayList<ArrayList<Route>> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private static ArrayList<Route> chooseRandomRouteCarrier(ArrayList<ArrayList<Route>> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private static void createCarrierToCarrierRoute(Carrier carrier, Carrier carrierAssigned, ArrayList<Route> routes, ArrayList<Route> existingRoutes, int routeIdentifier){
        Route route = new Route("carrier","carrier",carrier.getIndex(),carrierAssigned.getIndex(),carrier.getX(),carrier.getY(),carrierAssigned.getX(),carrierAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(carrier.identifier).append("-").append(carrierAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(existingRoutes,route)) {
            routes.add(route);
            existingRoutes.add(route);
            routeIdentifier ++;
        }
    }

    private static void createCarrierToAirportRoute(Carrier carrier, Airport airportAssigned, ArrayList<Route> routes, ArrayList<Route> existingRoutes, int routeIdentifier){
        Route route = new Route("carrier","airport",carrier.getIndex(),airportAssigned.getIndex(), carrier.getX(),carrier.getY(),airportAssigned.getX(),airportAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(carrier.identifier).append("-").append(airportAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(existingRoutes,route)){
            routes.add(route);
            existingRoutes.add(route);
            routeIdentifier ++;
        }
    }

    private static void createAirportToCarrierRoute(Airport airport, Carrier carrierAssigned, ArrayList<Route> routes, ArrayList<Route> existingRoutes, int routeIdentifier){
        Route route = new Route("airport","carrier",airport.getIndex(),carrierAssigned.getIndex(),airport.getX(),airport.getY(),carrierAssigned.getX(),carrierAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(airport.identifier).append("-").append(carrierAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(existingRoutes,route)) {
            routes.add(route);
            existingRoutes.add(route);
            routeIdentifier ++;
        }
    }

    private static void createAirportToAirportRoute(Airport airport, Airport airportAssigned, ArrayList<Route> routes, ArrayList<Route> existingRoutes, int routeIdentifier){
        Route route = new Route("airport","airport",airport.getIndex(),airportAssigned.getIndex(), airport.getX(),airport.getY(),airportAssigned.getX(),airportAssigned.getY(),0);
        StringBuilder ID = new StringBuilder().append(airport.identifier).append("-").append(airportAssigned.identifier);
        route.identifier = String.valueOf(ID);
        if (canCreate(existingRoutes,route)){
            routes.add(route);
            existingRoutes.add(route);
            routeIdentifier ++;
        }
    }

    public static ArrayList<Route> assignCarrierRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, Carrier carrier, ArrayList<Route> existingRoutes, int routeIdentifier){

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
                    createCarrierToCarrierRoute(carrier, carrierAssigned, routes, existingRoutes, routeIdentifier);
                }
            } else {
                Airport airportAssigned = airports.get(randomAirportIndex);
                createCarrierToAirportRoute(carrier,airportAssigned,routes, existingRoutes, routeIdentifier);
                }
            if (routes.size() != routesToAssign -1){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                if (randomCarrierIndex != unavailable) {
                    createCarrierToCarrierRoute(carrier, carrierAssigned, routes, existingRoutes, routeIdentifier);
                }
                Airport airportAssigned = airports.get(randomAirportIndex);
                createCarrierToAirportRoute(carrier,airportAssigned,routes, existingRoutes, routeIdentifier);
            }
            }
        return routes;
    }

    private static ArrayList<Route> assignAirportRoutes(ArrayList<Carrier> carriers, ArrayList<Airport> airports, Airport airport, ArrayList<Route> existingRoutes, int routeIdentifier){

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
                createAirportToCarrierRoute(airport, carrierAssigned, routes, existingRoutes, routeIdentifier);
            } else {
                Airport airportAssigned = airports.get(randomAirportIndex);
                if (randomAirportIndex != unavailable){
                    createAirportToAirportRoute(airport,airportAssigned,routes, existingRoutes, routeIdentifier);
                }
            }
            if (routes.size() != routesToAssign -1){
                Carrier carrierAssigned = carriers.get(randomCarrierIndex);
                createAirportToCarrierRoute(airport, carrierAssigned, routes, existingRoutes, routeIdentifier);
                Airport airportAssigned = airports.get(randomAirportIndex);
                if (randomAirportIndex != unavailable){
                    createAirportToAirportRoute(airport,airportAssigned,routes, existingRoutes, routeIdentifier);
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

    public static char assignRouteIdentifier(int index) {
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

    public static String printRoutes(ArrayList<Route> routes){
        String string = "";
        for (Route route : routes){
            string += route.identifier + " ";
        }
        return string;
    }

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

    public static String keyPressed(String username){
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
            username = username.concat("A");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)){
            username = username.concat("B");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
            username = username.concat("C");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            username = username.concat("D");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            username = username.concat("E");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)){
            username = username.concat("F");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)){
            username = username.concat("G");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
            username = username.concat("H");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            username = username.concat("I");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)){
            username = username.concat("J");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)){
            username = username.concat("K");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)){
            username = username.concat("L");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            username = username.concat("M");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)){
            username = username.concat("N");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)){
            username = username.concat("O");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            username = username.concat("P");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            username = username.concat("Q");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)){
            username = username.concat("R");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)){
            username = username.concat("S");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            username = username.concat("T");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)){
            username = username.concat("U");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
            username = username.concat("V");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
            username = username.concat("W");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            username = username.concat("X");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)){
            username = username.concat("Y");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            username = username.concat("Z");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            username = username.concat(" ");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DEL)){
            if (!username.equals("")) {
                username = username.substring(0, username.length() - 1);
            }
        }
        return username;
    }

}
