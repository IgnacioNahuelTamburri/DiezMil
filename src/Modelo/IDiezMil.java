package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDiezMil extends Remote, IObservableRemoto {

    void iniciarJuego() throws RemoteException;

    void agregarJugador(String nombre) throws RemoteException;

    void eliminarJugador(String nombre) throws RemoteException;

    List<Jugador> getJugadores() throws RemoteException;

    List<Integer> getDados() throws RemoteException;

    void elegirDados(List<Integer> dados) throws RemoteException;

    void desseleccionarDados(List<Integer> elegidos) throws RemoteException;

    //List<Map.Entry<String, Integer>> getLeaderBoard();

    int calcularPuntos() throws RemoteException;

    void plantarse() throws RemoteException;

    void finalizarTurno() throws RemoteException;

    void tirarDados() throws RemoteException;

    boolean puedeSeguir() throws RemoteException;

    Jugador turnoDe() throws RemoteException;

    void cargarPartida() throws RemoteException;

    //void removeObserver(Observer observer);

    int getAcumulados() throws RemoteException;

    void notifyObservers(String acontecimiento) throws RemoteException;
}
