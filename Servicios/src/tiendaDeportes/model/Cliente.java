package tiendaDeportes.model;

import java.util.Random;

public class Cliente implements Runnable {

    private Long id;
    private Supermercado supermercado;
    private Random random = new Random();

    public Cliente(Long id, Supermercado supermercado) {
        this.id = id;
        this.supermercado = supermercado;
    }

    @Override
    public void run() {
        int numCaja = 0;
        try {
            System.out.printf("el cliente %d quiere entrar al supermercado%n", this.id);
            supermercado.entrar();
            System.out.printf("el cliente %d ha entrado al supermercado%n", this.id);
            try {
                Thread.sleep(3000);
                numCaja = random.nextInt(3) + 1;
                supermercado.ocuparCaja(numCaja);
                System.out.printf("el cliente %d ha entrado en la caja %d%n", this.id, numCaja);
                try {
                    double importe = // número 1-100 con dos decimales
                            Math.round(
                                    (random.nextDouble(99) + 1) * 100.0
                            ) / 100.0;
                    supermercado.cobrar(numCaja, importe);
                    System.out.printf("Cliente %d ha pagado %.2f € en la caja %d%n", this.id, importe, numCaja);
                } finally {
                    supermercado.liberarCaja(numCaja); // Si ya había cogido la caja y falla algo al cobrar, la caja no se queda bloqueada.
                    System.out.printf("Cliente %d ha salido de la caja %d%n", this.id, numCaja);
                }
            } finally {
                supermercado.salir(); // Si el cliente ya había entrado y luego falla algo, no se queda ocupando plaza para siempre.
                System.out.printf("Cliente %d ha salido del supermercado%n", this.id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("Cliente %d fue interrumpido%n", this.id);
        }
    }
}
