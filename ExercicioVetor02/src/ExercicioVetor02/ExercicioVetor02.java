package ExercicioVetor02;

import java.util.Scanner;

public class ExercicioVetor02 {

	public static void main(String[] args) {
		Scanner leitor = new Scanner(System.in);
		
		// Cria o vetor para 10 Strings
		String[] nome = new String[10];
		
		// Solicita que o usu�rio digite 10 nomes
		// para preencher o vetor nome]
		for(int i=0; i < nome.length; i++) {
			System.out.println("Digite um nome");
			nome[i] = leitor.nextLine();
		}
		
		// Solicita que o usu�rio digite um nome para ser
		// pesquisado no vetor de nomes
		System.out.println("Digite um nome para ser pesquisado");
		String nomeBusca = leitor.nextLine();
		
		// Verifica se nomeBusca existe no vetor nome
		// Se existir, exibe o �ndice onde o nomeBusca est�
		// Sen�o, exibe "Nome n�o encontrado"
		boolean achou = false; // Vari�vel achou indica se achou ou n�o
		for (int i=0; i < nome.length && !achou; i++) {
			if (nome[i].equals(nomeBusca)) {
				System.out.println("Encontrou o nome no �ndice "
						           + i);
				achou = true;
			}
		}
		
		// Se n�o encontrou, exibe a mensagem "Nome n�o encontrado"
		if (!achou) {
			System.out.println("Nome n�o encontrado");
		}
		
		leitor.close();

	}

}
