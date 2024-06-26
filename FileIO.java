

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.w3c.dom.Node;

public class FileIO extends JPanel{
    
          public double[][][][] vals4(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<double[][][]> array4d = new ArrayList<>();
            ArrayList<double[][]> array = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
              
                if(line.equals("[")){array= new ArrayList<>();continue; }
                if(line.equals("]")){array4d.add(array.toArray(new double[0][][]));continue; }
                
                String[] values = line.substring(3, line.length() - 3).split("] \\[");
                double[][] row = new double[values.length][];
                for (int i = 0; i < values.length; i++) {
                   
                    String[] strings = values[i].split(" ");
                     row[i]= new double[strings.length];
                    for(int j = 0; j < strings.length; j++)
                    row[i][j] = Double.parseDouble(strings[j]);
                }
                array.add(row);
            }
            return array4d.toArray(new double[0][][][]);
          
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public double[][][] vals3(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<double[][]> array = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {

                String[] values = line.substring(3, line.length() - 3).split("] \\[");
                double[][] row = new double[values.length][];
                for (int i = 0; i < values.length; i++) {
                   
                    String[] strings = values[i].split(" ");
                     row[i]= new double[strings.length];
                    for(int j = 0; j < strings.length; j++)
                    row[i][j] = Double.parseDouble(strings[j]);
                }
                array.add(row);
            }
            return array.toArray(new double[0][][]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public static double[][][][] getimages3d(String path){
        
        File[] directory = new File(path).listFiles();
        double[][][][]rgb =new double[directory.length][][][];
        for(int i =0; i<directory.length;i++){
            try {
            BufferedImage image = ImageIO.read(directory[i]);
            rgb[i]= new double[image.getWidth()][image.getHeight()][3];
            for(int x=0;x<image.getWidth();x++)for(int y=0;y<image.getHeight();y++){
                Color color = new Color(image.getRGB(x, y));
                rgb[i][x][y][0]= color.getRed();
            rgb[i][x][y][1]= color.getBlue();
        rgb[i][x][y][2]= color.getGreen();
    }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
       
        return rgb;
    }
        public static double[][][] getimages1d(String path){
        
        File[] directory = new File(path).listFiles();
        double[][][]luminance =new double[directory.length][][];
        for(int i =0; i<directory.length;i++){
            try {
            BufferedImage image = ImageIO.read(directory[i]);
            luminance[i]= new double[image.getWidth()][image.getHeight()];
            for(int x=0;x<image.getWidth();x++)for(int y=0;y<image.getHeight();y++){
                Color color = new Color(image.getRGB(x, y));
                luminance[i][x][y]=  0.2126 * color.getRed() + 0.7152 *  color.getGreen() + 0.0722 *  color.getBlue();

    }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    
        return luminance;
    }
    public static int[][][] getimages1dINT(String path){
        
        File[] directory = new File(path).listFiles();
        int[][][]luminance =new int[directory.length][][];
        for(int i =0; i<directory.length;i++){
            try {
            BufferedImage image = ImageIO.read(directory[i]);
            luminance[i]= new int[image.getWidth()][image.getHeight()];
            for(int x=0;x<image.getWidth();x++)for(int y=0;y<image.getHeight();y++){
                Color color = new Color(image.getRGB(x, y));
                luminance[i][x][y]=  (int)(0.2126 * color.getRed() + 0.7152 *  color.getGreen() + 0.0722 *  color.getBlue());

    }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    
        return luminance;
    }
    public  double[][] vals2(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<double[]> array = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.substring(1, line.length() - 1).split(" ");
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                   
                    
                     
                    
                    row[i] = Double.parseDouble(values[i]);
                }
                array.add(row);
            }
            return array.toArray(new double[0][]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
       
    
    public void save3DArrayToFile(double[][][] array, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double[][] row : array) {

                writer.write("[ ");
                for (double elements[] : row) {
                    writer.write("[");
                    for (double element : elements) 
                    writer.write(element + " ");
                    writer.write("] ");
                }
                writer.write("]\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public void save4DArrayToFile(double[][][][] d4, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double[][][] array : d4) {writer.write("[\n"); 
                for (double[][] row : array) {
                
                writer.write("[ ");
                for (double elements[] : row) {
                    writer.write("[");
                    for (double element : elements) 
                    writer.write(element + " ");
                    writer.write("] ");
                }
                writer.write("]\n");
            }
            writer.write("]\n");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static double[][] poolimage(double[][] image,double size){
        double[][] newimage = new double[(int)size][(int)size];
       
        double oriimgx=0;double oriimgy=0;

        for(int x=0;x<size;x++){
       
            oriimgy=0;
            for(int y=0;y<size;y++){
               
            int light =0;
            
            for(int minix=0;minix<(int)(Math.ceil((x+1)*(image.length/size)) - oriimgx);minix++)
            for(int miniy=0;miniy<(int)(Math.ceil((y+1)*(image.length/size)) - oriimgy);miniy++){
              
               
                light += image[(int)oriimgx+minix][(int)oriimgy+miniy];
            
        }
       
            newimage[x][y] = Math.min(light/((int)(Math.ceil((x+1)*(image.length/size)) - oriimgx)*(int)(Math.ceil((y+1)*(image.length/size)) - oriimgy)),255);
            
    oriimgy+=(image.length/size);
    }
    oriimgx+=(image.length/size);
}
  
        return newimage;

    }
    public static double[][] poolimage(int[][] image,double size){
        double[][] newimage = new double[(int)size][(int)size];
       
        double oriimgx=0;double oriimgy=0;

        for(int x=0;x<size;x++){
       
            oriimgy=0;
            for(int y=0;y<size;y++){
               
            int light =0;
            
            for(int minix=0;minix<(int)(Math.ceil((x+1)*(image.length/size)) - oriimgx);minix++)
            for(int miniy=0;miniy<(int)(Math.ceil((y+1)*(image.length/size)) - oriimgy);miniy++){
              
               
                light += image[(int)oriimgx+minix][(int)oriimgy+miniy];
            
        }
       
            newimage[x][y] = Math.min(light/((int)(Math.ceil((x+1)*(image.length/size)) - oriimgx)*(int)(Math.ceil((y+1)*(image.length/size)) - oriimgy)),255);
            
    oriimgy+=(image.length/size);
    }
    oriimgx+=(image.length/size);
}
  
        return newimage;

    }
    public static double[][] removeNoise(double[][] image){
        double[][] outputimage= new double[image.length][image[0].length];
       
        for(int x=0;x<image.length;x++)for(int y=0;y<image[0].length;y++){
            double light=0;
            for(int minix=-1;minix<2;minix++){if(minix+x<0||minix+x>=image.length )continue;
            for(int miniy=-1;miniy<2;miniy++){if(miniy+y<0||miniy+y>=image[0].length )continue;
            light+= image[x+minix][y+miniy]*(((minix==0&&0==miniy)?4:((Math.abs(minix)+Math.abs(miniy))))/4);}}
            outputimage[x][y]= light;
           
        }

        return outputimage;
    }
}