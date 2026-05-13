import Modelo.DiezMil;
import Modelo.IDiezMil;
import ar.edu.unlu.rmimvc.RMIMVCException;

import java.rmi.RemoteException;

public class Servidor {
    public static void main(String[] args) {
        IDiezMil diezMil = new DiezMil();
        ar.edu.unlu.rmimvc.servidor.Servidor servidor = new ar.edu.unlu.rmimvc.servidor.Servidor("127.0.0.1",40000);
        try {
            servidor.iniciar(diezMil);
        }catch (RemoteException | RMIMVCException e){
            e.printStackTrace();
        }
    }
}
