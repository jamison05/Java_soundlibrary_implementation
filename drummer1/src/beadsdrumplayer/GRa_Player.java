package beadsdrumplayer;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.LPRezFilter;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Phasor;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;
import net.beadsproject.beads.ugens.WavePlayer;

public class GRa_Player {
	Envelope wav1Envelope1, wav2Envelope2;
	Gain masterGain;
	Gain sineGain;
	Phasor phas;
	Panner p, pan1;
	Glide pGlide, mGlide, stLoopGlide;
	Glide enLoopGlide;
	LPRezFilter lprez;
	BiquadFilter bqi;
	static WavePlayer panLFO;
	Reverb reverb;
	GranularSamplePlayer gsp;
	
	GRa_Player (AudioContext ac){
		sound_gen(ac);
	}
	
	
	void sound_gen(AudioContext ac)
	{
		// instantiate the AudioContext
		
		// load the source sample from a file
		Sample sourceSample = null;
		try
		{
			sourceSample = new Sample("bassdrum.wav");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
		
		
		// instantiate a GranularSamplePlayer
		 gsp = new GranularSamplePlayer(ac, sourceSample);
		gsp.setLoopType(SamplePlayer.LoopType.NO_LOOP_FORWARDS);
		gsp.setGrainSize(new Static(ac, 100f));
		WavePlayer me = new WavePlayer(ac,500f, Buffer.NOISE);
		
		 pGlide = new Glide(ac, 1f, 1f);
		gsp.setPitch(pGlide);
		
		wav1Envelope1 =  new Envelope(ac, 1f);
		mGlide = new Glide(ac, 1f, 200f);
		stLoopGlide= new Glide(ac, 50000f);
		enLoopGlide = new Glide(ac,1f);
		gsp.setRate(enLoopGlide);
		masterGain = new Gain(ac, 1, mGlide);

		// connect our GranularSamplePlayer to the master gain
		masterGain.addInput(gsp);
		gsp.start(); // start the granular sample player
		// connect the master gain to the AudioContext's master
		// output
		ac.out.addInput(masterGain);
		ac.start(); // begin audio processing
		
	}
	
	
	
	
	void keyDown1(int drumtune,float valGlide ){

		mGlide.setValue(1f);
		//pGlide.setValue(drumtune);
		//gsp.setPitch(pGlide);
		//gsp.setValue(valGlide);
		//gsp.reset();
		
		
		gsp.reset();
		gsp.reTrigger();
		
		gsp.start();
	
		gsp.update();
	
	
	}
	
	


	
}
