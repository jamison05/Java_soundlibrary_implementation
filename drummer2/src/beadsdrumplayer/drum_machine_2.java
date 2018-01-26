package beadsdrumplayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.ShortMessage;
import javax.swing.Timer;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;

public class drum_machine_2
{
	public double timeelapse;
	public double timestart;
	WavePlayer kick, snareNoise, snareTone;
	WavePlayer kick1, snareNoise1, snareTone1;
	WavePlayer kick2, snareNoise2, snareTone2;
	WaveShaper kick3, snareNoise3, snareTone3;
	Envelope kickGainEnvelope, snareGainEnvelope;
	Gain kickGain, snareGain;
	Gain kickGain1, snareGain1;
	Gain kickGain2, snareGain2;
	BiquadFilter kickFilter, snareFilter, snareFilter2;
	Reverb revdrum1,revdrum2;
	Panner pandrum1,pandrum2;
	
	public float[] mus1= new float[100];
	 int delay = 100;//In order to go at faster speeds you will have to shorten the envelope
	 int delay2 = 1000;
	 Timer dts;
	// construct the synthesizer
	public void setup()
	{

		 
		 read_playfile st_mus = new read_playfile(1);
		 
		 for (int i=0; i < st_mus.musr1.length;i++){
		 mus1[i]=st_mus.musr1[i][3];
		 
		 }
		Bead ltf[] = {kick, snareNoise, snareTone};

		//Will have LP filters and BI quad filters. 
		AudioContext ac = new AudioContext();

				// // construct the kick WavePlayerset up the envelope for kick gain, 	// set up the Gain,	// set up the filters
				kickGainEnvelope = new Envelope(ac, 0.0f);
				kick = new WavePlayer(ac,50.0f, Buffer.SINE);
				kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP, 250.0f, 50f);
				kickFilter.addInput(kick);
				kickGain = new Gain(ac, 1, kickGainEnvelope);
				kickGain.addInput(kickFilter);
				ac.out.addInput(kickGain);
				// set up the snare WavePlayers
				// set up the snare envelope
				//Here is like a guitar like sound.
				//ticking sound.
				Newbuffers mlr = new Newbuffers(28);
				snareTone3= new WaveShaper(ac,1, mlr.mr1);
				snareGainEnvelope = new Envelope(ac, 0.0f);
				snareNoise = new WavePlayer(ac,600f, Buffer.NOISE);
				snareGain1 = new Gain(ac, 1, snareGainEnvelope);
				snareTone = new WavePlayer(ac,200f, Buffer.NOISE);
				snareTone3.addInput(snareTone);
				snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT,2000f, 1f);
				snareFilter2 = new BiquadFilter(ac, BiquadFilter.BP_PEAK,400f, 50f);
				snareFilter.addInput(snareNoise);
				snareFilter.addInput(snareTone);
				snareFilter2.addInput(snareFilter);
				snareGain1.addInput(snareFilter2);
				Gain addtosnare = new Gain(ac,1, 50);
				// set up the Gain
				
			
			
				//snareGain1.addInput(snareFilter2);
				revdrum1 = new Reverb(ac);
				revdrum1.setSize(0.0001f);
				revdrum1.setLateReverbLevel(0.7f);
				
				//revdrum1.addInput();
			
				revdrum1.addInput(snareGain1);
				// connect the gain to the main out
				ac.out.addInput(revdrum1);
			
		ac.start();
	
		   dts = new Timer(delay2, taskPerformer);
		   dts.start();
	
		
	   
	
	
	}
	
	public void keyDown(int midiPitch)
	{
		if (m==25){
			snareGainEnvelope.clear();
			snareGainEnvelope.setValue(0);
		
		

		
		
		}
		
	   
		
		// kick should trigger on C
		if( midiPitch == 0 )
		{
			// attack segment
			kickGainEnvelope.addSegment(0.8f, 2.0f);
			// decay segment
			kickGainEnvelope.addSegment(0.6f, 1.0f);
			// release segment
			kickGainEnvelope.addSegment(0.0f,1.0f);
		}
		
		// snare should trigger on E
		if( midiPitch == 4 )
		{
			// attack segment
			snareGainEnvelope.addSegment(100f,2.00f);
			// decay segment
			snareGainEnvelope.addSegment(50f, 10f);
			// release segment
			snareGainEnvelope.addSegment(0.0f,10f);  //For the drums you want to be able to fill it so that the beats don't sound random.
		}
	}
	int m=2;float kle=0;int klp=0;
	public void keyUp(int midiPitch)
	{
		// do nothing
	}

	 ActionListener taskPerformer = new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	kle= ((mus1[m+1])*(1000))- ((mus1[m])*(1000));
	        	
	        	klp = (int)kle;
	        	//dts.setDelay(klp);
	          	keyDown(4);
	     		
	        
	        	m++;
	        if (mus1[m+1]==0){m=2;} //Resets the numbers  
	        }
	    };
	
	
//	public void holdn(int mili, AudioContext ac){
	//	double ti1=0;	
		//timestart=ac.getTime();
		//while (timeelapse<mili)
		//{
		//double timeelapse;
		//ti1= ac.getTime();
		//timeelapse= ac.getTime()-timestart;
		
		//}
		//ti1=0;
	//}

}
