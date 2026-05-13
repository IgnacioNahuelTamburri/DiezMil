package Vista;

import Controlador.Controlador;

public interface Vista {

    void iniciar();

    void iniciar(String nombre);

    void setJugador(String nombre);

    void setControlador(Controlador controlador);

    void finTurno();

    void ganador();

    void errorGuardar();

    void errorCargar();

    void noGuardado();

    void cambioDeTurno();
}
