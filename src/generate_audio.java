import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleAudioFormat;
import net.beadsproject.beads.data.audiofile.AudioFileType;
import net.beadsproject.beads.data.audiofile.AudioFileWriter;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.RecordToSample;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;
import net.beadsproject.beads.ugens.WavePlayer;

public class Generate_audio extends Granular_Example_01{
	
	
	
	
	
	public void generate_audio1 (int audio_input, String audio_save_file, int fuzzy_ref, AudioContext ac){
		
		
	
		switch  (audio_input){
		
		case 1:
			set_Source("lmn1_1.wav");
			break;
		case 2:
			set_Source("lmn1_2.wav");
			break;
		case 3:
			set_Source("lmn2_1.wav");
			break;
		case 4:
			set_Source("lmn2_2.wav");
			break;
		case 5:
			set_Source("lmn3_1.wav");
			break;
		case 6:
			set_Source("lmn3_2.wav");
			break;
		case 7:
			set_Source("lmn4_1.wav");
			break;
		case 8:	
			set_Source("lmn4_2.wav");
			break;
		case 9:
			set_Source("lmn5_1.wav");
			break;
		case 10:
			set_Source("lmn5_2.wav");
			break;
		case 11:
			set_Source("lmn6_1.wav");
			break;
		case 12:
			set_Source("lmn6_2.wav");
			break;
		case 13:
			set_Source("lmn7_1.wav");
			break;
		case 14:
			set_Source("lmn7_2.wav");
			break;
		case 15:
			set_Source("lmn8_1.wav");
			break;
		case 16:	
			set_Source("lmn8_2.wav");
			break;
		case 17:
			set_Source("lmn9_1.wav");
			break;
		case 18:
			set_Source("lmn9_2.wav");
			break;
		case 19:
			set_Source("lmn10.wav");
			break;
		case 20:
				set_Source("lmn11.wav");
				break;
		case 21:
				set_Source("lmn12.wav");
				break;
		case 22:
				set_Source("congo2.wav");
				break;
		case 23:
				set_Source("bassdrum.wav");
				break;
		case 24:	
				set_Source("hollow_hit_2.wav");
				break;
		}
		// instantiate a GranularSamplePlayer
		// tell gsp to loop the file
		GranularSamplePlayer gsp = new GranularSamplePlayer(ac, sourceSample);
		gsp.setLoopType(SamplePlayer.LoopType.LOOP_BACKWARDS);
		
		
		
		
		// set the grain size to a fixed 10ms
		gsp.setGrainSize(new Static(ac, 1000f));
		gsp.setGrainInterval(new Static (ac, 300f));
	    gsp.setPosition(new Static (ac, 100f));
//		gsp.setPitch(pitchValue);
//		
		WavePlayer me = new WavePlayer(ac,500f, Buffer.NOISE);
		
		
		Envelope add_envelope = new Envelope (ac, 0); 
		Envelope add_envelope2 = new Envelope (ac, 0); 
		Glide ms = new Glide(ac, .8f, 20f);
    
		Glide randomnessValue = new Glide(ac, 80, 1);
		Glide intervalValue = new Glide(ac, 2f, 1);
		Glide grainSizeValue = new Glide(ac, 1000, 1);
		Glide positionValue = new Glide(ac, 200000, 1);
		Glide pitchValue = new Glide(ac, .5f, 1);

		gsp.setPitch(add_envelope);
		gsp.setGrainSize(add_envelope2);;
		// tell gsp to behave somewhat randomly
		Panner panner;
		
		WavePlayer panLFO = new WavePlayer(ac,1f, Buffer.SINE);//Do not set the frequency below 1. 
		panner = new Panner(ac, panLFO);
		panner.addInput(gsp);
		ac.out.addInput(panner);		
		// begin audio processing
		ac.start();
		
		
		for (int iter_for=0, a =0; iter_for<4000;iter_for+=500, a++){
			add_envelope.addSegment(file_holder1[fuzzy_ref][a++], iter_for);
		}
		 	
		
			for (int iter_for=0, a = 0; iter_for<4000;iter_for+=500, a++ ){
				if (file_holder1[fuzzy_ref][a] <.01){
					file_holder1[fuzzy_ref][a] =.01f;
				}
				add_envelope2.addSegment((file_holder1[fuzzy_ref][a])*500, iter_for);
			}
			
		//This is important after starting the audio. 
		save_soundfile(ac, audio_save_file);
	}
	
	
	
	
	long startTime = System.currentTimeMillis();
	// Run some code;
	long diff=0;
	long stopTime=0;
	int a =0;
	public void save_soundfile (AudioContext ac,String audio_string){
	

		Sample outputSample = new Sample(1000000, 2, 44100);
		RecordToSample rts;
		rts = new RecordToSample(ac, outputSample, RecordToSample.Mode.INFINITE); 
		rts.addInput(ac.out); 
		ac.out.addDependent(rts);
		try{
		 
		
			//		Scanner in = new Scanner( System.in); System.out.println(" Press enter to save sound and end the program.");
			//		in.nextLine();

			startTime = System.currentTimeMillis();
			// Run some code;
			 diff=0;
			stopTime=0;
			while (diff < 4050){
				stopTime = System.currentTimeMillis();
				diff = stopTime - startTime;
			}		
				diff=0;
				rts.pause( true); rts.clip();
				outputSample.write(audio_string, AudioFileType.WAV); 
				System.out.println("Finished recording.");
			}
			catch (Exception e) { e.printStackTrace(); System.exit( 1);
				System.exit(0);
		}
		
		
		
		rts.kill();
		rts = null;
		
		ac.stop();
		ac.reset();
		ac = null;
	}
		Sample sourceSample = null;
		public void set_Source (String audio_filein){
		
		try{
			sourceSample = new Sample(audio_filein);
		}
		catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		
		}
		
		public void audio_purge(){
			
			
		}
		public void stop_audio(){
	
		}


}