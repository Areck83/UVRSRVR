package mx.uv;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class App{
    private static Gson gson = new Gson();
    public static void main( String[] args ){
        port(obtenerPuertoConexion());
        //port(80);
        //NotaDAO.crearNota(new Nota("12", "alfredo", "zmsoj", "solicitar", "2022-10-23 10:10:04", "202", "Quiero hacer tarea"));
        //La siguiente fraccion de codigo previene la aparicion del error del CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            System.out.println(accessControlRequestHeaders);
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            System.out.println(accessControlRequestMethod);
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));
        //port(obtenerPuertoConexion()); //Se supone que se debe de conectar al puerto del ambiente.
        System.out.println("Se ha pasado la prueba del CORS");

        post("/registrarNota", (req, res) -> {
            System.out.print("Ha entrado en la accion de registrar nota");
            String datosForm = req.body();
            Nota nota = gson.fromJson(datosForm, Nota.class);
            JsonObject respuesta = new JsonObject();
            if(nota==null){
                //res.status(400);
                respuesta.addProperty("mensaje", false);
                return respuesta;
            }
            res.status(200);
            System.out.print("Se ha mandado la solicitud a crear la nota");
            return NotaDAO.crearNota(nota);
        });
    }

    //Esta funcion obtiene un puerto del ambiente. Sirve si no se encuentra en la opcion del despliegue
    static int obtenerPuertoConexion() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //Se supone que es el puerto default
    }
}
