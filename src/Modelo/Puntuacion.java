package Modelo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Puntuacion {

    public int calcularPuntos(List<Dado> tirada) {
        int puntos = 0;
        int[] array = convertir(tirada);
        if(cincoUnos(array)){
            puntos += 10000;
            vaciarLista(tirada);
            return puntos;
        }
        if(escalera(array)){
            vaciarLista(tirada);
            puntos += 500;
            return puntos;
        }
        int iguales=tresIguales(tirada);
        if(iguales == 1){
            puntos += 1000;
        }
        else if(iguales != -1){
            puntos += iguales*100;
        }
        puntos += calcularIndividuales(tirada);
        return puntos;
    }

    private int[] convertir(List<Dado> tirada){
        int[] array = new int[tirada.size()];
        for(int i = 0;i < tirada.size();i++){
            array[i] = tirada.get(i).getCaraPrincipal();
        }
        return array;
    }

    private int calcularIndividuales(List<Dado> tirada){
        int contadorUnos = 0;
        int contadorCincos = 0;
        Iterator<Dado> iterator=tirada.iterator();
        while(iterator.hasNext()) {
            Dado d= iterator.next();
            if (d.getCaraPrincipal() == 1) {
                contadorUnos++;
                iterator.remove();
            }
            if (d.getCaraPrincipal() == 5) {
                contadorCincos++;
                iterator.remove();
            }
        }
        contadorCincos = contadorCincos * 50;
        contadorUnos = contadorUnos * 100;
        return contadorCincos + contadorUnos;
    }

    private int tresIguales(List<Dado> tirada){
        for (Dado d : tirada) {
            int contador = 0;
            for (Dado d1 : tirada) {
                if (d.getCaraPrincipal() == d1.getCaraPrincipal()) {
                    contador++;
                }
            }
            if (contador >= 3) {
                int eliminados = 0;
                Iterator<Dado> iteradorEliminar = tirada.iterator();
                while (iteradorEliminar.hasNext()) {
                    Dado d1 = iteradorEliminar.next();
                    if (d1.getCaraPrincipal() == d.getCaraPrincipal() && eliminados <= 2) {
                        iteradorEliminar.remove();
                    } else eliminados++;
                }
                return d.getCaraPrincipal();
            }
        }
        return -1;
    }

    private boolean cincoUnos(int[] array){
        if(array.length != 5){
            return false;
        }
        return Arrays.equals(array, new int[]{1, 1, 1, 1, 1});
    }

    private boolean escalera(int[] array){
        if(array.length != 5){
            return false;
        }
        Arrays.sort(array);
        if (Arrays.equals(array,new int[]{1,2,3,4,5})){
            return true;
        } else if (Arrays.equals(array,new int[]{2,3,4,5,6})){
            return true;
        } else return Arrays.equals(array, new int[]{1, 3, 4, 5, 6});
    }

    private void vaciarLista(List<Dado> tirada){
        tirada.clear();
    }

}
