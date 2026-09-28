import Controlador.Controlador;
import Vista.Vista;
import Vista.VistaConsola;
import Vista.VistaGrafica;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;

import java.rmi.RemoteException;

public class Cliente2 {
    public static void main(String[] args) {
        Vista vista = new VistaConsola();
        IControladorRemoto controladorRemoto = new Controlador(vista);
        ar.edu.unlu.rmimvc.cliente.Cliente cliente = new ar.edu.unlu.rmimvc.cliente.Cliente("127.0.0.1",40002,"127.0.0.1",40000);
        try {
            cliente.iniciar(controladorRemoto);
            vista.iniciar();
        }catch (RMIMVCException | RemoteException e){
            e.printStackTrace();
        }
    }
}