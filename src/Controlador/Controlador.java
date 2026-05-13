package Controlador;

import Modelo.IDiezMil;
import Modelo.Jugador;
import Vista.Vista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements IControladorRemoto {

    private Vista vista;

    private IDiezMil IDiezMil;

    public Controlador(Vista vista){
        setVista(vista);
    }

    public void setVista(Vista vista){
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void agregarJugador(String nombre) {
        try {
            IDiezMil.agregarJugador(nombre);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void eliminarJugador(String nombre){
        try {
            IDiezMil.eliminarJugador(nombre);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        //IDiezMil.removeObserver(this);
    }

    public List<Integer> getDados(){
        try {
            return IDiezMil.getDados();
        }catch (RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getJugadores(){
        List<String> nombres = new ArrayList<>();
        try {
            for (Jugador j : IDiezMil.getJugadores()) {
                nombres.add(j.getNombre());
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return nombres;
    }

    public List<Integer> getPuntos(){
        List<Integer> puntos = new ArrayList<>();
        try{
            for(Jugador j : IDiezMil.getJugadores()){
                puntos.add(j.getPuntos());
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return puntos;
    }

    public boolean puedeSeguir(){
        try{
            return IDiezMil.puedeSeguir();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }

    public void iniciarJuego(){
        try{
            IDiezMil.iniciarJuego();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public List<Integer> tirarDados() {
        try {
            IDiezMil.tirarDados();
            return IDiezMil.getDados();
        }catch (RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    public int calcularPuntos(){
        try {
            return IDiezMil.calcularPuntos();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void plantarse(){
        try{
            IDiezMil.plantarse();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void finalizarTurno(){
        try{
            IDiezMil.finalizarTurno();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public String turnoDe(){
        try{
            return IDiezMil.turnoDe().getNombre();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "";
    }

    public void cargarPartida(){
        try{
            IDiezMil.cargarPartida();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public int getAcumulados(){
        try{
            return IDiezMil.getAcumulados();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void elegirDados(List<Integer> elegidos){
        try{
            IDiezMil.elegirDados(elegidos);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void desseleccionarDados(List<Integer> elegidos){
        try{
            IDiezMil.desseleccionarDados(elegidos);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.IDiezMil = (IDiezMil) t;
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {
        if(o instanceof String) {
            if (o.equals("Inicio")) {
                vista.cambioDeTurno();
            } else if (o.equals("Ganador")) {
                vista.ganador();
            } else if (o.equals("FinTurno")) {
                vista.finTurno();
            } else if (o.equals("Error Guardar")) {
                vista.errorGuardar();
            } else if (o.equals("Error Cargar")) {
                vista.errorCargar();
            } else if (o.equals("No Guardado")) {
                vista.noGuardado();
            }
        }
    }
}
