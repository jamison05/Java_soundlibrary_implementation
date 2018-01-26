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
import net.beadsproject.beads.ugens.WavePlayer;

public class drum_machine_01
{
	public double timeelapse;
	public double timestart;
	WavePlayer kick, snareNoise, snareTone;
	Envelope kickGainEnvelope, snareGainEnvelope;
	Gain kickGain, snareGain;
	BiquadFilter kickFilter, snareFilter, snareFilter2;
	
	public static void main(String[] args)
	{
		
		read_playfile rp = new read_playfile(1);
		drum_machine_2 synth2 = new drum_machine_2();
		synth2.setup();
	}
	 int delay = 100;//In order to go at faster speeds you will have to shorten the envelope
	 Timer dts;
	// construct the synthesizer
	public void setup()
	{
		
		Bead ltf[] = {kick, snareNoise, snareTone};

		//Will have LP filters and BI quad filters. 
		//
		AudioContext ac = new AudioContext();

		// set up the envelope for kick gain
		kickGainEnvelope = new Envelope(ac, 0.0f);
		// construct the kick WavePlayer
		kick = new WavePlayer(ac,50.0f, Buffer.SINE);
		// set up the filters
		kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP, 250.0f, 50f);
		kickFilter.addInput(kick);
		// set up the Gain
		kickGain = new Gain(ac, 1, kickGainEnvelope);
		kickGain.addInput(kickFilter);

		// connect the gain to the main out
		ac.out.addInput(kickGain);

		
		// set up the snare envelope
		snareGainEnvelope = new Envelope(ac, 0.0f);
		// set up the snare WavePlayers
		snareNoise = new WavePlayer(ac, 20f, Buffer.NOISE);
		snareTone = new WavePlayer(ac, 200.0f, Buffer.SINE);
		// set up the filters
		snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT, 4000.0f, 1f);
		snareFilter2 = new BiquadFilter(ac, BiquadFilter.BP_PEAK, 300.0f, 25f);
		snareFilter.addInput(snareNoise);
		snareFilter.addInput(snareTone);
		snareFilter2.addInput(snareFilter);
		
		
		Gain addtosnare = new Gain(ac,1, 50);
		// set up the Gain
		snareGain = new Gain(ac, 1, snareGainEnvelope);
		snareGain.addInput(snareFilter2);
		
		// connect the gain to the main out
		//ac.out.addInput(snareGain);
		ac.start();
		
		
		
	
		   dts = new Timer(delay, taskPerformer);
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
			kickGainEnvelope.addSegment(0.6f, 5.0f);
			// release segment
			kickGainEnvelope.addSegment(0.0f,100.0f);
		}
		
		// snare should trigger on E
		if( midiPitch == 4 )
		{
			// attack segment
			snareGainEnvelope.addSegment(100f, 2.00f);
			// decay segment
			snareGainEnvelope.addSegment(50f, 5f);
			// release segment
			snareGainEnvelope.addSegment(0.0f,100.0f);  //For the drums you want to be able to fill it so that the beats don't sound random.
		}
	}
	int m=0;
	public void keyUp(int midiPitch)
	{
		// do nothing
	}

	 ActionListener taskPerformer = new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	       
	        
	        	keyDown(4);
	        	m++;
	        
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
