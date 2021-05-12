package com.example.pracprocesos.cucumber.lecturaCiudades;
import com.example.pracprocesos.Ciudad;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(Cucumber.class)
public class LecturaCiudades {

    @Given("^The player entered the name of the virus and started the game$")
    public void the_player_entered_the_name_of_the_virus_and_started_the_game() throws Throwable {
        System.out.println("El jugador ha introducido un nombre al virus");
    }

    @When("^The game reads and starts loading the data of the cities$")
    public void the_game_reads_and_starts_loading_the_data_of_the_cities() throws Throwable {
        ConcurrentHashMap<String, Ciudad> gameGraph = new ConcurrentHashMap<String,Ciudad>();
        gameGraph = InputData("newGameData.txt");
        if (gameGraph.size()!=22){
            throw new Exception("No se han podido leer los datos de las ciudades correctamente");
        }
        else{
            System.out.println("Estas han sido las ciudades que se han leido el archivo y con las que el juego va a interactuar");
            for (Map.Entry<String, Ciudad> entry : gameGraph.entrySet()) {
                System.out.println("Ciudad: " + entry.getKey());
            }
        }
    }

    @Then("^The game starts$")
    public void the_game_starts() throws Throwable {
        System.out.println("El juego ha cargado correctamente los datos");
    }

    public ConcurrentHashMap<String, Ciudad> InputData(String archivo) {
        ConcurrentHashMap<String, Ciudad> mapa = new ConcurrentHashMap<>();
        try {
            File initialFile = new File("src/main/assets/newGameData.txt");
            InputStream targetStream = new FileInputStream(initialFile);
            int size = targetStream.available();
            byte[] buffer = new byte[size];
            targetStream.read(buffer);
            targetStream.close();
            String allData = new String(buffer, StandardCharsets.UTF_8);
            //System.out.println(allData);
            Scanner sc = new Scanner(allData);

            while (sc.hasNextLine()) {
                String nombre = sc.nextLine();
                String comunidad = sc.nextLine();
                float densidad = Float.parseFloat(sc.nextLine());
                int poblacion = Integer.parseInt(sc.nextLine());
                int sanos = Integer.parseInt(sc.nextLine());
                int infectados = Integer.parseInt(sc.nextLine());
                int muertos = 0;
                int totalsanitario = Integer.parseInt(sc.nextLine());
                int hospitalizados = Integer.parseInt(sc.nextLine());
                String colindantes = sc.nextLine();
                String puertos = sc.nextLine();
                String aeropuertos = sc.nextLine();

                ArrayList<String> listaColindantes = stringToList(colindantes);
                ArrayList<String> listaPuertos = stringToList(puertos);
                ArrayList<String> listaAeropuertos = stringToList(aeropuertos);

                Ciudad ciu = new Ciudad(nombre,
                        comunidad,
                        densidad,
                        poblacion,
                        sanos,
                        infectados,
                        muertos,
                        totalsanitario,
                        hospitalizados,
                        listaColindantes,
                        listaPuertos,
                        listaAeropuertos);
                mapa.put(nombre, ciu);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return mapa;
    }
    public ArrayList<String> stringToList(String s){
        ArrayList<String> lista = new ArrayList<String>();
        String[] array = s.split(", ");
        for (int i = 0; i < array.length; i++){
            if (!array[i].equals("")) {
                lista.add(array[i]);
            }
        }
        return lista;
    }

}
