/*
 * Imaginamos una tienda de deportes que tiene 3 cajas en funcionamiento 
 * en todo momento para cobrar a los clientes.
	A dicho supermercado no pueden acceder más de 15 personas a la vez, 
	ya que es muy pequeña y queremos seguir cumpliendo la normativa de 
	cercanía para evitar problemas de salud. A su vez, sólo un cliente 
	puede estar en cada caja para cobrarle.
	Queremos saber cuánto dinero hemos ganado con cada una de las cajas 
	que entran a comprar y el total al cerrar la aplicación.
	NOTA: Ahora mismo no es necesario complicarse en la aplicación para 
	la compra de cada cliente, vale con indicar una cantidad por teclado 
	o aleatoria de lo que gasta cada uno.
*/

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Supermercado extends Thread{
	
	protected static double caja1, caja2, caja3;
	Random r = new Random();
	protected static Scanner sc = new Scanner(System.in);
	private int id;
	
	private static Semaphore tienda = new Semaphore(15);
	private static Semaphore c1 = new Semaphore(1);
	private static Semaphore c2 = new Semaphore(1);
	private static Semaphore c3 = new Semaphore(1);
	
	
	protected Supermercado(int id) {
		this.id = id;
	}
	
	public void run() {
		int ale=0; //Numero aleatorio para la caja
		try {
			tienda.acquire();
				System.out.println("El cliente "+this.id+" ha entrado en la tienda");
				Thread.sleep(3000);
				ale=(int) (Math.random()*3+1);
				switch(ale) {
					case 1: c1.acquire();
								System.out.println("El cliente "+this.id+" ha entrado en la caja 1");
								caja1+=r.nextDouble(100)+1;
							c1.release();
							System.out.println("El cliente "+this.id+" ha salido en la caja 1");
						break;
					case 2: c2.acquire();
								System.out.println("El cliente "+this.id+" ha entrado en la caja 2");
								caja2+=r.nextDouble(100)+1;
							c2.release();
							System.out.println("El cliente "+this.id+" ha salido en la caja 2");
						break;
					case 3: c3.acquire();
								System.out.println("El cliente "+this.id+" ha entrado en la caja 3");
								caja3+=r.nextDouble(100)+1;
							c3.release();
							System.out.println("El cliente "+this.id+" ha salido en la caja 2");
						break;
				}
			tienda.release();
			System.out.println("El cliente "+this.id+" ha salido del supermercado");
		}
		catch(InterruptedException e) {
			System.out.print("Error en el acceso");
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		int clientes=0;
		System.out.println("¿Cuántos clientes van a entrar en la tienda?");
		clientes=sc.nextInt();
		
		ArrayList<Thread> lista = new ArrayList<>();
		
		for(int i=1; i<=clientes; i++) {
			Thread hilo = new Supermercado(i);
			lista.add(hilo);
			hilo.start();
		}
		
		for(Thread hilo: lista) {
			hilo.join();
		}
		
		System.out.println("Total de la caja 1 es: "+caja1);
		System.out.println("Total de la caja 2 es: "+caja2);
		System.out.println("Total de la caja 3 es: "+caja3);
		System.out.println("Total de las cajas es: "+(caja1+caja2+caja3));

}

}
