package application;

import java.util.Scanner;

import entities.SistemaDeArquivos;

public class Main {

	public static void main(String[] args) {
		SistemaDeArquivos memoria = new SistemaDeArquivos(20);
		Scanner sc = new Scanner(System.in);
		int opcao =  0;
		
		do {
			System.out.println("1-Alocaçao Contigua\n2-Alocacao Indexada\n3-Alocaçao Encadeada\n4-Remover Arquivo\n5-Encerrar");
			opcao = sc.nextInt();
			if(opcao<=3) {
				System.out.print("Arquivo: ");
				String arquivo = sc.next();
				System.out.print("\nBlocos: ");
				int blocos = sc.nextInt();
				memoria.menuOperacoes(opcao, arquivo, blocos);
			}
			else if(opcao == 4) {
				System.out.print("Qual arquivo remover: ");
				memoria.removerBlocos(sc.next());
				memoria.exibeMemoria();
			}
		}
		while(opcao!=5);
		System.out.println("Encerrado!!");
		
		sc.close();
	}

}
