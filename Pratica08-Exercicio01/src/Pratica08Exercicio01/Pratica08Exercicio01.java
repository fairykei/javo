package Pratica08Exercicio01;

import java.util.Stack;

public class Pratica08Exercicio01 {

	/* M�todo ehPalindromo - recebe um vetor de inteiros e devolve true
	 * se o vetor � pal�ndromo e false caso contr�rio
	 */
	public static boolean ehPalindromo(int[] v) {
		// Cria uma pilha para armazenar Integer
		Stack<Integer> pilha = new Stack<Integer>();
		
		// Empilha todos os elementos do vetor
		for (int i=0; i < v.length; i++) {
			pilha.push(v[i]);
		}
		
		/* Percorre o vetor no sentido normal, comparando cada elemento
		 * com o valor desempilhado da pilha. Se algum elemento do vetor
		 * for diferente ao desempilhado, retorna false (n�o � pal�ndromo)
		 */
		for (int i=0; i < v.length; i++) {
			if (v[i] != pilha.pop()) {
				return false;
			}
		}
		
		/* Se chegou aqui, � porque o vetor � pal�ndromo.
		 * Retorna true
		 */
		return true;
		
	}
	
	/* M�todo ehPalindromo2 - recebe um vetor de inteiros e devolve true
	 * se o vetor � pal�ndromo e false caso contr�rio
	 * Esse m�todo � semelhante ao m�todo anterior, mas percorre o vetor
	 * apenas uma vez
	 */
	public static boolean ehPalindromo2(int[] v) {
		Stack<Integer> pilha = new Stack<Integer>();
		int i;
		// Empilha metade do vetor
		for (i=0; i < v.length/2; i++) {
			pilha.push(v[i]);
		}
		// Se o tamanho do vetor � �mpar, avan�a o �ndice i
		if (v.length % 2 == 1) {
			i++;
		}
		// Percorre a 2a metade do vetor, comparando com os valores empilhados
		// Se algum valor do vetor for diferente ao desempilhado, retorna false
		for (; i < v.length; i++) {
			if (v[i] != pilha.pop()) {
				return false;
			}
		}
		// Vetor � pal�ndromo, ent�o retorna true
		return true;
		
	}
	
	public static void main(String[] args) {
		int[] vetor1 = {1,3,3,1};
		int[] vetor2 = {10,20,30,40};
		int[] vetor3 = {10,20,30,30,20,10};
		int[] vetor4 = {1,2,3,2,1};
		
		if (ehPalindromo2(vetor1)) {
			System.out.println("vetor1 � pal�ndromo");
		}
		else {
			System.out.println("vetor1 n�o � pal�ndromo");
		}
		
		if (ehPalindromo2(vetor2)) {
			System.out.println("vetor2 � pal�ndromo");
		}
		else {
			System.out.println("vetor2 n�o � pal�ndromo");
		}
		if (ehPalindromo2(vetor3)) {
			System.out.println("vetor3 � pal�ndromo");
		}
		else {
			System.out.println("vetor3 n�o � pal�ndromo");
		}
		if (ehPalindromo2(vetor4)) {
			System.out.println("vetor4 � pal�ndromo");
		}
		else {
			System.out.println("vetor4 n�o � pal�ndromo");
		}

	}

}
