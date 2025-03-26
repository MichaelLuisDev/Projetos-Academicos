package application;

import java.io.File;
import java.util.Scanner;

import utils.Utils;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		File arquivo_1 = new File("C:\\temp\\numeros_aleatorios_10.txt");
		File arquivo_2 = new File("C:\\temp\\numeros_aleatorios_5.txt");
		
		//Verifica se os arquivos sao valido ou feijoada
		if (arquivo_1 == null || !arquivo_1.exists() || arquivo_2 == null || !arquivo_2.exists()) {
			System.out.println("Erro: Arquivo não encontrado.");
			sc.close();
			return;
		}
		System.out.println("Estrutura:\n1-Lista\n2-Arvore AVL\n4-Tabela Hash");
		int estrutura = sc.nextInt();
		System.out.println(Utils.menuOperacoes());
		int operacao = sc.nextInt();
		Utils.escolhaOperacoes(estrutura, operacao, arquivo_1, arquivo_2);
		

		sc.close();
	}
}
