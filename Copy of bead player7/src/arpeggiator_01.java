import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Function;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.LPRezFilter;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Phasor;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.TapIn;
import net.beadsproject.beads.ugens.TapOut;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;


public class arpeggiator_01
{
	float frequency = 100.0f;
	int tick = 0;
	Function arpeggiator;
	WavePlayer square;
	
	Envelope gainEnvelope;
	Gain gain;
	
	int lastKeyPressed = -1;
	 int delay = 1500; 
	 int delay2= 800;//This is the second delay for the keyups.  They have their own time pattern too in midi files. 
	 Timer dts;
	 Timer dts2; //Key up timer that is every 1500
	Clock beatClock;
	
	public static void main(String[] args)
	{
		arpeggiator_01 synth = new arpeggiator_01();
		synth.setup();
	}
	
	// construct the synthesizer
	public void setup()
	{
		AudioContext ac = new AudioContext();
	
		// the gain envelope
		gainEnvelope = new Envelope(ac, 0.0f);
		
		// set up a custom function to arpeggiate the pitch
		arpeggiator = new Function(gainEnvelope)
		{
			@Override
			public float calculate()
			{
				return frequency * (1+ tick);
			}
			
			@Override
			public void messageReceived(Bead msg)
			{
				tick++;
				if( tick >= 4 ) tick = 0;
			}
		};
		// add arpeggiator as a dependent to the AudioContext
		ac.out.addDependent(arpeggiator);
		
		// the square generator
		square = new WavePlayer(ac, arpeggiator, Buffer.SQUARE);

		// set up a clock to keep time
		beatClock = new Clock(ac, 500.0f);
		beatClock.setTicksPerBeat(4);
		beatClock.addMessageListener(arpeggiator);
		ac.out.addDependent(beatClock);
		
		// set up the Gain and connect it to the main output
		gain = new Gain(ac, 1, gainEnvelope);
		gain = new Gain(ac, 2, gainEnvelope);
		gain.addInput(square);
		ac.out.addInput(gain);

		// set up the keyboard input
	
		
		ac.start();
		beatClock.start();
	     dts = new Timer(delay, taskPerformer);
	     dts.start();
	     dts2 = new Timer(delay2, taskPerformer2);
	     dts2.setRepeats(false);
	     
		
		beatClock.getTicksPerBeat();
		
		
	

	

	
	}
	
	
	
	
	
	
	private float pitchToFrequency(int midiPitch)
	{
		/*
		 *  MIDI pitch number to frequency conversion equation from
		 *  http://newt.phys.unsw.edu.au/jw/notes.html
		 */
		double exponent = (midiPitch - 69.0) / 12.0;
		return (float)(Math.pow(2, exponent) * 440.0f);
	}
	
	public void keyDown(int midiPitch)
	{
		if( square != null && gainEnvelope != null )
		{
			lastKeyPressed = midiPitch;
			
			// restart the arpeggiator
			frequency = pitchToFrequency(midiPitch);
			tick = -1;
			beatClock.reset();
			
			// interrupt the envelope
			gainEnvelope.clear();
			// attack segment
			gainEnvelope.addSegment(0.5f, 10.0f);
		}
	}
	
	public void keyUp(int midiPitch)
	{
		// release segment
		if( midiPitch == lastKeyPressed && gainEnvelope != null )
			gainEnvelope.addSegment(0.0f, 50.0f);
	}

	 ActionListener taskPerformer = new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	       
	       
	        	keyDown(30);
	   	     dts2.start();
	        
	        
	        }
	    };
		 ActionListener taskPerformer2 = new ActionListener() {
		        public void actionPerformed(ActionEvent evt) {
		       
		       
		        
		        	keyUp(30);
		        }
		    };

	   
}
