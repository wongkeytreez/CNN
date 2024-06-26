
public class Main {
  public static int[][][]images = FileIO.getimages1dINT("training");
  public static  double[][][]denoised =new double[images.length][][];
    public static void main(String[] args){
       
      
      
        cnn.setweights();
        NeuralNetwork network = new NeuralNetwork(cnn.network[cnn.network.length-1][0].length*cnn.network[cnn.network.length-1][0].length*cnn.network[cnn.network.length-1].length, 4, 50,"learnt.txt");
      {
       denoised[0]= FileIO.removeNoise(FileIO.poolimage(images[0],100.0));

        network.addtraining(denoised[0],10000.0);
      }
     {
       denoised[0]= FileIO.removeNoise(FileIO.poolimage(images[1],100.0));

        network.addtraining(denoised[0],0.0);
      }
   network.train();
       System.out.println(NeuralNetwork.network[NeuralNetwork.network.length - 1][0]);
    
      }
    
}