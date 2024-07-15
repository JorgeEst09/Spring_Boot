package com.alurachallenge.LibreriaGutendex.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    /**
     * Realiza una solicitud HTTP GET a la URL especificada y devuelve la respuesta como una cadena JSON.
     * @param url La URL a la que se va a realizar la solicitud HTTP GET.
     * @return La respuesta de la API en formato JSON.
     * @throws RuntimeException Si ocurre un error al realizar la solicitud HTTP.
     */
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener datos de la URL: " + url, e);
        }

        String json = response.body();
        return json;
    }
}
