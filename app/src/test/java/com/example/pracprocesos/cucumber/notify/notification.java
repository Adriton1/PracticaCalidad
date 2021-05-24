package com.example.pracprocesos.cucumber.notify;
import android.view.View;

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
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


@RunWith(Cucumber.class)
public class notification {
    String ciudad;
    int numSanos=0;
    ConcurrentHashMap<String, Ciudad> gameGraph = InputData("copyGameData.txt");
    boolean noMorePeople;
    @Given("^the player is in a province and no people are left$")
    public void the_player_is_in_a_province_and_no_people_are_left() throws Throwable {
        String ciudad="Zaragoza";
        numSanos=gameGraph.get(ciudad).getSanos();

    }


    @When("^the player pushes the Infect button$")
    public void the_player_pushes_the_infect_button() throws Throwable {
        if(numSanos<=0){
            noMorePeople=true;
            System.out.println("Ya no puedes seguir infectando");
        }
        else{
            System.out.println("Puedes seguir infectando");
            noMorePeople=false;
        }
    }

    @Then("^the game gives a notification indicating there are no more people$")
    public void the_game_gives_a_notification_indicating_there_are_no_more_people() throws Throwable {
        System.out.println(numSanos);
    }
    public ConcurrentHashMap<String, Ciudad> InputData(String archivo) {
        ConcurrentHashMap<String, Ciudad> mapa = new ConcurrentHashMap<>();
        try {
            File initialFile = new File("src/main/assets/"+archivo);
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
