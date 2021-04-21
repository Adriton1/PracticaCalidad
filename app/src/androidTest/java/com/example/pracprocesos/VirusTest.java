package com.example.pracprocesos;


import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class VirusTest extends TestCase {

    public void testIsEveryoneDead() {
        Virus virus = new Virus();
        ConcurrentHashMap<String, Ciudad> gameGraph = new ConcurrentHashMap<String, Ciudad>();
        for (int i=0; i!=21; i++) {
            Ciudad ciu = new Ciudad(String.valueOf(i),
                    "a",
                    10000,
                    1000000,
                    0,
                    0,
                    1000000,
                    0,
                    0,
                    null,
                    null,
                    null);
            gameGraph.put(String.valueOf(i), ciu);
        }
        virus.actualizar(gameGraph);
        boolean muertos = virus.isEveryoneDead(gameGraph);
        assert(muertos);
    }

    public void testIsNotEveryoneDead() {
        Virus virus = new Virus();
        ConcurrentHashMap<String, Ciudad> gameGraph = new ConcurrentHashMap<String, Ciudad>();
        for (int i=0; i!=21; i++) {
            if (i==3){
                Ciudad ciu = new Ciudad(String.valueOf(i),
                        "a",
                        10000,
                        1000000,
                        0,
                        0,
                        999999,
                        0,
                        0,
                        null,
                        null,
                        null);
                gameGraph.put(String.valueOf(i), ciu);
            }
            else {
            Ciudad ciu = new Ciudad(String.valueOf(i),
                    "a",
                    10000,
                    1000000,
                    0,
                    0,
                    1000000,
                    0,
                    0,
                    null,
                    null,
                    null);
            gameGraph.put(String.valueOf(i), ciu);
            }
        }
        virus.actualizar(gameGraph);
        boolean muertos = virus.isEveryoneDead(gameGraph);
        assertFalse(muertos);
    }
}