package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.ArvoreAvl;
import entities.ArvoreRubroNegra;
import entities.TabelaHashDuplo;

public class Utils {
	private Utils() {
	}

// Define qual a menor quantidade de chaves entre dois arquivos
	public static <T extends Comparable<T>> T menorChaveArquivo(T elemento1, T elemento2) {
		return (elemento1.compareTo(elemento2) < 0) ? elemento1 : elemento2;
	}

	// Define qual a maior quantidade de chaves entre dois arquivos
	public static <T extends Comparable<T>> T maiorChaveArquivo(T elemento1, T elemento2) {
		return (elemento1.compareTo(elemento2) > 0) ? elemento1 : elemento2;
	}

//Ler arquivo para uma lista
	public static void lerArquivoParaLista(File arquivo, List<? super String> lista) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					lista.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo " + arquivo.getName() + ": " + e.getMessage());
		}
	}

//Ler arquivo para uma arvore AVL
	public static void lerArquivoParaArvoreAVL(File arquivo, ArvoreAvl<? super String> no) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					no.inserirElemento(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo " + arquivo.getName() + ": " + e.getMessage());
		}
	}

//Ler arquivo para uma tabela Hash
	public static void lerArquivoParaTabela(File arquivo, TabelaHashDuplo<? super String> tabela) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					tabela.inserirChave(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo " + arquivo.getName() + ": " + e.getMessage());
		}
	}

//Metodo nao implementado
	public static void lerArquivoParaArvoreRubroNegra(File arquivo, ArvoreRubroNegra<? super String> no) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					no.inserirElemento(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo " + arquivo.getName() + ": " + e.getMessage());
		}
	}

//Metodo que coordena as operacoes com lista
	public static void operacaoLista(int opcao, File arquivo_1, File arquivo_2) {
		File maiorArquivo = Utils.maiorChaveArquivo(arquivo_1, arquivo_2);
		File menorArquivo = Utils.menorChaveArquivo(arquivo_1, arquivo_2);
		List<String> menorLista = new ArrayList<>();
		List<String> maiorLista = new ArrayList<>();
		Utils.lerArquivoParaLista(menorArquivo, menorLista);
		Utils.lerArquivoParaLista(maiorArquivo, maiorLista);

		if (opcao == 1) {
			maiorLista.retainAll(menorLista);
			System.out.println("Encontrado " + maiorLista.toString());
		}
		if (opcao == 2) {
			for (String elemento : menorLista) {
				if (!maiorLista.contains(elemento)) {
					maiorLista.add(elemento);
					System.out.println("Inserido [" + elemento.toString() + "]");
				}
			}
			System.out.println(maiorLista.toString());
		}

		if (opcao == 3) {
			System.out.println("Removido [" + menorLista.removeAll(maiorLista) + "]");
			System.out.println(menorLista);

		}
	}

//Metodo que coordena as operacoes com arvore AVL
	public static void operacaoArvoreAvl(int opcao, File arquivo_1, File arquivo_2) {
		File maiorArquivo = Utils.maiorChaveArquivo(arquivo_1, arquivo_2);
		File menorArquivo = Utils.menorChaveArquivo(arquivo_1, arquivo_2);
		List<String> menorLista = new ArrayList<>();
		Utils.lerArquivoParaLista(menorArquivo, menorLista);
		ArvoreAvl<String> arvoreAvl = new ArvoreAvl<>();
		Utils.lerArquivoParaArvoreAVL(maiorArquivo, arvoreAvl);

		if (opcao == 1) {
			for (String lista : menorLista) {
				if (arvoreAvl.localizarElemento(lista) == true) {
					System.out.println("Encontrado [" + lista + "]");
				}
			}
		}
		if (opcao == 2) {
			for (String lista : menorLista) {
				if (arvoreAvl.localizarElemento(lista) == false) {
					arvoreAvl.inserirElemento(lista);
					System.out.println("Inserido [" + lista + "]");
				}
			}
			arvoreAvl.imprimirEmOrder();
		}
		if (opcao == 3) {
			for (int i = (menorLista.size()) - 1; i >= 0; i--) {
				if (arvoreAvl.localizarElemento(menorLista.get(i)) == true) {
					System.out.println("Removido [" + menorLista.get(i) + "]");
					menorLista.remove(menorLista.get(i));
				}
			}
			// System.out.println(menorLista.toString());

		}
	}

//Metodo que coordena as operacoes com tabela Hash
	public static void operacaoTabelaHash(int opcao, File arquivo_1, File arquivo_2) {
		File maiorArquivo = Utils.maiorChaveArquivo(arquivo_1, arquivo_2);
		File menorArquivo = Utils.menorChaveArquivo(arquivo_1, arquivo_2);
		List<String> menorLista = new ArrayList<>();
		Utils.lerArquivoParaLista(menorArquivo, menorLista);
		TabelaHashDuplo<String> tabelaHash = new TabelaHashDuplo<>();
		Utils.lerArquivoParaTabela(maiorArquivo, tabelaHash);

		if (opcao == 1) {
			for (String lista : menorLista) {
				String chaveEncontrada = tabelaHash.localizarChave(lista);
				if (chaveEncontrada != null && chaveEncontrada.compareTo(lista) == 0) {
					System.out.println("Encontrado [" + lista + "]");
				}
			}
		}
		if (opcao == 2) {
			for (String lista : menorLista) {
				String chaveEncontrada = tabelaHash.localizarChave(lista);
				if (chaveEncontrada == null) {
					tabelaHash.inserirChave(lista);
					System.out.println("Inserido: " + lista);
				}
			}

			tabelaHash.imprimirEmOrdem();
		}
		if (opcao == 3) {
			for (int i = (menorLista.size()) - 1; i >= 0; i--) {
				String chaveEncontrada = tabelaHash.localizarChave(menorLista.get(i));
				if (chaveEncontrada != null && chaveEncontrada.compareTo(menorLista.get(i)) == 0) {
					System.out.println("Removido [" + menorLista.get(i) + "]");
					menorLista.remove(chaveEncontrada);
				}
			}
			System.out.println(menorLista.toString());
		}

	}

// Metodo MENU para coordernar as escolher as estruturas e operacoes da implementacao
	public static void escolhaOperacoes(int estrutura, int operacao, File arquivo_1, File arquivo_2) {
		if (estrutura == 1) {
			operacaoLista(operacao, arquivo_1, arquivo_2);
		}
		if (estrutura == 2) {
			operacaoArvoreAvl(operacao, arquivo_1, arquivo_2);
		}
		if (estrutura == 4) {
			operacaoTabelaHash(operacao, arquivo_1, arquivo_2);
		}
	}

	public static String menuOperacoes() {
		return "Escolha uma operacao a ser feita:\n1-Busca de A em B\n2-Inserir em B os elementos faltantes de A\n3-Remover de A os elementos de B";
	}

}
