/**
 * @authores Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 *              RA89740                             RA89275
 */
package trabalhografos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrabalhoGrafos {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        /*Função principal que realiza a leitura do arquivo de entrada, identifica de que tipo de grafo se trata
        e exibe as opções de execução que o usuário pode realizar sobre o grafo de entrada*/

        int numeroVertices, numeroArestas;
        boolean direcionado, ponderado;

        FileReader arq;
        BufferedReader lerArq;
        String linha;

        Grafo grafo;

        int opcao = 0;
        String nomeArq;
        System.out.println("\t***PRIMEIRO TRABALHO DE GRAFOS***");
        System.out.println("Desenvolvido por: Juliano C. C. Tavares & Kevin L. R. M. Silva");
        System.out.printf("\nInforme o caminho do arquivo que contém o grafo: ");
        Scanner ler = new Scanner(System.in);
        nomeArq = ler.nextLine();

        arq = new FileReader(nomeArq);
        lerArq = new BufferedReader(arq);
        linha = lerArq.readLine(); // lê primeira linha do arquivo -> num de vertices
        numeroVertices = Integer.parseInt(linha); //transforma em int pra salvar na variavel
        grafo = new Grafo(numeroVertices);
        linha = lerArq.readLine(); //lê segunda linha -> num de arestas
        numeroArestas = Integer.parseInt(linha);
        linha = lerArq.readLine(); //lê terceira linha -> direcionado ou não
        direcionado = !linha.equals("0");
        linha = lerArq.readLine(); //começa a ler o grafo
        String[] auxLeitura = linha.split(" "); //divide a string toda vez que achar um espaço
        ponderado = auxLeitura.length != 2;

        do {
            if (direcionado && ponderado) {
                grafo.matrizAdj[Integer.parseInt(auxLeitura[0])][Integer.parseInt(auxLeitura[1])] = Integer.parseInt(auxLeitura[2]);
            } else if (direcionado && !ponderado) {
                grafo.matrizAdj[Integer.parseInt(auxLeitura[0])][Integer.parseInt(auxLeitura[1])] = 1;
            } else if (!direcionado && ponderado) {
                grafo.matrizAdj[Integer.parseInt(auxLeitura[0])][Integer.parseInt(auxLeitura[1])] = Integer.parseInt(auxLeitura[2]);
                grafo.matrizAdj[Integer.parseInt(auxLeitura[1])][Integer.parseInt(auxLeitura[0])] = Integer.parseInt(auxLeitura[2]);
            } else {
                grafo.matrizAdj[Integer.parseInt(auxLeitura[0])][Integer.parseInt(auxLeitura[1])] = 1;
                grafo.matrizAdj[Integer.parseInt(auxLeitura[1])][Integer.parseInt(auxLeitura[0])] = 1;
            }
            linha = lerArq.readLine();
            if (linha != null) {
                auxLeitura = linha.split(" ");
            }
        } while (linha != null);

        arq.close();

        int idVertice1, idVertice2;

        do {
            System.out.printf("\n\nEscolha o que deseja fazer\n[1] - Exibir Dados do Grafo\n[2] - Executar Busca em Largura\n[3] - Executar Busca em profundidade\n[4] - Achar Componentes Fortemente Conectados\n[5] - Achar Caminho Mínimo entre Dois Vertices Usando Bellman-Ford\n[0] - Sair\n>");
            opcao = ler.nextInt();
            switch (opcao) {
                case 1:
                    System.out.printf("\nNumero de vertices: " + numeroVertices + "\n");
                    System.out.println("Numero de arestas: " + numeroArestas);
                    if (direcionado) {
                        System.out.println("\nGrafo direcionado");
                    } else {
                        System.out.println("Grafo não direcionado");
                    }
                    if (ponderado) {
                        System.out.println("Grafo ponderado");
                    } else {
                        System.out.println("Grafo não ponderado");
                    }
                    System.out.println("Matriz de Adjacentes (0 indica sem ligação e > 0 indica o peso da aresta) ");
                    grafo.printMatriz(grafo.matrizAdj);
                    break;
                case 2:
                    System.out.printf("\n***Busca em Largura***");
                    do {
                        System.out.printf("\nDigite o id do vertice que deseja iniciar a busca: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < numero de vertices!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    grafo.dfs(idVertice1);
                    grafo.printRaizNiveis();
                    break;
                case 3:
                    System.out.printf("\n***Busca em Profundidade***");
                    do {
                        System.out.printf("\nDigite o id do vertice que deseja iniciar a busca: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < numero de vertices!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    grafo.bfs(idVertice1);
                    grafo.printRaizNiveis();
                    break;
                case 4:
                    System.out.printf("\n***Componentes Fortemente Conectados***");
                    do {
                        System.out.printf("\nDigite o id do vertice que deseja iniciar a busca: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < numero de vertices!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    grafo.strongly_connected_components(idVertice1);
                    break;
                case 5:
                    System.out.printf("\n***Caminho Mínimo Entre Dois Vértices***");
                    do {
                        System.out.printf("\nDigite o id do vertice 1: ");
                        idVertice1 = ler.nextInt();
                        if (idVertice1 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < numero de vertices!");
                        }
                    } while (idVertice1 >= numeroVertices);
                    do {
                        System.out.printf("\nDigite o id do vertice 2: ");
                        idVertice2 = ler.nextInt();
                        if (idVertice2 >= numeroVertices) {
                            System.out.println("\tO numero informado deve estar 0 <= id < numero de vertices!");
                        }
                    } while (idVertice2 >= numeroVertices);
                    grafo.caminhoMinimo(idVertice1, idVertice2);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.printf("\nDigite um valor válido!");
            }
        } while (opcao != 0);
    }

}
