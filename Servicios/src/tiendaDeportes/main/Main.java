package tiendaDeportes.main;

import tiendaDeportes.model.Cliente;
import tiendaDeportes.model.Supermercado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("¿Cuántos clientes entran hoy? ");
        int numClientes = sc.nextInt();

        Supermercado supermercado = new Supermercado();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = new Cliente((long) i + 1, supermercado);
            Thread hilo = new Thread(cliente);
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error esperando a que termine un hilo.");
            }
        }

        System.out.printf("%nTotal caja 1: %.2f €%n", supermercado.getCaja1().getRecaudado());
        System.out.printf("Total caja 2: %.2f €%n", supermercado.getCaja2().getRecaudado());
        System.out.printf("Total caja 3: %.2f €%n", supermercado.getCaja3().getRecaudado());
        System.out.printf("Total recaudado: %.2f €%n", supermercado.getTotalRecaudado());
    }
}
