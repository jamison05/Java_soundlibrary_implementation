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


public class Newbuffers extends BufferFactory{

	
	
	
	
	
	
	public float[] mr1 = new float[256];

	
	
	public Newbuffers(int arg0) {
		
		generateBuffer(arg0);
	}
	   
	public Buffer generateBuffer(int arg0) {
		
			   	
			   int iter=0;
			   float[] kls = new float[300];
			   BufferFactory jk;
			   String filename;
			   filename="C:\\Users\\Joshua\\Documents\\Music_Programming\\wavefiles\\wave";
			   String str = Integer.toString(arg0);
			   filename= filename+ str+ ".txt";
			   Path file = Paths.get(filename);

			   try (InputStream in = Files.newInputStream(file);
			    	    BufferedReader reader =
			    	      new BufferedReader(new InputStreamReader(in))) {
			    	    String line = null;
			    	    while ((line = reader.readLine()) != null) {

			    	    	
			    	    	System.out.println(line);
			    	    	float result = Float.parseFloat(line);			
			    	        kls[iter++]= result;
			    	    	
			    	    }
			    	} catch (IOException x) {
			    	    System.err.println(x);}
			    	
			    	
			
			   
			   this.mr1=kls;
				return null;
		
		
		

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
