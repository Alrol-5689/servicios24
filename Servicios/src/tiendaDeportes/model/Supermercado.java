package tiendaDeportes.model;

import lombok.Data;

import java.util.concurrent.Semaphore;

@Data
public class Supermercado {
    private Semaphore aforo = new Semaphore(15);
    private Caja caja1 = new Caja(1L);
    private Caja caja2 = new Caja(2L);
    private Caja caja3 = new Caja(3L);

    public void entrar() {
        try {
            aforo.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo interrumpido al intentar entrar al supermercado");
        }
    }

    public void salir() {
        aforo.release();
    }

    public void ocuparCaja(int numCaja) throws InterruptedException {
        switch (numCaja) {
            case 1 -> caja1.ocupar();
            case 2 -> caja2.ocupar();
            case 3 -> caja3.ocupar();
            default -> throw new IllegalArgumentException("Número de caja no válido");
        };
    }

    public void liberarCaja(int numCaja) {
        if (numCaja == 1) {
            caja1.liberar();
        } else if (numCaja == 2) {
            caja2.liberar();
        } else if (numCaja == 3) {
            caja3.liberar();
        } else {
            throw new IllegalArgumentException("Número de caja no válido");
        }
    }

    public void cobrar(int numCaja, double importe) {
        if (numCaja == 1) {
            caja1.sumarImporte(importe);
        } else if (numCaja == 2) {
            caja2.sumarImporte(importe);
        } else if (numCaja == 3) {
            caja3.sumarImporte(importe);
        } else {
            throw new IllegalArgumentException("Número de caja no válido");
        }
    }

    public double getTotalRecaudado() {
        return caja1.getRecaudado() + caja2.getRecaudado() + caja3.getRecaudado();
    }
}
