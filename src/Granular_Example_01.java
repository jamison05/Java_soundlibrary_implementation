
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;
import net.beadsproject.beads.ugens.WavePlayer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class Granular_Example_01
{
	public static void main(String[] args)
	{
		float[][] file_holder1 = new float[10][500]; 
		
		
		String filename  = "temp";
	    String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){reader.close();}
	    }
	  
		
		// instantiate the AudioContext
		AudioContext ac = new AudioContext();
		
		// load the source sample from a file
		Sample sourceSample = null;
		try
		{
			sourceSample = new Sample("FullerUp.wav");
		}
		catch(Exception e)
		{
			/*
			 * If the program exits with an error message,
			 * then it most likely can't find the file
			 * or can't open it. Make sure it is in the
			 * root folder of your project in Eclipse.
			 * Also make sure that it is a 16-bit,
			 * 44.1kHz audio file. These can be created
			 * using Audacity.
			 */
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
		
		
		// instantiate a GranularSamplePlayer
		GranularSamplePlayer gsp = new GranularSamplePlayer(ac, sourceSample);
		
		// tell gsp to loop the file
		gsp.setLoopType(SamplePlayer.LoopType.LOOP_BACKWARDS);
		
		// set the grain size to a fixed 10ms
		gsp.setGrainSize(new Static(ac, 1000f));
		gsp.setGrainInterval(new Static (ac, 300f));
	    gsp.setPosition(new Static (ac, 100f));
//		 gsp.setPitch(pitchValue);
//		
		WavePlayer me = new WavePlayer(ac,500f, Buffer.NOISE);
		
		Glide ms = new Glide(ac, 1f, 20f);
		
    	Glide randomnessValue = new Glide(ac, 80, 1);
		Glide intervalValue = new Glide(ac, 2f, 1);
		Glide grainSizeValue = new Glide(ac, 100, 1);
		Glide positionValue = new Glide(ac, 50000, 1);
		Glide pitchValue = new Glide(ac, .5f, 1);

		gsp.setPitch(ms);
		// tell gsp to behave somewhat randomly
		Panner panner;
		
		WavePlayer panLFO = new WavePlayer(ac,1f, Buffer.SINE);//Do not set the frequency below 1. 
		//Do not set the frequency below 1. 
//     	 gsp.setRandomness(randomnessValue);
//		 gsp.setGrainInterval(intervalValue);
//		 gsp.setGrainSize(grainSizeValue);
//		 gsp.setPosition(positionValue);
//		 gsp.setPitch(pitchValue);

		panner = new Panner(ac, panLFO);
		panner.addInput(gsp);
		
		// connect gsp to ac
		ac.out.addInput(panner);
		
		// begin audio processing
		
		
		ac.start();

	}
}
