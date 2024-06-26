import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class cnn {
    public static double[][][][] network;
    public static double[][][][] weights;
    public static void run(double[][]image){

         for (int i = 0; i < network.length; i++) {
                network[i] = new double[network[i].length][network[i][0].length][network[i][0][0].length];
            }
     System.arraycopy( FileIO.poolimage(image,100.0), 0, network[0][0], 0, network[0][0].length);
     
     for(int layer=0;layer<weights.length;layer++){
        for(int thisimg=0;thisimg<network[layer].length;thisimg++){
            

                                       double[][] pooled = FileIO.poolimage(network[layer][thisimg], network[layer+1][0].length);
            //double[][] output = new double[pooled.length][pooled.length];
           
            for(int nextimg=0;nextimg<weights[layer].length/network[layer].length;nextimg++){
            for(int x=0;x<pooled.length;x++)for(int y=0;y<pooled[0].length;y++){

                for(int minix=-1;minix<2;minix++)if(x+minix>-1&&x+minix<pooled.length)for(int miniy=-1;miniy<2;miniy++)if(y+miniy>-1&&y+miniy<pooled[0].length)
                 network[layer+1][thisimg*(weights[layer].length/network[layer].length)+nextimg][x][y]+= pooled[x+minix][y+miniy]*weights[layer][thisimg*(weights[layer].length/network[layer].length)+nextimg][minix+1][miniy+1];
              
                 
          }
     
          // output = new double[pooled.length][pooled.length];

            }
        }
      }


    }
    public static void setweights(){
        String savefile ="cnnWeights.txt"; 
        

        int[] imagesper = {1,32,32} ;
        int[] layersize = {100,80,80};
         File f = new File(savefile);
          network = new double[imagesper.length][][][];
         for (int i = 0; i < imagesper.length; i++) {
                network[i] = new double[imagesper[i]][layersize[i]][layersize[i]];
            }
   if(!f.exists() ||f.isDirectory()) { 
           
           
            weights = new double[imagesper.length-1][][][];
            for (int i = 0; i < imagesper.length-1; i++) {
                weights[i] = new double[imagesper[i+1]][3][3];
            }
            
       
            for (int layer = 0; layer < weights.length; layer++) {
            for (int image = 0; image < weights[layer].length; image++) {
            for (int x = 0; x < weights[layer][image].length; x++) {
                for (int y= 0; y < weights[layer][image][x].length; y++)
                weights[layer][image][x][y] = Math.random();
            }
            }}
     new FileIO().save4DArrayToFile(weights,savefile);
    } 
     weights = new FileIO().vals4("cnnWeights.txt");
    }
    public static void updateWeights(double[][] loss2d){


        double[] flatloss = new double[loss2d[0].length];
        double[][] gradient = new double[weights.length][];
     for(int node=0;node<loss2d.length;node++) 
        for(int input =0;input<loss2d[node].length;input++)flatloss[input]+=((Double.isNaN(loss2d[node][input]/NeuralNetwork.network[0][input]))?0:(loss2d[node][input]/NeuralNetwork.network[0][input]))*NeuralNetwork.weights[0][input][node];
    
        //  gradient[weights.length-1] =
     for(int image =0;image<flatloss.length/(network[network.length-1][0].length*network[network.length-1][0][0].length);image++){
        for(int x=0;x<network[network.length-1][0].length;x++){
          //
            for(int y=0;y<network[network.length-1][0][0].length;y++){
            double loss =flatloss[image*(network[network.length-1][0].length*network[network.length-1][0][0].length)+x*network[network.length-1][0].length+y]/(double)Math.pow(10, 3);
                   
         

            for(int wx=0;wx<weights[weights.length-1][image].length;wx++)if(wx+x-1>-1&& wx+x-1<weights[weights.length-1][0].length)for(int wy=0;wy<weights[weights.length-1][image][0].length;wy++)if(wy+y-1>-1&& wy+y-1<weights[weights.length-1][0][0].length)
        
            { 
            weights[weights.length-1][image][wx][wy] 
            -= 
            loss*network[network.length-2][(image)*(network[network.length-2].length/network[network.length-1].length)][x+wx-1][y+wy-1];
    }
        }}

  }
    //  for(int layer =weights.length-2;layer>=0;layer--){
    
    // }
    //System.exit(0);
   new FileIO().save4DArrayToFile(cnn.weights,"cnnWeights.txt");

     }

}
