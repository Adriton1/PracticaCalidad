package com.example.pracprocesos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class MapActivity extends AppCompatActivity {

    TextView lugar, sanos, infectados, muertos, textTurno;
    Button botonVirus, botonInfectar;
    ImageView imagenMapa, mapaZonas;
    ConcurrentHashMap<String, Ciudad> gameGraph = new ConcurrentHashMap<String,Ciudad>();
    Virus virus = new Virus();
    int turno = -1;
    Set<String> infectadas = new HashSet<String>();
    HashMap<Integer,String> colorToCity=new HashMap<Integer, String>();
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
        gameGraph = InputData("newGameData.txt");
        virus.actualizar(gameGraph);
        mostrarVirus(virus);

        botonVirus.setOnClickListener(v -> {
            virus.actualizar(gameGraph);
            mostrarVirus(virus);
        });

        imagenMapa = (ImageView) findViewById(R.id.imagen_mapa);
        mapaZonas = (ImageView) findViewById(R.id.mapa_areas_colores);

        imagenMapa.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP) {
                clickMapa((int) event.getX(), (int) event.getY());
            }
            return true;
        });

        botonInfectar = (Button) findViewById(R.id.infectar);

        botonInfectar.setOnClickListener((View.OnClickListener) v -> {
            lugar = (TextView) findViewById(R.id.lugar);
            String ciudad = (String) lugar.getText();
            boolean isEveryoneDead= virus.isEveryoneDead(gameGraph);
            if (isEveryoneDead){
                finishing();
            }
            if ((turno > -1) || (gameGraph.get(ciudad) != null)) {
                if (turno > -1) {
                    gameGraph = viajes(gameGraph);
                    for (String c : infectadas) {
                        gameGraph = propagacion(gameGraph,c);
                    }
                }
                else {   // Primer turno, hace falta ciudad
                    gameGraph.get(ciudad).sumSanos(-10);
                    gameGraph.get(ciudad).sumInfectados(10);
                    botonInfectar.setText("Siguiente turno");
                    infectadas.add(ciudad);
                }
                if (gameGraph.get(ciudad) != null) {
                    cambiarDatosLateral(ciudad,
                            gameGraph.get(ciudad).getSanos(),
                            gameGraph.get(ciudad).getInfectados(),
                            gameGraph.get(ciudad).getMuertos());
                }else{
                    virus.actualizar(gameGraph);
                    mostrarVirus(virus);
                }
                turno++;
                String aux = "Turno " + turno;
                textTurno.setText(aux);
            }

        });
        colorToCity.put(Color.parseColor("#A24FFF"),"Valladolid");
        colorToCity.put(Color.parseColor("#FFCB7F"),"Santiago de Compostela");
        colorToCity.put(Color.parseColor("#D2A9CB"),"Oviedo");
        colorToCity.put(Color.parseColor("#F07995"),"Santander");
        colorToCity.put(Color.parseColor("#8AC277"),"Vitoria");
        colorToCity.put(Color.parseColor("#FF9700"),"Pamplona");
        colorToCity.put(Color.parseColor("#4D72FF"),"Logroño");
        colorToCity.put(Color.parseColor("#BBC19F"),"Zaragoza");
        colorToCity.put(Color.parseColor("#A59794"),"Barcelona");
        colorToCity.put(Color.parseColor("#BD5858"),"Madrid");
        colorToCity.put(Color.parseColor("#FFD8BB"),"Mérida");
        colorToCity.put(Color.parseColor("#FFF24D"),"Toledo");
        colorToCity.put(Color.parseColor("#FEA98C"),"Valencia");
        colorToCity.put(Color.parseColor("#669BA1"),"Palma de Mallorca");
        colorToCity.put(Color.parseColor("#009D78"),"Murcia");
        colorToCity.put(Color.parseColor("#FF3A3A"),"Sevilla");
        colorToCity.put(Color.parseColor("#D46CD3"),"Málaga");
        colorToCity.put(Color.parseColor("#FFA6FE"),"Algeciras");
        colorToCity.put(Color.parseColor("#D5D5D5"),"Ceuta");
        colorToCity.put(Color.parseColor("#434343"),"Melilla");
        colorToCity.put(Color.parseColor("#DFFF74"),"Las Palmas de Gran Canaria");
        colorToCity.put(Color.parseColor("#728926"),"Santa Cruz de Tenerife");
    }

    private void finishing() {
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setMessage("Has Ganado!!!\n"+"España ha sido exterminada" );
        alert.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog= alert.create();
        dialog.show();
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
        String sitio = "España";
        sitio=colorToCity.get(color);
        
        System.out.println(sitio);
        if (gameGraph.get(sitio) != null){
        System.out.println("Sanos: " + gameGraph.get(sitio).getSanos());
        System.out.println("Infectados: " + gameGraph.get(sitio).getInfectados());
        System.out.println("Muertos: " + gameGraph.get(sitio).getMuertos());
            cambiarDatosLateral(sitio,
                    gameGraph.get(sitio).getSanos(),
                    gameGraph.get(sitio).getInfectados(),
                    gameGraph.get(sitio).getMuertos());
        } else {
            virus.actualizar(gameGraph);
            mostrarVirus(virus);
        }

    }

    public int colorDePunto(int x, int y) {
        this.mapaZonas.setDrawingCacheEnabled(true);
        Bitmap puntos = Bitmap.createBitmap(this.mapaZonas.getDrawingCache());
        this.mapaZonas.setDrawingCacheEnabled(false);
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
                int viajaTierra=0;
                int viajaAire=0;
                int viajaMar=0;
                ArrayList<String> conexiones_tierra = origen.getTierra();
                ArrayList<String> conexiones_mar = origen.getMar();
                ArrayList<String> conexiones_aire = origen.getAire();

                auxFunction(infec_antiguos,conexiones_tierra,viajaTierra,grafo,infectados);

                auxFunction(infec_antiguos,conexiones_mar,viajaMar,grafo,infectados);

                auxFunction(infec_antiguos,conexiones_aire,viajaAire,grafo,infectados);

            }
        }

        return grafo;
    }
    private void auxFunction(Set<String> elder,ArrayList<String> myList,int myNum,ConcurrentHashMap<String, Ciudad> graph,int infectados){
        if (!myList.isEmpty()) {
            for (String m : myList) {
                double prob = Math.random();
                if (!elder.contains(m) && (prob > 0.6)) {
                    Ciudad destino = graph.get(m);
                    myNum = (int) Math.round((0.003 * infectados * 0.9) + (0.003 * infectados * Math.random() * 0.1));
                    destino.sumInfectados(myNum);
                    destino.sumSanos(-myNum);
                    infectadas.add(m);
                }
            }
        }
    }

}