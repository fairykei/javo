package ExercicioVetor03;

import java.util.Scanner;

public class ExercicioVetor03 {

	public static void main(String[] args) {
		Scanner leitor = new Scanner (System.in);
		
		// Cria o vetor para armazenar 10 inteiros
		int[] num = new int[10];
		
		// Declara uma vari�vel soma para conter a soma dos valores
		double soma = 0;
		
		// Solicita que o usu�rio digite 10 valores inteiros
		// para preencher o vetor num
		for (int i=0; i < num.length; i++) {
			System.out.println("Digite um n�mero inteiro");
			num[i] = leitor.nextInt();
			soma += num[i];
		}
		// C�lculo da m�dia
		double media =  soma / num.length;
		System.out.println("A soma dos valores � " + soma);
		System.out.println("A m�dia dos valores � " + media);
		
		// Exibir os valores do vetor que est�o acima da media
		System.out.println("\nValores acima da m�dia:");
		for (int i=0; i < num.length; i++) {
			if (num[i] > media) {
				System.out.println(num[i]);
			}
		}
		
		

	}

}
