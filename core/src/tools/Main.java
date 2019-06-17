package tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {

        HashMap score = Score.extraerDatos();
        Set<String> keys = score.keySet();
        for(String key: keys){
            System.out.println(key);
        }
    }
}
