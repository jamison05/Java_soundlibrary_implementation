package beadsdrumplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;


public class read_playfile {

	
	
	
	
	
	
	public float[][] musr1 = new float[100][4];

	
	public read_playfile(int arg0) {
		
		generatefile(arg0);
	}
	   
	public void generatefile(int arg0) {
		
			   int ison=0;    int isoff1=0;    int ison3=0; 
			   int iter=0;int set=0;int mult=0;
			   float[] kls = new float[300];
			   BufferFactory jk;
			   String filename;
			   filename="mu_fo\\notestatus";
			   String str = Integer.toString(arg0);
			   filename= filename+ str+ ".txt";
			   Path file = Paths.get(filename);

			   try (InputStream in = Files.newInputStream(file);
			    	    BufferedReader reader =
			    	      new BufferedReader(new InputStreamReader(in))) {
			    	    String line = null;
			    	    //line.contains(s)
			    	    //line.substring(beginIndex, endIndex)
			    	    while ((line = reader.readLine()) != null) {

			    	    	
			    	    	System.out.println(line);
			    	    	float result = Float.parseFloat(line);			
			    	        
			    	    	musr1[iter++][0]= result;
			    	    }
			    	} catch (IOException x) {
			    	    System.err.println(x);}
			  
			   iter=0;
			   filename="mu_fo\\notepitch";
			 
			   filename= filename+ str+ ".txt";
			   Path file2 = Paths.get(filename); 	
			  
			   
			   
			   try (InputStream in = Files.newInputStream(file2);
			    	    BufferedReader reader =
			    	      new BufferedReader(new InputStreamReader(in))) {
			    	    String line = null;
			    	    //line.contains(s)
			    	    //line.substring(beginIndex, endIndex)
			    	    while ((line = reader.readLine()) != null) {

			    	    	
			    	    	System.out.println(line);
			    	    	float result = Float.parseFloat(line);			
			    	    	musr1[iter++][1]= result;
			    	    	
			    	    }
			    	} catch (IOException x) {
			    	    System.err.println(x);}
			    
			   
			   iter=0;	
			   filename="mu_fo\\notevelocity";
			   filename= filename+ str+ ".txt";
			   Path file3 = Paths.get(filename);
			 
			   try (InputStream in = Files.newInputStream(file3);
			    	    BufferedReader reader =
			    	      new BufferedReader(new InputStreamReader(in))) {
			    	    String line = null;
			    	    //line.contains(s)
			    	    //line.substring(beginIndex, endIndex)
			    	    while ((line = reader.readLine()) != null) {

			    	    	
			    	    	System.out.println(line);
			    	    	float result = Float.parseFloat(line);			
			    	    	musr1[iter++][2]= result;
			    	    	
			    	    }
			    	} catch (IOException x) {
			    	    System.err.println(x);}
		
			   iter=0;
			   filename="mu_fo\\notetime";
			   filename= filename+ str+ ".txt";
			   Path file4 = Paths.get(filename);
			 
			   try (InputStream in = Files.newInputStream(file4);
			    	    BufferedReader reader =
			    	      new BufferedReader(new InputStreamReader(in))) {
			    	    String line = null;
			    	    //line.contains(s)
			    	    //line.substring(beginIndex, endIndex)
			    	    while ((line = reader.readLine()) != null) {

			    	    	
			    	    	System.out.println(line);
			    	    	float result = Float.parseFloat(line);			
			    	    	musr1[iter++][3]= result;
			    	    	
			    	    }
			    	} catch (IOException x) {
			    	    System.err.println(x);}
		
			   
			   
			   

	}


	


}