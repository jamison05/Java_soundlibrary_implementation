package beadsdrumplayer;


import java.io.BufferedReader;
import java.io.File;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.LPRezFilter;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Phasor;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;


public class Simp_wav
{
	public static Newbuffers tlr1 = new Newbuffers(2);

	private WavePlayer wav1, wav2;
	private WavePlayer modulator;
	private Envelope wav1Envelope1, wav2Envelope2;
	private Gain g,g2, masterGain;
	private Gain sineGain, SineGain2;
	private Phasor phas;
	private Panner p1a, pan1;
	private Glide gainValue;
	private LPRezFilter lprez;
	private BiquadFilter bqi;
	static WavePlayer panLFO;
	private Reverb reverb;
	private WaveShaper ws, ws2;		
	Simp_wav(AudioContext ac){
		wave_gen(ac);
	}
	
	public void wave_gen(AudioContext ac)
	{//Harmonica

		
		Newbuffers tlr1 = new Newbuffers(9);
		Newbuffers tlr2 = new Newbuffers(2);
	    	ws= new WaveShaper(ac, 1, tlr1.mr1);
	    	ws2= new WaveShaper(ac, 1, tlr2.mr1);
	    	ws.setWetMix(.7f);
			ws2.setWetMix(1);
	 reverb = new Reverb(ac);

				reverb.setSize((float)0.6);
				reverb.setDamping((float)0.2);
				reverb.setSize(0.6f);
	
				wav1Envelope1 = new Envelope(ac,0f);
				wav2Envelope2 = new Envelope(ac,0f);
				wav1 = new WavePlayer(ac,440,Buffer.SINE);
				wav2 = new WavePlayer(ac,440,Buffer.SINE);
	
				ws.addInput(wav1);
				ws2.addInput(wav2);
				
				g = new Gain(ac, 1, wav1Envelope1);
				g.addInput(ws); 
				g2= new Gain(ac,1, wav2Envelope2);
				g2.addInput(ws2);
	
	//	Phasor phas = new Phasor(ac,0.5f);
		panLFO = new WavePlayer(ac,1f, Buffer.SINE);//Do not set the frequency below 1. 
		p1a = new Panner(ac, panLFO);//Use a glide for more sweeping modficiations.   
	
		gainValue = new Glide(ac, 0, 100);
		masterGain = new Gain(ac, 1, gainValue);
	
	
		masterGain.addInput(g);
		masterGain.addInput(g2);
		
		reverb.addInput(masterGain);
		p1a.addInput(reverb);
		
		ac.out.addInput(p1a);
		// begin audio processing
		ac.start();
		//tj.setValue(1);
		//p = new Panner(ac,tj);
		//p.addInput(g);		
		//This actually creates a slow shift with the Glide tj.  This is for more effect. 
	}    //Have the more higher effect here.  
	

	void keyDown1(int midiPitch,float valGlide, float time_shift){
		gainValue.setValue(valGlide);
int j = 0;
				wav1.setFrequency(pitchToFrequency(midiPitch));

			
            wav1.setFrequency(pitchToFrequency(midiPitch));
		
			wav1Envelope1.addSegment(0f*valGlide, 50f*time_shift);
			wav1Envelope1.addSegment(0.7f*valGlide, 250f*time_shift);
			wav1Envelope1.addSegment(1f*valGlide,500.0f*time_shift);
			wav1Envelope1.addSegment(.7f*valGlide, 250f*time_shift);
			wav1Envelope1.addSegment(0.0f*valGlide,500f*time_shift);  //For the drums you want to be able to fill it so that the beats don't sound random.
	
		
		
		
		
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
	

	
	public void keyUp(int midiPitch)
	{
		
			masterGain.setValue(0.0f);
	
	}
	}
