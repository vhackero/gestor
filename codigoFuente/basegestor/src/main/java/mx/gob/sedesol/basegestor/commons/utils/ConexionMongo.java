/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.commons.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import org.apache.log4j.Logger;

/**
 * Clase que conecta a la base de datos de Mongo
 * @author jonthansanchez
 */
public class ConexionMongo {
    private static final Logger LOG = Logger.getLogger(ConexionMongo.class.getSimpleName());
    private static MongoCredential mongoCredentials;
    private static MongoClient mongoClient;
    private static String servidor;
    private static int puerto;
    private static String usuario;
    private static String clave;
    
    public static MongoDatabase obtenerBaseDeDatos(ParametroSistemaService service) {
        
        obtenerConfiguraciones(service);
        if (servidor != null && usuario != null && clave != null) {
            mongoCredentials = MongoCredential.createCredential(usuario,
                ConstantesBitacora.BASE_MONGO,
                clave.toCharArray());

            mongoClient = new MongoClient(new ServerAddress(servidor, puerto), Arrays.asList(mongoCredentials));

            return mongoClient.getDatabase(ConstantesBitacora.BASE_MONGO);
        }
        return null;
    }
    
    public static void cerrarBaseDeDatos() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    
    private static void obtenerConfiguraciones(ParametroSistemaService service) {
        if (servidor == null) {
            servidor = service.obtenerParametro(ConstantesBitacora.SERVIDOR_MONGO);
        }
        try {
            puerto = Integer.parseInt(service.obtenerParametro(ConstantesBitacora.PUERTO_SERVIDOR_MONGO));
        } catch (NumberFormatException ex) {
            LOG.error("Error al obtener el puerto de Mongo: \n", ex);
        }
        if (usuario == null) {
            usuario = service.obtenerParametro(ConstantesBitacora.USUARIO_MONGO);
        }
        if (clave == null) {
            clave = service.obtenerParametro(ConstantesBitacora.PASSWORD_MONGO);
        }
    }
}
