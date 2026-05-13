package Modelo;

import java.io.Serializable;
import java.util.List;

public record DatosSerializables(List<Jugador> jugadores, int turno, int turnoTotal) implements Serializable {

}