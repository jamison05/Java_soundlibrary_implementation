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


public class Amp_Mod1
{
	public static Newbuffers tlr1 = new Newbuffers(2);

	private  WavePlayer carrier;
	private  WavePlayer modulator;
	private Envelope amp_modEnvelope1, amp_modEnvelope2;
	private Gain masterGain;
	private Gain sineGain;
	private Phasor phas;
	private Panner p;
	private Glide gainValue;
	private LPRezFilter lprez;
	private BiquadFilter bqi;
	private static WavePlayer panLFO;

	Amp_Mod1(AudioContext ac){
		wave_gen(ac);
	}
	
	public void wave_gen(AudioContext ac)
	{

		WaveShaper ws, ws2;		
		Newbuffers tlr1 = new Newbuffers(23);
		Newbuffers tlr2 = new Newbuffers(2);
	    
		Reverb reverb = new Reverb(ac);
		ws= new WaveShaper(ac, 1, tlr1.mr1);
		ws2= new WaveShaper(ac, 1, tlr2.mr1);
		Phasor phasor = new Phasor(ac,1);
		phasor.setFrequency((float) .2);
		reverb.setSize((float)0.2);
		reverb.setDamping((float)0.7);  //Use Damping to get rid of the artifacts. 
		Gain g;
		ws.setWetMix(.7f);
		ws2.setWetMix(1);
			Panner p;
		amp_modEnvelope1 = new Envelope(ac,0f);	
			
		carrier = new WavePlayer(ac,440,Buffer.TRIANGLE);
		
		Buffer dtk = new Buffer(3);
	
		ws.addInput(carrier);
	    modulator = new WavePlayer(ac,5f, Buffer.SINE);
	    ws2.addInput(modulator);
	    ws2.calculateBuffer();
		 sineGain = new Gain(ac, 1,modulator);
		sineGain.addInput(ws);
		gainValue = new Glide(ac,  0f, 100); //The middle value makes more controls the volume.
		g = new Gain(ac, 1, amp_modEnvelope1);
		g.addInput(sineGain); 
		Phasor phas = new Phasor(ac,0.5f);
		panLFO = new WavePlayer(ac,1f, Buffer.SINE);//Do not set the frequency below 1. 
		p = new Panner(ac, panLFO);//Use a glide for more sweeping modficiations.   
		
		reverb.setSize(.001f);
		masterGain = new Gain(ac, 1, gainValue);
	

		masterGain.addInput(g);
		reverb.addInput(masterGain);
		p.addInput(reverb);
		ac.out.addInput(p);
		// begin audio processing
		ac.start();
		
	
		//tj.setValue(1);
		//p = new Panner(ac,tj);
		//p.addInput(g);		
		//This actually creates a slow shift with the Glide tj.  This is for more effect. 
	}    //Have the more higher effect here.  
	

	
	
	
	public void wave_gen2(AudioContext ac)
	{

		WaveShaper ws, ws2;		
		Newbuffers tlr1 = new Newbuffers(23);
		Newbuffers tlr2 = new Newbuffers(2);
	    
		Reverb reverb = new Reverb(ac);
		ws= new WaveShaper(ac, 1, tlr1.mr1);
		ws2= new WaveShaper(ac, 1, tlr2.mr1);
		Phasor phasor = new Phasor(ac,1);
		phasor.setFrequency((float) .2);
		reverb.setSize((float)0.2);
		reverb.setDamping((float)0.7);  //Use Damping to get rid of the artifacts. 
		Gain g;
		ws.setWetMix(.7f);
		ws2.setWetMix(1);
			Panner p;
		amp_modEnvelope1 = new Envelope(ac,0f);	
			
		carrier = new WavePlayer(ac,440,Buffer.TRIANGLE);
		
		Buffer dtk = new Buffer(3);
	
		ws.addInput(carrier);
	    modulator = new WavePlayer(ac,5f, Buffer.SINE);
	    ws2.addInput(modulator);
	    ws2.calculateBuffer();
		 sineGain = new Gain(ac, 1,modulator);
		sineGain.addInput(ws);
		gainValue = new Glide(ac,  0f, 100); //The middle value makes more controls the volume.
		g = new Gain(ac, 1, amp_modEnvelope1);
		g.addInput(sineGain); 
		Phasor phas = new Phasor(ac,0.5f);
		panLFO = new WavePlayer(ac,1f, Buffer.SINE);//Do not set the frequency below 1. 
		p = new Panner(ac, panLFO);//Use a glide for more sweeping modficiations.   
		
		reverb.setSize(.001f);
		masterGain = new Gain(ac, 1, gainValue);
	

		masterGain.addInput(g);
		reverb.addInput(masterGain);
		p.addInput(reverb);
		ac.out.addInput(p);
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
		carrier.setFrequency(pitchToFrequency(midiPitch));
	
			
	
			amp_modEnvelope1.addSegment(0f*valGlide, 50f*time_shift);
			amp_modEnvelope1.addSegment(0.7f*valGlide, 250f*time_shift);
			amp_modEnvelope1.addSegment(1f*valGlide,500.0f*time_shift);
			amp_modEnvelope1.addSegment(.7f*valGlide, 250f*time_shift);
			amp_modEnvelope1.addSegment(0.0f*valGlide,1000f*time_shift);  //For the drums you want to be able to fill it so that the beats don't sound random.
	
		
		
		
		
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

