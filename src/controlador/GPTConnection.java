
package controlador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class GPTConnection {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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"; // Reemplaza con tu clave de API de OpenAI
    private static int contadorSolicitudes = 0;
    private static final int LIMITE_SOLICITUDES = 60; 
        
   public static String obtenerRespuestaChatGPT(String mensaje) {
        try {
            if (contadorSolicitudes >= LIMITE_SOLICITUDES) {
            throw new RuntimeException("Se ha alcanzado el límite de solicitudes permitido en este período.");
             }
            contadorSolicitudes++;
            // Crea la URL de la API
            URL url = new URL(API_URL);

            // Abre una conexión HTTP
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conexion.setDoOutput(true);

            // Construye el cuerpo de la solicitud
            String cuerpoSolicitud = "{\"model\": \"text-embedding-ada-002\", \"messages\": [{\"role\": \"user\", \"content\": \"" + mensaje + "\"}]}";

            // Envía la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                byte[] input = cuerpoSolicitud.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Obtiene la respuesta
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        }catch (IOException e) {
            if (e.getMessage().contains("HTTP response code: 429")) {
                System.out.println("Se alcanzó el límite de solicitudes. Esperando antes de volver a intentar...");
                try {
                    // Espera 5 segundos
                    Thread.sleep(5000);

                } catch (InterruptedException ex) {
                    // Manejar la excepción si el hilo es interrumpido mientras está dormido
                    System.err.println("El hilo fue interrumpido durante la espera.");
                    Thread.currentThread().interrupt();  // Restaurar la bandera de interrupción
                }
                return obtenerRespuestaChatGPT(mensaje);
            } else {
                throw new RuntimeException("Error al realizar la solicitud a la API de ChatGPT", e);
            }
        }
    }
    
}


