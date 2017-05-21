/**
 * @authores Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 *              RA89740                             RA89275
 */
package trabalhografos2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrabalhoGrafos2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        int numeroVertices, numeroArestas;

        FileReader arq;
        BufferedReader lerArq;
        String linha;

        Grafo grafo;

        int opcao = 0;
        String nomeArq;
        System.out.println("\t***SEGUNDO TRABALHO DE GRAFOS***");
        System.out.println("Desenvolvido por: Juliano C. C. Tavares & Kevin L. R. M. Silva");
        System.out.printf("\nInforme o caminho do arquivo que contém o grafo completo: ");
        Scanner ler = new Scanner(System.in);
        nomeArq = ler.nextLine();

        arq = new FileReader(nomeArq);
        lerArq = new BufferedReader(arq);
        linha = lerArq.readLine(); // lê primeira linha do arquivo -> num de vertices
        numeroVertices = Integer.parseInt(linha); //transforma em int pra salvar na variavel
        grafo = new Grafo(numeroVertices);
        linha = lerArq.readLine(); //lê segunda linha -> num de arestas
        numeroArestas = Integer.parseInt(linha);
        if(numeroArestas < ((numeroVertices*(numeroVertices-1)))/2){
            System.out.println("O Grafo não é completo... Não é possível executar os algoritmos!");
            System.exit(0);
        }
        linha = lerArq.readLine(); //começa a ler o grafo
        String[] auxLeitura = linha.split(" "); //divide a string toda vez que achar um espaço
        if (auxLeitura.length == 2) {
            System.out.println("O Grafo é não ponderado... Não é possível executar os algoritmos!");
            System.exit(0);
        }
        do {
            grafo.matrizAdj[Integer.parseInt(auxLeitura[0])][Integer.parseInt(auxLeitura[1])] = Integer.parseInt(auxLeitura[2]);
            grafo.matrizAdj[Integer.parseInt(auxLeitura[1])][Integer.parseInt(auxLeitura[0])] = Integer.parseInt(auxLeitura[2]);
            linha = lerArq.readLine();
            if (linha != null) {
                auxLeitura = linha.split(" ");
            }
        } while (linha != null);

        arq.close();

        int idVertice1;

        do {
            System.out.printf("\n\nEscolha o que deseja fazer\n[1] - Exibir Dados do Grafo\n[2] - Buscar Ciclo Hamiltoniano Baseado em Árvore Geradora Mínima\n[3] - Buscar Ciclo Hamiltoniano Usando Heurística do Vizinho Mais Próximo\n[0] - Sair\n>");
            opcao = ler.nextInt();
            switch (opcao) {
                case 1:
                    System.out.printf("\nNumero de vertices: " + numeroVertices + "\n");
                    System.out.println("Numero de arestas: " + numeroArestas);
                    System.out.println("Matriz de Adjacentes (0 indica sem ligação e > 0 indica o peso da aresta) ");
                    grafo.printMatriz(grafo.matrizAdj);
                    break;
                case 2:
                    System.out.printf("\n***Buscar Ciclo Hamiltoniano Baseado em Árvore Geradora Mínima***");
                    do {
                        System.out.printf("\nDigite o id do vertice que deseja iniciar a busca: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < " + numeroVertices +"!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    grafo.cicloHam(idVertice1);
                    break;
                case 3:
                    System.out.printf("\n***Buscar Ciclo Hamiltoniano Usando Heurística do Vizinho Mais Próximo***");
                    do {
                        System.out.printf("\nDigite o id do vertice que deseja iniciar a busca: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < " + numeroVertices +"!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    grafo.tsp_nn(idVertice1);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.printf("\nDigite um valor válido!");
            }
        } while (opcao != 0);
    }
}
