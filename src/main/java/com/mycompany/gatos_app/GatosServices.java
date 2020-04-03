package com.mycompany.gatos_app;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GatosServices {

    public static void verGatos() throws IOException {
        //1. Vamos a traer los datos de la API
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();

        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        //Cortar los corchetes
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);

        //Crear un objeto  de la clase Gson
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);

        //Redimencionar en caso de necesitar
        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(image);

            if (fondoGato.getIconWidth() > 800) {
                //Redimencionamos
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + " 1. Ver Otra Imagen \n"
                    + " 2. Favorito \n"
                    + " 3. Volver \n";

            String[] botones = {"Ver Otra Imagen", "Favorito", "Volver"};
            String idGato = (gatos.getUrl());
            String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE,
                    fondoGato, botones, botones[0]);

            int seleccion = -1;
            //Validamos la opcion seleccionada por el usuario
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }

            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gatos gato) {
        try {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\": \"" + gato.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void verFavoritos(String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();

        //Guardamos el String con la respuesta
        String elJson = response.body().string();

        //Creamos el objeto gson
        Gson gson = new Gson();

        GatosFavoritos[] gatosArray = gson.fromJson(elJson, GatosFavoritos[].class);

        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) * min;
            int indice = aleatorio - 1;

            GatosFavoritos gatofavorito = gatosArray[indice];

            //Redimencionar en caso de necesitar
            Image image = null;
            try {
                URL url = new URL(gatofavorito.image.getUrl());
                image = ImageIO.read(url);

                ImageIcon fondoGato = new ImageIcon(image);

                if (fondoGato.getIconWidth() > 800) {
                    //Redimencionamos
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n"
                        + " 1. Ver Otra Imagen \n"
                        + " 2. Eliminar Favorito \n"
                        + " 3. Volver \n";

                String[] botones = {"Ver Otra Imagen", "Eliminar Favorito", "Volver"};
                String idGato = (gatofavorito.getId());
                String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE,
                        fondoGato, botones, botones[0]);

                int seleccion = -1;
                //Validamos la opcion seleccionada por el usuario
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;
                    }
                }

                switch (seleccion) {
                    case 0:
                        verFavoritos(apiKey);
                        break;
                    case 1:
                        borrarFavorito(gatofavorito);
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void borrarFavorito(GatosFavoritos gatofavorito){
        
    }
}
