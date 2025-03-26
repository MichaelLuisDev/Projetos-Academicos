package entities;

import java.util.ArrayList;
import java.util.List;

//Um vetor para a memoria e outro que armazena as posicoes encadeadas
public class SistemaDeArquivos {
	private Object[] memoria;
	List<Integer> encadeada;
	private int tamanho;

//Inicializa os dois vetores
	public SistemaDeArquivos(int capacidade) {
		this.memoria = new Object[capacidade];
		this.encadeada = new ArrayList<>();
		this.tamanho = 0;
		for (int i = 0; i < capacidade; i++) {
			memoria[i] = "#";
		}
	}

//-----------------------------------------------------------------------------
	public boolean alocacaoContigua(String elemento, int blocosOcupacao) {
		int inicio = encontrarEspacoContiguo(blocosOcupacao);// Verifica se tem espaco contiguo suficiente
		
		if (inicio != -1) {
			for (int i = inicio; i < inicio + blocosOcupacao; i++) {
				memoria[i] = elemento;
				tamanho++;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean alocacaoEncadeada(String elemento, int blocosOcupacao) {
		for (int i = 0; i < memoria.length; i++) {
			// Verifica se tem bloco vazio, se e suficiente e controla os blocos inseridos
			if (memoria[i].equals("#") && blocosOcupacao <= (memoria.length - tamanho) && blocosOcupacao > 0) {
				memoria[i] = elemento;
				encadeada.add(i);
				tamanho++;
				blocosOcupacao--;
			}
			if (blocosOcupacao == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean alocacaoIndexada(String elemento, int blocosOcupacao) {
		// Verifica se o espaço da memoria é suficiente
		if(blocosOcupacao > memoria.length || blocosOcupacao > (memoria.length-tamanho)) {
			return false;
		}
		// Instancia a lista indexada e adiciona os blocos do arquivo na lista
		List<String> indexada = new ArrayList<>();
		for (int i = 0; i < memoria.length; i++) {
			if (memoria[i].equals("#") && memoria.length > 0) {
				while (blocosOcupacao > 0) {
					indexada.add(elemento);
					blocosOcupacao--;
				}
				// Adiciona a lista de blocos no indice x da memoria
				memoria[i] = indexada;
				tamanho++;
				return true;
			}
		}
		return false;
	}

	public int removerBlocos(String nomeArquivo) {
		int blocosRemovidos = 0;
		// Busca o bloco no qual o arquivo a ser removido esta localizado
		for (int i = 0; i < memoria.length; i++) {
			if (memoria[i].equals(nomeArquivo)) {
				memoria[i] = "#";
				tamanho--;
				blocosRemovidos++;
			}
			// Verifica se o indice atual e uma instancia de uma lista, caso a remocao seja
			// de um alocacao indexada
			else if (memoria[i] instanceof List) {
				// Verifica se o arquivo a ser removido e o mesmo que esta na lista
				if (memoria[i].toString().contains(nomeArquivo)) {
					memoria[i] = "#";
					tamanho--;
					blocosRemovidos++;
				}
			}
		}
		return blocosRemovidos;

	}

	private int encontrarEspacoContiguo(int blocosOcupacao) {
		int contagem = 0;
		int inicio = -1;

		for (int i = 0; i < memoria.length; i++) {
			// Inicia a contagem de blocos vazios e seta o inicio possivel do espaco vazio
			if (memoria[i].equals("#")) {
				if (contagem == 0) {
					inicio = i;
				}
				contagem++;
				// Se a contagem bater com a ocupacao de blocos, o espaco vazio encontrado e
				// suficiente
				if (contagem == blocosOcupacao) {
					return inicio;
				}
			} else {
				contagem = 0; // Reinicia a contagem do 0
				inicio = -1;
			}
		}
		return -1;
	}

	public String exibeMemoria() {
		for (int i = 0; i < memoria.length; i++) {
			System.out.print(memoria[i]);
		}
		System.out.println("\nTamanho de memoria preenchida: " + tamanho());
		return null;
	}

	public int tamanho() {
		return tamanho;
	}
	public String msgErro() {
		return "Memoria insuficiente";
	}
	public void menuOperacoes(int opcao,String arquivo,int blocos) {
			if(opcao == 1) {
				alocacaoContigua(arquivo, blocos);
				exibeMemoria();
				
				
			}
			else if(opcao == 2) {
				alocacaoIndexada(arquivo, blocos);
				exibeMemoria();
				
			}
			else if(opcao == 3) {
				alocacaoEncadeada(arquivo, blocos);
				exibeMemoria();
				
			}
			
			else {
				System.out.println("Opcao invalida!!");
			}
	}

}
