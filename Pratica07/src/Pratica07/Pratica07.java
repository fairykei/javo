package Pratica07;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Pratica07 {
	
	/* M�todo gravaArquivo - recebe uma lista de objetos Aluno
	 * e uma vari�vel isCSV que indica se � arquivo CSV ou n�o
	 * Se for arquivo CSV, grava os campos delimitados por ';'
	 * Se for arquivo TXT, grava os campos delimitados por ' '
	 */
	public static void gravaLista(ArrayList<Aluno> lista, boolean isCSV) {
		FileWriter arq = null;		// objeto FileWriter - representa o arquivo
		Formatter saida = null;		// objeto Formatter para executar sa�da formatada
		boolean deuRuim = false;	// indica se deu erro 
		String nomeArquivo;			// nome do arquivo
		
		if (isCSV) {
			nomeArquivo= "aluno.csv";	// nome do arquivo, se for CSV
		}
		else {
			nomeArquivo= "aluno.txt";	// nome do arquivo, se for TXT
		}
		
		/* Abre o arquivo para escrita e de forma
		 * que grave sempre no final do arquivo, sem apagar o que
		 * j� est� gravado no arquivo (append)
		 * 
		 * Vamos tratar esse arquivo como um objeto Formatter
		 * para poder gravar usando format
		 */
		try {
			arq = new FileWriter(nomeArquivo, true);  // true indica que vai fazer "append"
			saida = new Formatter(arq);
		}
		catch (IOException erro) {
			System.err.println("Erro ao abrir arquivo");
			System.exit(1);  // encerra o programa, com status de erro
		}
		
		// Grava no arquivo os dados dos objetos Aluno que est�o na lista 
		try {
			// percorre a lista de objetos Aluno
			for (Aluno a : lista) {
				// Grava os atributos do objeto aluno no arquivo
				// O %n indica que ser� gravado um fim de registro
				// No Windows, o fim de registro � um \r\n
				// No Linux e no MacOS, o fim de registro � um \n
				if (isCSV) {
					saida.format("%d;%s;%.2f%n",a.getRa(),	// grava os atributos do objeto aluno
								a.getNome(),a.getNota());	// separados por ';'
				}
				else {
					saida.format("%d %s %.2f%n",a.getRa(),	// grava os atributos do objeto aluno 
								a.getNome(),a.getNota());	// separados por ' '
				}	
			}
		}
		catch (FormatterClosedException erro) {
			System.err.println("Erro ao gravar no arquivo");
	           deuRuim= true;
	    }
		finally { // bloco finally � executado independente de dar erro ou n�o
				  // usado para fechar os objetos saida e close.
			saida.close();
			try {
				arq.close();
			}
			catch (IOException erro) {
				System.err.println("Erro ao fechar arquivo.");
				deuRuim = true;
			}
			if (deuRuim) {
				System.exit(1);
			}
		}
	}

	/* M�todo leExibeArquivo - l� e exibe os registros de um arquivo
	 * Recebe true se o arquivo a ser lido � o aluno.csv e false se for o aluno.txt
	 */
	public static void leExibeArquivo(boolean isCSV) {
		FileReader arq= null;		// objeto FileReader - representa o arquivo a ser lido
		Scanner entrada = null;		// objeto Scanner - para ler do arquivo
		String nomeArquivo;			// nome do arquivo
		boolean deuRuim= false;		// indica se deu erro
		
		if (isCSV) {
			nomeArquivo= "aluno.csv";	// nome do arquivo, se for CSV
		}
		else {
			nomeArquivo= "aluno.txt";	// nome do arquivo, se for TXT
		}

		// Abre o arquivo para leitura
		try {
			arq = new FileReader(nomeArquivo);
			if (isCSV) {
				// se o arquivo for CSV, usa como delimitador de campo o ';' e o fim de registro
				entrada = new Scanner(arq).useDelimiter(";|\\r\\n");
			}
			else {
				// se o arquivo for TXT, usa como delimitador de campo o ' ' e o fim de registro
				entrada = new Scanner(arq);
			}
		}
		catch (FileNotFoundException erro) {
			System.err.println("Arquivo n�o encontrado");
			System.exit(1); // encerra o programa, com status de erro
		}
		
		// L� os registros do arquivo e exibe os dados lidos na console
		try {
			// Exibe na console os t�tulos das colunas
			System.out.printf("%-8s%-10s%7s\n","RA","NOME","NOTA" );
			// Enquanto tem registro a ser lido
			while (entrada.hasNext()) {
				int ra = entrada.nextInt();				// L� o RA
				String nome = entrada.next();			// L� o nome
				double nota = entrada.nextDouble();		// L� a nota
				System.out.printf("%-8d%-10s%7.2f\n",ra,nome,nota);	// Exibe na console em colunas
			}
		}
	    catch (NoSuchElementException erro)
	    {
	        System.err.println("Arquivo com problemas.");
	        deuRuim = true;
	    }
	    catch (IllegalStateException erro)
	    {
	        System.err.println("Erro na leitura do arquivo.");
	        deuRuim = true;
	    }
	 	finally {
	 		entrada.close();
	 		try {
	 			arq.close();
	 		}
	 		catch (IOException erro) {
				System.err.println("Erro ao fechar arquivo.");
				deuRuim = true;
			}
	 		if (deuRuim) {
	 			System.exit(1);
	 		}
	 	}
	}

	public static void main(String[] args) {
		Scanner leitor = new Scanner(System.in);
		boolean fim = false;		// indica fim do programa
		int ra;						// usado para ler o RA do aluno
		String nome;				// usado para ler o nome do aluno
		double nota;				// usado para ler a nota do aluno
		int opcao;					// usado para ler a op��o digitada pelo usu�rio

		// Cria��o da lista que vai conter objetos Aluno
		ArrayList<Aluno> lista = new ArrayList<Aluno>();
		
		while (!fim) {	// Fica num loop at� que o usu�rio escolha Fim
			// Exibe o menu de op��es para o usu�rio
			System.out.println("\nEscolha uma das op��es:");
			System.out.println("1- Adicionar um aluno");
			System.out.println("2- Exibir a lista");
			System.out.println("3- Gravar a lista num arquivo txt");
			System.out.println("4- Gravar a lista num arquivo csv");
			System.out.println("5- Ler e exibir arquivo txt");
			System.out.println("6- Ler e exibir arquivo csv");
			System.out.println("7- Fim");
			// L� a op��o digitada pelo usu�rio
			opcao= leitor.nextInt();
			
			switch(opcao) {
			// Op��o 1 - Adicionar um aluno
			case 1:
				// Solicita que o usu�rio digite o RA, nome e nota do aluno, e l� os valores
				System.out.println("Digite o ra do aluno");
				ra= leitor.nextInt();
				System.out.println("Digite o nome do aluno");
				nome= leitor.next();
				System.out.println("Digite a nota do aluno");
				nota= leitor.nextDouble();
				// Cria um objeto aluno inicializando os atributos com os valores digitados pelo usu�rio
				Aluno aluno = new Aluno(ra,nome,nota);
				// Adiciona esse objeto � lista de alunos
				lista.add(aluno);
				break;
				
			// Op��o 2 - Exibir a lista	
			case 2:
				if (lista.size() == 0)  {
					System.out.println("Lista vazia");
				}
				else {
					/* sem for
					 * System.out.println(lista);
					 */
					/* for sem �ndice
					for (Aluno a : lista) {
						System.out.println(a);
					} 
					*/
					// for com �ndice
					for (int i=0; i < lista.size(); i++) {
						System.out.println(lista.get(i));
					}
				}
				break;
				
			// Op��o 3 - Gravar a lista num arquivo txt	
			case 3:
				if (lista.size() == 0) {
					System.out.println("Lista vazia. N�o h� o que gravar.");
				}
				else {
					gravaLista(lista, false); // Chama o m�todo gravaLista para gravar a lista num arquivo txt
					lista.clear();			  // Limpa a lista
				}
				break;

			// Op��o 4 - Gravar a lista num arquivo csv	
			case 4:
				if (lista.size() == 0) {
					System.out.println("Lista vazia. N�o h� o que gravar.");
				}
				else {
					gravaLista(lista, true);  // Chama o m�todo gravaLista para gravar a lista num arquivo csv
					lista.clear();			  // Limpa a lista
				}
				break;
			
			// Op��o 5 - Ler e exibir arquivo txt
			case 5:
				leExibeArquivo(false);
				break;

			// Op��o 6 - Ler e exibir arquivo csv
			case 6:
				leExibeArquivo(true);
				break;

			// Op��o 7 - Fim
			case 7:
				fim = true;
				break;
				
			// Op��o diferente de 1 a 7	
			default:
				System.out.println("Op��o inv�lida!");
				break;
			}
		}
	}

}
