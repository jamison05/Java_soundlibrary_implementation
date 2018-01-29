
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleAudioFormat;
import net.beadsproject.beads.data.audiofile.AudioFileType;
import net.beadsproject.beads.data.audiofile.AudioFileWriter;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.RecordToSample;
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
		//Try 4 different sound modulations.  
		float[][] file_holder1 = new float[15][500]; 
		String filename  = "nit_1step2_sound_mod.dat";
		file_holder1[1] = get_fuzzy_strings(filename);
		filename = "nit_1step2_sound_mod.dat";
		file_holder1[2] = get_fuzzy_strings(filename);
		filename = "nit_1step3_sound_mod.dat";
		file_holder1[3] = get_fuzzy_strings(filename);
		filename = "nit_1step4_sound_mod.dat";
		file_holder1[4] = get_fuzzy_strings(filename);	
		filename = "nit_2step2_sound_mod.dat";
		file_holder1[5] = get_fuzzy_strings(filename);
		filename = "nit_2step3_sound_mod.dat";
		file_holder1[6] = get_fuzzy_strings(filename);
		filename = "nit_2step4_sound_mod.dat";
		file_holder1[7] = get_fuzzy_strings(filename);
		
		
		filename = "nit_2step2_sound_mod.dat";
		file_holder1[4] = get_fuzzy_strings(filename);	
		
		filename = "nit_2step3_sound_mod.dat";
		file_holder1[5] = get_fuzzy_strings(filename);
		
		filename = "nit_2step4_sound_mod.dat";
		file_holder1[6] = get_fuzzy_strings(filename);
		
		filename = "nit_3step2_sound_mod.dat";
		file_holder1[7] = get_fuzzy_strings(filename);
		
		filename = "nit_3step3_sound_mod.dat";
		file_holder1[8] = get_fuzzy_strings(filename);
		
		filename = "nit_3step4_sound_mod.dat";
		file_holder1[9] = get_fuzzy_strings(filename);
		
		filename = "nit_4step2_sound_mod.dat";
		file_holder1[10] = get_fuzzy_strings(filename);
		
		filename = "nit_4step3_sound_mod.dat";
		file_holder1[11] = get_fuzzy_strings(filename);
		
		
		// instantiate the AudioContext
		AudioContext ac = new AudioContext();
		
		// load the source sample from a file
		Sample sourceSample = null;
		try
		{
			sourceSample = new Sample("hollow_hit.wav");
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
		
		Glide ms = new Glide(ac, .8f, 20f);
		
    	Glide randomnessValue = new Glide(ac, 80, 1);
		Glide intervalValue = new Glide(ac, 2f, 1);
		Glide grainSizeValue = new Glide(ac, 1000, 1);
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

		
		

		try{
			SampleAudioFormat af = new SampleAudioFormat( 44100.0f, 16, 1, true, true); 

			AudioFileWriter write_audio_rght = null ;
			String filename2="";
			filename2="C:\\Users\\deepj\\workspace\\HashMap_utilityAudio\\src\\kl9newlat.wav";
			
		
			Sample outputSample = new Sample(100000, 2, 44100);
			RecordToSample rts = new RecordToSample(ac, outputSample, RecordToSample.Mode.INFINITE); rts.addInput(ac.out); 
			ac.out.addDependent(rts); 
		
		

			//		Scanner in = new Scanner( System.in); System.out.println(" Press enter to save sound and end the program.");
			//		in.nextLine();

			long startTime = System.currentTimeMillis();
			// Run some code;
			long diff=0;
			long stopTime=0;
			while (diff < 4000){
				stopTime = System.currentTimeMillis();
				diff = stopTime - startTime;
			}		
		
		
			
			rts.pause( true); rts.clip(); outputSample.write(filename2, AudioFileType.WAV); 
			rts.kill();
			System.out.println("Finished recording.");
			}
			catch (Exception e) { e.printStackTrace(); System.exit( 1);
			System.exit(0);
		}
				
		
		
	}

public static float[] get_fuzzy_strings( String filename ){
	
	
	
    String content = null;
    float [] fuzzy_value = new float[200];
    File file = new File(filename); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        
        reader.read(chars);
        content = new String(chars);
        String[] content_ar= content.split(",");
        int it=0;
        for (String value: content_ar){
        	fuzzy_value[it++] = Float.parseFloat(value);
        } 
        it=0;
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if(reader !=null){try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
        }    
    }
  
	return fuzzy_value;
	
	
}



}
