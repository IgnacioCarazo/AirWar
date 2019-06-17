package tools;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Score {
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static void guardarDatos(HashMap hashMap) throws IOException {
        File rootFile = new File("package.json");
        File finalFile = new File(rootFile.getAbsolutePath());
        JSON_MAPPER.writeValue(finalFile,hashMap); //Serealizacion realizada
    }


    public static HashMap extraerDatos()  {
        File rootFile = new File("package.json");
        File finalFile = new File(rootFile.getAbsolutePath());
        HashMap hashMap = null; //Objeto Deserealizado
        System.out.println(finalFile);
        try {
            hashMap = JSON_MAPPER.readValue(finalFile, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

}
