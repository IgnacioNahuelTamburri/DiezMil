package Modelo;

import java.util.List;

public class Cubilete {

    public void mezclar(List<Dado> dados){
        for (Dado dado : dados) {
            dado.lanzarDado();
        }
    }

}
