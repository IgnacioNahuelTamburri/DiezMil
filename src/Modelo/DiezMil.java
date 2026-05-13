package Modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DiezMil extends ObservableRemoto implements IDiezMil {

    private static final int ganar = 500;

    private List<Jugador> jugadores;

    private List<Dado> dados;

    private List<Dado> seleccionados;

    private int turno;

    private int turnoTotal;

    private final Cubilete cubilete;

    private final Puntuacion puntuacion;

    //private Leaderboard leaderboard;

    public DiezMil(){
        this.cubilete = new Cubilete();
        this.jugadores = new ArrayList<>();
        this.seleccionados = new ArrayList<>();
        this.turno = 0;
        this.turnoTotal = 0;
        this.puntuacion = new Puntuacion();
        /*try {
            File top = new File("LeaderBoard.ser");
            if (top.exists()) {
                leaderboard = Leaderboard.loadFromFile("LeaderBoard.ser");
            }else{
                leaderboard = new Leaderboard();
                leaderboard.saveToFile("LeaderBoard.ser");
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            leaderboard = new Leaderboard();
        }*/
        generarDados();
    }

    private void generarDados(){
        dados=new ArrayList<>();
        for(int i=0;i<5;i++) {
            dados.add(new Dado());
        }
        cubilete.mezclar(dados);
    }

    public void iniciarJuego() throws RemoteException{
        notifyObservers("Inicio");
    }

    public void agregarJugador(String nombre) throws RemoteException{
        jugadores.add(new Jugador(nombre));
    }

    public void eliminarJugador(String nombre) throws RemoteException{
        jugadores.removeIf(j -> j.getNombre().equals(nombre));
    }

    public List<Jugador> getJugadores() throws RemoteException{
        return jugadores;
    }

    public List<Integer> getDados() throws RemoteException{
        return convertirNumeros(dados);
    }

    public void elegirDados(List<Integer> elegidos) throws RemoteException{
        List<Dado> convertida = convertirDados(elegidos);
        List<Dado> nueva = new ArrayList<>(convertida);
        this.seleccionados = new ArrayList<>(convertida);
        for(Dado d : convertida){
            this.dados.remove(d);
        }
        if(dados.isEmpty()){
            generarDados();
        }
        sumarAcumulados(puntuacion.calcularPuntos(nueva));
    }

    public void desseleccionarDados(List<Integer> elegidos) throws RemoteException{
        restarPuntos(puntuacion.calcularPuntos(convertirDados(elegidos)));
    }

    private void restarPuntos(int puntos){
        jugadores.get(turno).restarAcumulados(puntos);
    }

    public int calcularPuntos() throws RemoteException{
        List<Dado> nueva = new ArrayList<>(seleccionados);
        int puntos = puntuacion.calcularPuntos(nueva);
        if(nueva.isEmpty()){
            return puntos;
        }else {
            return 0;
        }
    }

    public int getAcumulados(){
        return jugadores.get(turno).getPuntosAcumulados();
    }

    public void sumarAcumulados(int puntos) throws RemoteException {
        jugadores.get(turno).sumarAcumulados(puntos);
    }

    public void plantarse() throws RemoteException{
        jugadores.get(turno).sumarPuntos();
        if(jugadores.get(turno).getPuntos() < ganar){
            finalizarTurno();
        }else{
            ganador();
        }
    }

    public void finalizarTurno() throws RemoteException{
        jugadores.get(turno).resetearAcumulados();
        turno++;
        turnoTotal++;
        if(turno >= jugadores.size()){
            turno = 0;
        }
        generarDados();
        guardarPartida();
        this.notifyObservers("FinTurno");
    }

    private void ganador() throws RemoteException{
        eliminarGuardado();
        /*leaderboard.agregarJugador(turnoDe().getNombre(),turnoTotal);
        try{
            leaderboard.saveToFile("LeaderBoard.ser");
        }catch (IOException e){
            e.printStackTrace();
        }*/
        this.notifyObservers("Ganador");
    }

    public void tirarDados() throws RemoteException{
        cubilete.mezclar(dados);
    }

    public boolean puedeSeguir() throws RemoteException{
        List<Dado> nueva = new ArrayList<>(seleccionados);
        int puntos = puntuacion.calcularPuntos(nueva);
        return nueva.isEmpty();
    }

    public Jugador turnoDe() throws RemoteException{
        return jugadores.get(turno);
    }

    private void guardarPartida() throws RemoteException{
        DatosSerializables datosSerializables = new DatosSerializables(jugadores, turno, turnoTotal);
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("PartidaGuardada.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(datosSerializables);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException e){
            notifyObservers("Error Guardar");
        }
    }

    private void eliminarGuardado(){
        File archivoGuardado = new File("PartidaGuardada.ser");
        if(archivoGuardado.exists()){
            archivoGuardado.delete();
        }
    }

    @Override
    public void cargarPartida() throws RemoteException{
        File archivoGuardado = new File("PartidaGuardada.ser");
        DatosSerializables datosSerializables = null;
        if(archivoGuardado.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream("PartidaGuardada.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                datosSerializables = (DatosSerializables) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            }catch (IOException | ClassNotFoundException e){
                notifyObservers("Error Cargar");
            }
        }
        if(datosSerializables != null){
            this.turnoTotal = datosSerializables.turnoTotal();
            this.jugadores = datosSerializables.jugadores();
            this.turno = datosSerializables.turno();
            notifyObservers("Inicio");
        }else {
            notifyObservers("No Guardado");
        }
    }

    @Override
    public void notifyObservers(String acontecimiento) throws RemoteException {
        this.notificarObservadores(acontecimiento);
    }

    private List<Dado> convertirDados(List<Integer> numeros){
        List<Dado> dados = new ArrayList<>();
        for(int i : numeros){
            dados.add(new Dado(i));
        }
        return dados;
    }

    private List<Integer> convertirNumeros(List<Dado> numeros){
        List<Integer> dados = new ArrayList<>();
        for(Dado d : numeros){
            dados.add(d.getCaraPrincipal());
        }
        return dados;
    }

}
