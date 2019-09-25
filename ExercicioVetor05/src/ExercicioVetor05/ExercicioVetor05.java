package ExercicioVetor05;

import java.util.Scanner;

public class ExercicioVetor05 {

	public static void main(String[] args) {
		Scanner leitor = new Scanner(System.in);
		
		// Cria o vetor para receber 10 inteiros
		int[] vetor = new int[10];
		
		// Solicita que o usu�rio digite 10 valores
		// para preencher o vetor
		for(int i=0; i < vetor.length; i++) {
			System.out.println("Digite um valor inteiro");
			vetor[i] = leitor.nextInt();
		}
		// Solicita que o usu�rio digite um valor para ser
		// pesquisado no vetor
		System.out.println("Digite um valor inteiro para ser "+
                           "pesquisado" );
		int numBusca = leitor.nextInt();
		
		// Percorre o vetor para contar quantas vezes o numBusca
		// aparece no vetor
		int cont = 0;
		for (int i=0; i < vetor.length; i++) {
			if (vetor[i] == numBusca) {
				cont++;
			}
		}
		
		// Exibe quantas vezes o numBusca ocorre no vetor
		System.out.println("O n�mero " + numBusca + 
				" aparece " + cont + " vez(es) no vetor");
		
		leitor.close();
	}

}
