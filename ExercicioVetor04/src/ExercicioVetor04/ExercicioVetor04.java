package ExercicioVetor04;

import java.util.Scanner;

public class ExercicioVetor04 {

	public static void main(String[] args) {
		Scanner leitor = new Scanner(System.in);
		
		// Cria o vetor com os dias dos meses do ano
		int[] diasMes= {0,31,28,31,30,31,30,31,31,30,31,30,31};
		
		int dia;
		int mes;
		int quantDias=0;
		
		// Solicita que o usu�rio digite o valor do dia
		do {
			System.out.println("Digite o valor do dia "+
		                        "(valor entre 1 e 31)" );
			dia = leitor.nextInt();
		} while (dia < 1 || dia > 31);
		
		// Solicita que o usu�rio digite o valor do m�s
		do {
			System.out.println("Digite o valor do m�s "+
		                        "(valor entre 1 e 12)" );
			mes = leitor.nextInt();
		} while (mes < 1 || mes > 12);
		
		// Somar a quantidade de dias dos meses anteriores
		// ao m�s digitado pelo usu�rio
		for (int i=0; i < mes; i++) {
			quantDias += diasMes[i];
		}
		
		// Somar o valor do dia � soma quantDias
		quantDias += dia;
		
		// Exibe a quantidade de dias correspondente ao dia/m�s
		System.out.println("A quantidade de dias � " + quantDias);
		
		
		leitor.close();
                           
	}

}
