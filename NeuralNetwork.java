

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NeuralNetwork {
    public static double[][][] weights;
    public static double[][] network;
    ArrayList<double[][]> trainingdata= new ArrayList<double[][]>();
    ArrayList<Double> trainingoutputs= new ArrayList<Double>();
    String savefile;
    public NeuralNetwork(int inputs, int hiddenLayers, int hiddenLayerSize, String thissavefile) {
   savefile = thissavefile;
    File f = new File(savefile);
   if(!f.exists() ||f.isDirectory()) { 
    weights = new double[hiddenLayers+2][][];
            weights[0] = new double[inputs][hiddenLayerSize];
            for (int i = 0; i < hiddenLayers; i++) {
                weights[i + 1] = new double[hiddenLayerSize][hiddenLayerSize];
            }
            weights[hiddenLayers] = new double[hiddenLayerSize][1];
            weights[hiddenLayers+1] = new double[1][1];
            for (int layer = 1; layer < weights.length; layer++) {
            for (int neuron = 0; neuron < weights[layer].length; neuron++) {
            for (int prevNeuron = 0; prevNeuron < weights[layer - 1].length; prevNeuron++) {
                weights[layer - 1][prevNeuron][neuron] = Math.random();
            }
            }}
     new FileIO().save3DArrayToFile(NeuralNetwork.weights,savefile);
    } 
    }
    public void addtraining(double[][] training,double output){
  trainingoutputs.add(output);
  trainingdata.add(training);
    }
public void train(){
    int iterations = 100; // Number of available CPU cores
    ExecutorService executor = Executors.newFixedThreadPool(1);
    double[] lastloss = new double[trainingdata.size()];
    executor.submit(()->weights = new FileIO().vals3(savefile));
        
        for (int i = 0; i < iterations; i ++) {
             
             Collections.shuffle(trainingdata);
  
             for (int example = 0;example<trainingdata.size();example++ ) {
             final int exampleint = example;
                executor.submit(() -> {
    
                          
                 cnn.run(trainingdata.get(exampleint));
        
        double[] flattened = new double[cnn.network[cnn.network.length-1].length*cnn.network[cnn.network.length-1][0].length*cnn.network[cnn.network.length-1][0][0].length];
        for(int img=0;img<cnn.network[cnn.network.length-1].length;img++){
          for(int x=0;x<cnn.network[cnn.network.length-1][img].length;x++){
           
            System.arraycopy(cnn.network[cnn.network.length-1][img][x], 0, flattened, img*(cnn.network[cnn.network.length-1][img].length*cnn.network[cnn.network.length-1][img].length)+x*cnn.network[cnn.network.length-1][img][0].length, cnn.network[cnn.network.length-1][img][x].length);
          }
        }
              
        
                 run(false,flattened);
                 System.out.println(exampleint+" "+(lastloss[exampleint]-((network[network.length - 1][0]-trainingoutputs.get(exampleint)))));
                 lastloss[exampleint]=(network[network.length - 1][0]-trainingoutputs.get(exampleint));
                 gradientDescent(trainingoutputs.get(exampleint), 0.000000001);
                });}   
        }
        executor.shutdown();
        try {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {}

    executor = null; 
    for (int example = 0;example<trainingdata.size();example++ ) {
             final int exampleint = example;
                
    
                          
                 cnn.run(trainingdata.get(exampleint));
        
        double[] flattened = new double[cnn.network[cnn.network.length-1].length*cnn.network[cnn.network.length-1][0].length*cnn.network[cnn.network.length-1][0][0].length];
        for(int img=0;img<cnn.network[cnn.network.length-1].length;img++){
          for(int x=0;x<cnn.network[cnn.network.length-1][img].length;x++){
           
            System.arraycopy(cnn.network[cnn.network.length-1][img][x], 0, flattened, img*(cnn.network[cnn.network.length-1][img].length*cnn.network[cnn.network.length-1][img].length)+x*cnn.network[cnn.network.length-1][img][0].length, cnn.network[cnn.network.length-1][img][x].length);
          }
        }
              
        
                 run(false,flattened);
                 System.out.println(exampleint+" "+(((network[network.length - 1][0]-trainingoutputs.get(exampleint)))));

                
            }   
    new FileIO().save3DArrayToFile(NeuralNetwork.weights,savefile);   
}
public double sigmoid(double x) {
    return 1 / (1 + Math.exp(-x));
}
    public  void run(Boolean test, double[] inputs) {
        
        network = new double[NeuralNetwork.weights.length][];
        for (int i = 0; i < network.length; i++) {
            network[i] = new double[weights[i].length];
        }

        network[weights.length - 1] = new double[1];
        for(int i = 0;i<inputs.length;i++)network[0][i]=inputs[i];
         
        double[][] clone = Arrays.asList(network).toArray(new double[0][]);
        for (int i = 0; i < network.length - 1; i++) {
            for (int j = 0; j < network[i].length; j++) {
                if(Double.isInfinite(network[i][j])||Double.isNaN(network[i][j])){
                    //clone[network.length - 1][0]*=-1;
                    network=clone;
                    System.exit(j);;
                    continue;}
                //if(Math.random()<0.0000001&&i>0&&!test) continue;
                for (int o = 0; o < network[i + 1].length; o++) {
                    network[i + 1][o] += network[i][j] * weights[i][j][o];
                    
                  //  if( Math.abs(network[i + 1][o])>1000) network[i + 1][o]=1000*( Math.abs(network[i + 1][o])/ network[i + 1][o]);
                }
                 // network[i][j]= Math.max(network[i][j], 0)  ;
                } 
               
            }

        }
    

    public void gradientDescent(double trueOutput, double learningRate) {
    double loss =(trueOutput-network[network.length - 1][0]);

    
    double[][][] gradients = new double[weights.length][][];
   
    for (int layer = weights.length - 1; layer >= 1; layer--) {
        gradients[layer] = new double[weights[layer].length][];
        if (layer == weights.length - 1) {
            gradients[layer] = new double[1][weights[layer - 1].length];
            for (int i = 0; i < gradients[layer][0].length; i++) {
     
                gradients[layer][0][i] =
                network[layer-1][i]*-((loss/(double)Math.pow(10, 9))/trainingoutputs.size());
                           if(Double.isNaN(gradients[layer][0][i]))System.out.println(network[layer-1][i]+" "+loss);

              
                        }
            continue;
            
        }
        for (int neuron = 0; neuron < weights[layer].length; neuron++) {
            gradients[layer][neuron] = new double[weights[layer - 1].length];
            double delta = 0;
            int i=0;
            for (double[] next : gradients[layer + 1]) {
               
               
                delta += (Double.isNaN(next[neuron]/network[layer][neuron])?0:next[neuron]/network[layer][neuron])*weights[layer][neuron][i];
                if(Double.isNaN(delta))System.out.println((next[neuron]+" "+network[layer][neuron]+" "+(next[neuron]/network[layer][neuron])+" "+weights[layer][neuron][i]));
                i++;
            }
            for (int o = 0; o < weights[layer - 1].length; o++) {

                 gradients[layer][neuron][o] =
                           
                         
                          delta*network[layer-1][o]
                           ;
                           
                           if(Double.isNaN(gradients[layer][neuron][o]))System.out.println(network[layer-1][o]);
                        

            }
        }
    }
                     
//System.out.println(loss);
    updateWeights(gradients, learningRate*(Math.abs(loss)/ (double) Math.pow(10,20)));
}


    public void updateWeights(double[][][] gradients, double learningRate) {
      //cnn.updateWeights(gradients[1]);
        

             
      
        for (int layer = 1; layer < weights.length; layer++) {
            for (int neuron = 0; neuron < weights[layer].length; neuron++) {
                for (int prevNeuron = 0; prevNeuron < weights[layer - 1].length; prevNeuron++) {
                   // System.out.print(0/3);
                  // System.out.println(gradients[layer][neuron][prevNeuron]);
                // if(weights[layer][prevNeuron][neuron]==0)weights[layer][prevNeuron][neuron]=0.0001;
                 //System.out.println(gradients[layer][neuron][prevNeuron]);
           weights[layer - 1][prevNeuron][neuron] -= (learningRate * gradients[layer][neuron][prevNeuron]);
                    if(Double.isNaN(weights[layer - 1][prevNeuron][neuron])){System.out.print(gradients[layer][neuron][prevNeuron]);System.exit(0);}
                }
            }
        }
        
    }


}
