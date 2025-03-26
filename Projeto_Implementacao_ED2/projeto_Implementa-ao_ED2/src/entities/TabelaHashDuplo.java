package entities;

public class TabelaHashDuplo<T extends Comparable<T>> {
	private ChaveHash<T>[] chaves;
	private Integer tamanho = 521;
	private int qtdElementos = 0;

	@SuppressWarnings("unchecked")
	public TabelaHashDuplo() {
		chaves = (ChaveHash<T>[]) new ChaveHash<?>[tamanho];

	}

	@SuppressWarnings("unchecked")
	public TabelaHashDuplo(Integer cap) {
		if (cap == null || cap < 0) {
			throw new NumberFormatException("Entrada precisa ser uma inteiro");
		}
		chaves = (ChaveHash<T>[]) new ChaveHash<?>[cap];

	}

//===================================================================================
//metodos publicos
	public boolean inserirChave(T elemento) {
		if (elemento == null) {
			throw new IllegalArgumentException("Chave nao pode ser nula");
		}
		return inserirElemento(elemento);
	}

	public boolean removerChave(T elemento) {
		if (elemento == null) {
			throw new IllegalArgumentException("Chave nao pode ser nula");
		}
		return removerElemento(elemento);
	}

	public T localizarChave(T elemento) {
		if (elemento == null) {
			throw new IllegalArgumentException("A chave nao pode ser null");
		}
		return localizarElemento(elemento);
	}

	public void imprimirEmOrdem() {
		for (int i = 0; i < chaves.length; i++) {
			// Verifica se a posição não é null antes de tentar acessar o elemento
			if (chaves[i] != null && chaves[i].getElemento() != null) {
				System.out.println("[" + (i + 1) + "] =  " + chaves[i].getElemento() + "\n");
			}
		}
	}

//Funcoes para hash duplo h2(k) = i*(k%7); 
	private int hash(T elemento) {
		return Math.abs(elemento.hashCode() % tamanho);
	}

	private int hash2(T elemento) {
		return Math.abs(elemento.hashCode() % 7);
	}

//Inserir chave
	private boolean inserirElemento(T elemento) {
	    if (qtdElementos >= tamanho * 0.2) { // Verifica fator de carga
	        redimensionarTabela(proximoPrimo(tamanho * 2)); // Redimensiona para um número primo
	    }

	    int index = hash(elemento);
	    if (chaves[index] == null) { // Inserção direta se não houver colisão
	        chaves[index] = new ChaveHash<>(elemento);
	        qtdElementos++;
	        return true;
	    }

	    int hash2 = hash2(elemento);
	    if (hash2 == 0) hash2 = 1; // Garante que não seja zero

	    int colisoes = 1;
	    while (colisoes < tamanho) {
	        int novoIndex = (index + colisoes * hash2) % tamanho;
	        if (chaves[novoIndex] == null) { // Encontra posição vazia
	            chaves[novoIndex] = new ChaveHash<>(elemento);
	            qtdElementos++;
	            return true;
	        }
	        colisoes++;
	        System.out.println(colisoes);
	    }
	    
	    return false; // Falha ao inserir (tabela cheia)
	}


//Remover chave
	private boolean removerElemento(T elemento) {
		int index = hash(elemento);
		int operacoes = 0;

		while (operacoes < tamanho) {
			int newIndex = (index + operacoes * hash2(elemento)) % tamanho;
			if (chaves[newIndex] == null || chaves[newIndex].getElemento() == null) {
				return false;
			}
			if (chaves[newIndex].getElemento().compareTo(elemento) == 0) {
				chaves[newIndex].setElemento(null);
				qtdElementos--;

				if (qtdElementos > 0 && qtdElementos <= tamanho * 0.25) { // Reduz pela metade se necessário
					redimensionarTabela(tamanho / 2);
				}
				return true;
			}
			operacoes++;
		}
		return false;
	}

//Localizar chave
	private T localizarElemento(T elemento) {
		int index = hash(elemento);
		int operacoes = 0;

		while (operacoes < tamanho) {
			int newIndex = (index + operacoes * hash2(elemento)) % tamanho;
			if (chaves[newIndex] == null || chaves[newIndex].getElemento() == null) {
				return null; // Elemento não encontrado
			}
			if (chaves[newIndex].getElemento().compareTo(elemento) == 0) {
				return chaves[newIndex].getElemento();
			}
			operacoes++;
		}
		return null;
	}

//Metodo para redimensionar a tabela
	private void redimensionarTabela(int novoTamanho) {
		novoTamanho = proximoPrimo(novoTamanho);
		@SuppressWarnings("unchecked")
		ChaveHash<T>[] novaTabela = (ChaveHash<T>[]) new ChaveHash<?>[novoTamanho];

		// Guarda a referência da tabela antiga e reinicializa
		ChaveHash<T>[] tabelaAntiga = chaves;
		this.chaves = novaTabela;
		this.tamanho = novoTamanho;
		this.qtdElementos = 0; // Será atualizado ao reinserir

		// Reinsere os elementos corretamente
		for (ChaveHash<T> chave : tabelaAntiga) {
			if (chave != null && chave.getElemento() != null) {
				inserirElemento(chave.getElemento());
			}
		}
	}

//Metodo encontrar primo para redimensionar
	private int proximoPrimo(int num) {
		while (!ehPrimo(num)) {
			num++;
		}
		return num;
	}

	private boolean ehPrimo(int num) {
		if (num < 2)
			return false;
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}

}
