import java.util.Scanner;

class VariableContador {
	public static int contador = 0;
	
	private static final Object mutex = new Object();
	
	public static void incrementarContador() {
		synchronized (mutex) {
			contador++;
		}
	}
}

class Hilocontador extends Thread {
	
	char vocal;
	
	public Hilocontador(char vocal) {
		this.vocal  = vocal;
	}
	
	public void verificacionVocal(char caracter) {	
		if (caracter == vocal) {
			VariableContador.incrementarContador();		
		}
	}
}

public class ConteoVocales {
	
	public static final int NUM_VOCALES = 5;
	
	public static void main(String[] args) {
		
		Hilocontador hilos[];	
		String texto = "";
		
		System.out.println ("Introduzca un texto: ");
		
		// Pedir texto por consola
		Scanner entrada = new Scanner(System.in);
		texto = entrada.nextLine();	
		entrada.close();
			
		hilos = new Hilocontador[NUM_VOCALES];
		
		// Inicialización de los hilos que cuentan las vocales
		hilos[0] = new Hilocontador('a');
		hilos[1] = new Hilocontador('e');
		hilos[2] = new Hilocontador('i');
		hilos[3] = new Hilocontador('o');
		hilos[4] = new Hilocontador('u');
		
		char caracterTemp;
		
		// Ejecución de todos los hilos
		for (int i = 0; i < hilos.length; i++) {	
			hilos[i].start();
		}	
		
		// Recorrido de todos los caracteres del texto	
		for (int i = 0; i < texto.length(); i++) {
			caracterTemp = Character.toLowerCase(texto.charAt(i));
			
			for (int j = 0; j < hilos.length; j++) {
				hilos[j].verificacionVocal(caracterTemp);
			}
		}
		
		for (int i = 0; i < hilos.length; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Numero de vocales: " + VariableContador.contador);
	}

}
