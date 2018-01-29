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

public class generate_audio {
	
	
	AudioContext ac = new AudioContext();
	
	
	public void generate_audio (int audio_input){
		
		
		
		switch  (audio_input){
		
			case 1:
				set_Source("bassdruum.wav");
			case 2:
				set_Source("small_hit3.wav");
			case 3:
				set_Source("small_hit2.wav");
			case 4:
				set_Source("crash.wav");
			case 5:
				set_Source("congo1.wav");
			case 6:
				set_Source("congo2.wav");
			case 7:
				set_Source("bassdruum.wav");
			case 8:	
				set_Source("hollow_hit2.wav");
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
	}
	
	
	
	
	
	public void save_soundfile (){
		

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