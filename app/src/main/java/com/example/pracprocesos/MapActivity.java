package com.example.pracprocesos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Math.random;

public class MapActivity extends AppCompatActivity {

    TextView lugar, sanos, infectados, muertos, textTurno;
    Button botonVirus, botonInfectar;
    ImageView imagen_mapa, mapa_zonas;
    ConcurrentHashMap<String, Ciudad> grafo = new ConcurrentHashMap<String,Ciudad>();
    Virus virus = new Virus();
    int turno = -1;
    Set<String> infectadas = new HashSet<String>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_map);

        TextView textTurno = (TextView) findViewById(R.id.textView_turno);
        virus.setNombre((String)getIntent().getSerializableExtra("virus"));
        botonVirus = (Button) findViewById(R.id.botonVirus);
        botonVirus.setText(virus.getNombre());
        grafo = InputData("newGameData.txt");
        virus.actualizar(grafo);
        mostrarVirus(virus);

        botonVirus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                virus.actualizar(grafo);
                mostrarVirus(virus);
            }
        });

        imagen_mapa = (ImageView) findViewById(R.id.imagen_mapa);
        mapa_zonas = (ImageView) findViewById(R.id.mapa_areas_colores);

        imagen_mapa.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP) {
                    clickMapa((int) event.getX(), (int) event.getY());
                }
                return true;
            }
        });

        botonInfectar = (Button) findViewById(R.id.infectar);

        botonInfectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lugar = (TextView) findViewById(R.id.lugar);
                String ciudad = (String) lugar.getText();

                if ((turno > -1) || (grafo.get(ciudad) != null)) {
                    if (turno > -1) {
                        grafo = viajes(grafo);
                        for (String c : infectadas) {
                            grafo = propagacion(grafo,c);
                        }
                    }
                    else {   // Primer turno, hace falta ciudad
                        grafo.get(ciudad).sumSanos(-10);
                        grafo.get(ciudad).sumInfectados(10);
                        botonInfectar.setText("Siguiente turno");
                        infectadas.add(ciudad);
                    }
                    if (grafo.get(ciudad) != null) {
                        cambiarDatosLateral(ciudad,
                                grafo.get(ciudad).getSanos(),
                                grafo.get(ciudad).getInfectados(),
                                grafo.get(ciudad).getMuertos());
                    }else{
                        virus.actualizar(grafo);
                        mostrarVirus(virus);
                    }
                    turno++;
                    String aux = "Turno " + turno;
                    textTurno.setText(aux);
                }

            }
        });
    }

    public void cambiarDatosLateral(String sLugar, int numSanos, int numInfectados, int numMuertos){
        lugar = (TextView) findViewById(R.id.lugar);
        lugar.setText(sLugar);
        sanos = (TextView) findViewById(R.id.sanos);
        sanos.setText(Integer.toString(numSanos));
        infectados = (TextView) findViewById(R.id.infectados);
        infectados.setText(Integer.toString(numInfectados));
        muertos = (TextView) findViewById(R.id.muertos);
        muertos.setText(Integer.toString(numMuertos));
    }

    public void mostrarVirus(Virus virus){
        cambiarDatosLateral("España", virus.getSanos(), virus.getInfectados(), virus.getMuertos());
    }

    /* Zona para interactividad del mapa */
    /*
        Tutorial de uso: https://blahti.wordpress.com/2012/06/26/images-with-clickable-areas/
        La idea clave: tenemos una imagen transparente con colores, comprobamos si al tocar esa
        imagen, el píxel que toca es de cierto color. Cada color corresponde a una autonomía.
     */

    public void clickMapa(int coord_x, int coord_y) {

        int color = colorDePunto(coord_x,coord_y);
        String sitio = null;

        if (colorSimilar(color, Color.parseColor("#A24FFF"))) {
            sitio = "Valladolid";
        }
        else if (colorSimilar(color, Color.parseColor("#FFCB7F"))) {
            sitio = "Santiago de Compostela";
        }
        else if (colorSimilar(color, Color.parseColor("#D2A9CB"))) {
            sitio = "Oviedo";
        }
        else if (colorSimilar(color, Color.parseColor("#F07995"))) {
            sitio = "Santander";
        }
        else if (colorSimilar(color, Color.parseColor("#8AC277"))) {
            sitio = "Vitoria";
        }
        else if (colorSimilar(color, Color.parseColor("#FF9700"))) {
            sitio = "Pamplona";
        }
        else if (colorSimilar(color, Color.parseColor("#4D72FF"))) {
            sitio = "Logroño";
        }
        else if (colorSimilar(color, Color.parseColor("#BBC19F"))) {
            sitio = "Zaragoza";
        }
        else if (colorSimilar(color, Color.parseColor("#A59794"))) {
            sitio = "Barcelona";
        }
        else if (colorSimilar(color, Color.parseColor("#BD5858"))) {
            sitio = "Madrid";
        }
        else if (colorSimilar(color, Color.parseColor("#FFD8BB"))) {
            sitio = "Mérida";
        }
        else if (colorSimilar(color, Color.parseColor("#FFF24D"))) {
            sitio = "Toledo";
        }
        else if (colorSimilar(color, Color.parseColor("#FEA98C"))) {
            sitio = "Valencia";
        }
        else if (colorSimilar(color, Color.parseColor("#669BA1"))) {
            sitio = "Palma de Mallorca";
        }
        else if (colorSimilar(color, Color.parseColor("#009D78"))) {
            sitio = "Murcia";
        }
        else if (colorSimilar(color, Color.parseColor("#FF3A3A"))) {
            sitio = "Sevilla";
        }
        else if (colorSimilar(color, Color.parseColor("#D46CD3"))) {
            sitio = "Málaga";
        }
        else if (colorSimilar(color, Color.parseColor("#FFA6FE"))) {
            sitio = "Algeciras";
        }
        else if (colorSimilar(color, Color.parseColor("#D5D5D5"))) {
            sitio = "Ceuta";
        }
        else if (colorSimilar(color, Color.parseColor("#434343"))) {
            sitio = "Melilla";
        }
        else if (colorSimilar(color, Color.parseColor("#DFFF74"))) {
            sitio = "Las Palmas de Gran Canaria";
        }
        else if (colorSimilar(color, Color.parseColor("#728926"))) {
            sitio = "Santa Cruz de Tenerife";
        }
        else {
            sitio = "España";
        }
        System.out.println(sitio);
        if (grafo.get(sitio) != null){
        System.out.println("Sanos: " + grafo.get(sitio).getSanos());
        System.out.println("Infectados: " + grafo.get(sitio).getInfectados());
        System.out.println("Muertos: " + grafo.get(sitio).getMuertos());
            cambiarDatosLateral(sitio,
                    grafo.get(sitio).getSanos(),
                    grafo.get(sitio).getInfectados(),
                    grafo.get(sitio).getMuertos());
        } else {
            virus.actualizar(grafo);
            mostrarVirus(virus);
        }

    }

    public int colorDePunto(int x, int y) {
        this.mapa_zonas.setDrawingCacheEnabled(true);
        Bitmap puntos = Bitmap.createBitmap(this.mapa_zonas.getDrawingCache());
        this.mapa_zonas.setDrawingCacheEnabled(false);
        return puntos.getPixel(x,y);
    }

    public boolean colorSimilar (int color1, int color2) {
        int tolerancia = 25;
        if (Math.abs (Color.red (color1) - Color.red (color2)) > tolerancia )
            return false;
        if (Math.abs (Color.green (color1) - Color.green (color2)) > tolerancia )
            return false;
        if (Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerancia )
            return false;
        return true;
    }

    /***************
     * CARGAR DATOS
     */

    public ConcurrentHashMap<String, Ciudad> InputData(String archivo) {
        ConcurrentHashMap<String, Ciudad> mapa = new ConcurrentHashMap<>();
        try {
            InputStream is = getAssets().open(archivo);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
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

    /***********************
     * ECUACIONES DEL VIRUS Y DE LOS VIAJES (Y SUS MÉTODOS)
     */
    Random rand = new Random();
    float d_min = Float.MAX_VALUE;
    ConcurrentHashMap<String, Ciudad> propagacion(ConcurrentHashMap<String, Ciudad> grafo, String nomCiudad){ //Devuelve el grafo
        Ciudad ciudad = grafo.remove(nomCiudad);
        int old_inf = ciudad.getInfectados();
        float densidad = ciudad.getPoblacion()/ciudad.getSuperficie();
        d_min = Math.min(densidad, d_min);
        float deathline = 1f;
        if (ciudad.getInfectados() > ciudad.getHospitalizados()) deathline = 1.0000005f;
        int infNuevos = (int) Math.round(0.3*Math.pow((Math.log(densidad) - 0.3*Math.log(Math.max(100, d_min)) + 1) * old_inf, deathline));
        int muertos;
        if (turno < 20) {
            muertos = (int) Math.pow((old_inf * Math.abs(Math.random() * 0.1)), deathline);
        }
        else if (turno < 40) {
            muertos = (int) Math.pow((old_inf * Math.abs(Math.random() * 0.2)), deathline);
        }
        else if (turno < 60) {
            muertos = (int) Math.pow((old_inf * Math.abs(Math.random() * 0.5)), deathline);
        }
        else {
            muertos = (int) Math.pow((old_inf * Math.abs(Math.random() * 0.8)), deathline);
        }

        int infTotales = infNuevos - muertos;
        ciudad.sumInfectados(infTotales - old_inf);
        ciudad.sumSanos(old_inf - infNuevos);
        ciudad.sumMuertos(muertos);
        ciudad.sumInfectados(-muertos);

        if(turno > 40 && ciudad.getInfectados()< 10){
            ciudad.setMuertos(ciudad.getMuertos() +ciudad.getInfectados());
            ciudad.setInfectados(0);
        }
        
        grafo.put(nomCiudad, ciudad);
        return grafo;
    }

    ConcurrentHashMap<String, Ciudad> viajes(ConcurrentHashMap<String, Ciudad> grafo){
        Set<String> infec_antiguos = new HashSet<>(infectadas);

        for (String c : infec_antiguos) {
            Ciudad origen = grafo.get(c);
            int infectados = origen.getInfectados();
            if ((infectados > 500)) {
                int viajaTierra, viajaAire, viajaMar;
                ArrayList<String> conexiones_tierra = origen.getTierra();
                ArrayList<String> conexiones_mar = origen.getMar();
                ArrayList<String> conexiones_aire = origen.getAire();
                if (!conexiones_tierra.isEmpty()) {
                    for (String t : conexiones_tierra) {
                        double prob = Math.random();
                        if (!infec_antiguos.contains(t) && (prob > 0.6)) {
                            Ciudad destino = grafo.get(t);
                            int sanos_destino = destino.getSanos();
                            viajaTierra = (int) Math.round((0.035 * infectados * 0.9) + (0.035 * infectados * Math.random() * 0.1));
                            destino.sumInfectados(viajaTierra);
                            destino.sumSanos(-viajaTierra);
                            infectadas.add(t);
                        }
                    }
                }
                if (!conexiones_aire.isEmpty()) {
                    for (String a : conexiones_aire) {
                        double prob = Math.random();
                        if (!infec_antiguos.contains(a) && (prob > 0.6)) {
                            Ciudad destino = grafo.get(a);
                            int sanos_destino = destino.getSanos();
                            viajaAire = (int) Math.round((0.012 * infectados * 0.9) + (0.012 * infectados * Math.random() * 0.1));
                            destino.sumInfectados(viajaAire);
                            destino.sumSanos(-viajaAire);
                            infectadas.add(a);
                        }
                    }
                }
                if (!conexiones_mar.isEmpty()) {
                    for (String m : conexiones_mar) {
                        double prob = Math.random();
                        if (!infec_antiguos.contains(m) && (prob > 0.6)) {
                            Ciudad destino = grafo.get(m);
                            int sanos_destino = destino.getSanos();
                            viajaMar = (int) Math.round((0.003 * infectados * 0.9) + (0.003 * infectados * Math.random() * 0.1));
                            destino.sumInfectados(viajaMar);
                            destino.sumSanos(-viajaMar);
                            infectadas.add(m);
                        }
                    }
                }
            }
        }

        return grafo;
    }

    /*HashMap<String, Ciudad> viajes(@NotNull HashMap<String, Ciudad> grafo, String nomOrigen){
        Ciudad origen = grafo.remove(nomOrigen);
        int poblacion = origen.getPoblacion();
        int viajaTierra = (int) Math.round(Math.random() * 0.035 * poblacion);
        int viajaMar = (int) Math.round(Math.random() * 0.003 * poblacion);
        int viajaAire = (int) Math.round(Math.random() * 0.012 * poblacion);

        float sigmaInf = origen.getInfectados()/origen.getSanos();
        int infTierra = (int) Math.round(sigmaInf * viajaTierra);
        int infMar = (int) Math.round(sigmaInf * viajaMar);
        int infAire = (int) Math.round(sigmaInf * viajaAire);

        int infAcumulado = 0, sanAcumulado = 0;
        int nTierra = origen.getTierra().size();
        int nMar = origen.getMar().size();
        int nAire = origen.getAire().size();
        String nomDestino = null;
        Ciudad destino = null;
        int viajaInfectados = 0, viajaSanos = 0;
        for (int i = 0; i < nTierra - 1; i++){
            nomDestino = origen.getTierra().get(i);
            destino = grafo.remove(nomDestino);
            viajaInfectados = (int) Math.round(infTierra / nTierra);
            viajaSanos = (int) Math.round(viajaTierra / nTierra) - viajaInfectados;
            infAcumulado += viajaInfectados;
            sanAcumulado += viajaSanos;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            origen.sumPoblacion(-viajaSanos - viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            destino.sumPoblacion(viajaSanos + viajaInfectados);
            grafo.put(nomDestino, destino);
        };
        nomDestino = origen.getTierra().get(nTierra-1);
        destino = grafo.remove(nomDestino);
        if (Objects.nonNull(destino)) {
            viajaInfectados = infTierra - infAcumulado;
            viajaSanos = viajaTierra - infTierra - sanAcumulado;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            grafo.put(nomDestino, destino);
        }

        for (int i = 0; i < nAire - 1; i++){
            nomDestino = origen.getAire().get(i);
            destino = grafo.remove(nomDestino);
            viajaInfectados = (int) Math.round(infAire / nAire);
            viajaSanos = (int) Math.round(viajaAire / nAire) - viajaInfectados;
            infAcumulado += viajaInfectados;
            sanAcumulado += viajaSanos;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            origen.sumPoblacion(-viajaSanos - viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            destino.sumPoblacion(viajaSanos + viajaInfectados);
            grafo.put(nomDestino, destino);
        };
        nomDestino = origen.getAire().get(nAire-1);
        destino = grafo.remove(nomDestino);
        if (Objects.nonNull(destino)) {
            viajaInfectados = infAire - infAcumulado;
            viajaSanos = viajaAire - infAire - sanAcumulado;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            grafo.put(nomDestino, destino);
        }
        for (int i = 0; i < nMar - 1; i++){
            nomDestino = origen.getMar().get(i);
            destino = grafo.remove(nomDestino);
            viajaInfectados = (int) Math.round(infMar / nMar);
            viajaSanos = (int) Math.round(viajaMar / nMar) - viajaInfectados;
            infAcumulado += viajaInfectados;
            sanAcumulado += viajaSanos;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            origen.sumPoblacion(-viajaSanos - viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            destino.sumPoblacion(viajaSanos + viajaInfectados);
            grafo.put(nomDestino, destino);
        };
        nomDestino = origen.getMar().get(nMar-1);
        destino = grafo.remove(nomDestino);
        if (Objects.nonNull(destino)) {
            viajaInfectados = infMar - infAcumulado;
            viajaSanos = viajaMar - infMar - sanAcumulado;
            origen.sumSanos(-viajaSanos);
            origen.sumInfectados(-viajaInfectados);
            destino.sumSanos(viajaSanos);
            destino.sumInfectados(viajaInfectados);
            grafo.put(nomDestino, destino);
        }

        grafo.put(nomOrigen, origen);
        return grafo;
    }*/

}