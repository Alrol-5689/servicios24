package tiendaDeportes.model;

import java.util.concurrent.Semaphore;

public class Caja {
    private final Long id;
    private double recaudado;
    private final Semaphore semaforo;

    public Caja(Long id) {
        this.id = id;
        this.recaudado = 0;
        this.semaforo = new Semaphore(1);
    }

    public void ocupar() throws InterruptedException {
        semaforo.acquire(); // hilo espera hasta que puede entrar
    }

    public void liberar() {
        semaforo.release(); // avisamos a los hilos de que dejamos hueco libre
    }

    public synchronized void sumarImporte(double importe) {
        this.recaudado += importe;
    }

    public double getRecaudado() {
        return recaudado;
    }
}
