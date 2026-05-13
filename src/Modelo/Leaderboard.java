/*package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractMap.SimpleEntry;

public class Leaderboard implements Serializable{
    private List<Map.Entry<String,Integer>> listado;

    public Leaderboard(){
        listado = new ArrayList<>();
    }

    public void agregarJugador(String nombre, int turnos){
        listado.add(new SimpleEntry<>(nombre,turnos));
        Collections.sort(listado,Comparator.comparingInt(Map.Entry::getValue));
        if(listado.size()>10){
            listado.remove(listado.size()-1);
        }
    }

    public List<Map.Entry<String,Integer>> getListado(){
        return listado;
    }

    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        }
    }

    public static Leaderboard loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Leaderboard) ois.readObject();
        }
    }
}
*/