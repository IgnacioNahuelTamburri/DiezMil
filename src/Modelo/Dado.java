package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dado implements Serializable {

    private final List<Integer> caras=new ArrayList<>();

    private int caraPrincipal;

    public Dado(){
        for(int i=1;i<=6;i++){
            this.caras.add(i);
        }
        caraPrincipal=caras.get(0);
    }

    public Dado(int cara){
        this();
        caraPrincipal=caras.get(cara-1);
    }

    public void lanzarDado(){
        Random random=new Random();
        int numero=random.nextInt(6);
        caraPrincipal=caras.get(numero);
    }

    public int getCaraPrincipal() {
        return caraPrincipal;
    }

    @Override
    public String toString(){
        return "Dado "+getCaraPrincipal();
    }

    @Override
    public boolean equals(Object obj) {
        // Verificar si es el mismo objeto
        if (this == obj) {
            return true;
        }

        // Verificar si el objeto es de la misma clase
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Comparar los atributos relevantes
        Dado otroDado = (Dado) obj;
        return (this.caraPrincipal == otroDado.getCaraPrincipal());
    }
}