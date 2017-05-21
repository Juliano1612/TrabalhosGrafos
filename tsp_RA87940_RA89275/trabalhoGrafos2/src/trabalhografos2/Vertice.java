/**
 * @authores Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 *              RA89740                             RA89275
 */
package trabalhografos2;

public class Vertice {
    public Vertice pai;
    public int descoberta;
    public int finalizado;
    public int id;

    public Vertice(int id) {
        this.id = id;
        this.pai = null;
        this.descoberta = 0;
        this.finalizado = 0;
    }
    
    
}
