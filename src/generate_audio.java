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

public class Generate_audio extends Granular_Example_01{
	
	
	AudioContext ac = new AudioContext();
	
	
	public void generate_audio1 (int audio_input, String audio_save_file){
		

		float xuee = 0;
		xuee= file_holder1[1][1];
		switch  (audio_input){
		
			case 1:
				set_Source("bassdruum.wav");
				break;
			case 2:
				set_Source("hollow_hit.wav");
				break;
			case 3:
				set_Source("hollow_hit_2.wav");
				break;
			case 4:
				set_Source("crash.wav");
				break;
			case 5:
				set_Source("congo1.wav");
				break;
			case 6:
				set_Source("congo2.wav");
				break;
			case 7:
				set_Source("bassdrum.wav");
				break;
			case 8:	
				set_Source("hollow_hit_2.wav");
				break;
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
		panner = new Panner(ac, panLFO);
		panner.addInput(gsp);
		ac.out.addInput(panner);		
		// begin audio processing
		ac.start();
		//This is important after starting the audio. 
		save_soundfile(audio_save_file);
	}
	
	
	
	Sample outputSample = new Sample(100000, 2, 44100);
	
	public void save_soundfile (String audio_string){
		

		try{
			SampleAudioFormat af = new SampleAudioFormat( 44100.0f, 16, 1, true, true); 

			AudioFileWriter write_audio_rght = null ;
			String filename2="";
			//filename2=;
			
		
			
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
				rts.pause( true); rts.clip(); outputSample.write(audio_string, AudioFileType.WAV); 
				rts.kill();
				System.out.println("Finished recording.");
			}
			catch (Exception e) { e.printStackTrace(); System.exit( 1);
				System.exit(0);
		}
		stop_audio();
	}
		Sample sourceSample = null;
		public void set_Source (String audio_filein){
		
		try
		{
			sourceSample = new Sample(audio_filein);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
	}
		public void stop_audio(){
			ac.stop();
		}










}