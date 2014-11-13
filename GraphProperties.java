/* TODO: OPTIMIZATIONS:
 * Store eccentrities in an array and loop through the array, instead of recomputing the eccentricities
 * over and over again
 * 
 * Initialize array with -1's == G.V()
 * 
 * Also, store the radius as a constant so it isn't being recalculated each time through the loop
 */ 


// Initialization of Graph Properties Class
public class GraphProperties{
  private Graph G;
  int[] dists;
  int[] eccs;
  
  // GraphProperties constructor
  public GraphProperties(Graph G){
    
    // Use CC.java to ensure that all verticies in the graph are connected
    // Throw an exception if they are not
    CC isConnected = new CC(G);
    if(isConnected.count() != 1){
      throw new IllegalArgumentException();
    }
    // Init dists and eccs with their respective values to be used in field calculations
    // dists: array of distances from given node to each vertex in the graph
    dists = new int[G.V()];
    
    // eccs: array of eccentricities for each vertex in the graph
    eccs = new int[G.V()];
    
    this.G = G;
  }
  
  /*
   * ECCENTRICITY
   * 
   * shortest path to the furthest node from the given node
   */
  public int eccentricity(int v){
    
    BreadthFirstPaths bfp = new BreadthFirstPaths(G, v);
    
    // populate each element in eccs with the distances of each vertex
    for(int i = 0; i < G.V(); i++){
      dists[i] = bfp.distTo(i);
    }
    
    // find the largest element in the array, which will represent the eccentricity of the graph
    int ecc = dists[0];
    for(int i = 1; i < dists.length; i++){
      if(dists[i] > ecc){
        ecc = dists[i];
      }
    }
    return ecc;
  }
  
  /*
   * RADIUS
   * 
   * smallest eccentricity of any vertex in the graph
   */
  public int radius(){
    
    // Populate the array with eccentricities
    for(int i = 0; i < G.V(); i++){
      eccs[i] = eccentricity(i);
    }
    
    // Find the smallest element in the array, which will be the radius
    int radi = eccs[0];
    for(int i = 1; i < eccs.length; i++){
      if(eccs[i] < radi){
        radi = eccs[i];
      }
    }
    return radi;
  }
  
  /*
   * DIAMETER
   * 
   * largest eccentricity of any vertex in the graph
   */
  public int diameter(){
    // Initialize array of the eccentricities of each vertex in the graph
    
    // Populate the array with eccentricities
    for(int i = 0; i < G.V(); i++){
      eccs[i] = eccentricity(i);
    }
    
    // Find the smallest element in the array, which will be the radius
    int diam = eccs[0];
    for(int i = 1; i < eccs.length; i++){
      if(eccs[i] > diam){
        diam = eccs[i];
      }
    }
    return diam;
  }
  
  /*
   * CENTER
   * 
   * vertex whose eccentricity is the radius of the graph
   */
  public int center(){
    
    // Store value of radius of graph as a constant
    int rad = radius();
    
    // Initialize center to -1
    int ctr = -1;
    
    // See if the eccentricity of any vertex matches this value and return that value if it does
    for(int i = 0; i < G.V(); i++){
      if(eccentricity(i) == rad){
        ctr = i;
        break;
      }
    }
    return ctr;
  }
  
  public static void main(String[] args){
  In in = new In(args[0]);
  Graph G = new Graph(in);
  GraphProperties gp = new GraphProperties(G);
  
  System.out.println("diameter: " + gp.diameter());

  System.out.println("radius: " + gp.radius());

  System.out.println("center: " + gp.center());
  }
}