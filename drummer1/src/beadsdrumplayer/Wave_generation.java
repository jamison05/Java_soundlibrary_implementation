package beadsdrumplayer;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;



class Wave_generation{

	private WavePlayer kick, snareNoise, snareTone,tick1,cymb;
	private Envelope kickGainEnvelope, snareGainEnvelope;
	private Envelope tickGainEnvelope;
	private Gain kickGain, snareGain, kickGain1;
	private Gain tickGain1;
	private Gain masterGain1, masterGain2, masterGain3;
	private Glide mastGld1, mastGld2, mastGld3;
	private BiquadFilter kickFilter, snareFilter, snareFilter2,snareFilter3;	
	private Reverb reverb1, reverb2, reverb3;
	//Panner panner1, panner2;
	
	Wave_generation(AudioContext ac){
		
	
		
	}
		

		
	void wave_gen(AudioContext ac){
	

	
			reverb1= new Reverb(ac);
		 	reverb1.setSize(0.1f);
		 	reverb1.setLateReverbLevel(1f);
		 	reverb2= new Reverb(ac);
		 	reverb2.setSize(0.02f);
		 	reverb2.setLateReverbLevel(.02f);
		 	reverb3= new Reverb(ac);
		 	reverb3.setSize(0.001f);
		 	reverb3.setLateReverbLevel(0.001f);
		 	
		 	
		 	
		 	
		// set up the envelope for kick gain
			kickGainEnvelope = new Envelope(ac, 0.0f);
			
			
			// construct the kick WavePlayer
			kick = new WavePlayer(ac,50.0f, Buffer.SAW);
			// set up the filters
			kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP, 200.0f, 1f);
			kickFilter.addInput(kick);
			// set up the Gain
			kickGain = new Gain(ac, 1, kickGainEnvelope);
			kickGain.addInput(kickFilter);
			reverb1.addInput(kickGain);
			// connect the gain to the main out
	
		

			
			// set up the snare envelope
			snareGainEnvelope = new Envelope(ac, 0.0f);
			// set up the snare WavePlayers
			snareNoise = new WavePlayer(ac, 3f, Buffer.NOISE);
			snareTone = new WavePlayer(ac,100f, Buffer.SINE);
			// set up the filters
			snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT,2000f, 1f);
	
			snareFilter.addInput(snareNoise);
			snareFilter.addInput(snareTone);
		
		
			Gain addtosnare = new Gain(ac,1, 50);
			// set up the Gain
			snareGain = new Gain(ac, 1, snareGainEnvelope);
			snareGain.addInput(snareFilter);
			reverb2.addInput(snareGain);
		
			
			
			snareFilter3 = new BiquadFilter(ac, BiquadFilter.BP_PEAK,400f, 100f);
			tick1 = new WavePlayer(ac,1.0f, Buffer.NOISE);
			cymb = new WavePlayer(ac,1.0f, Buffer.NOISE);
			Newbuffers ltp = new Newbuffers(112);
			WaveShaper ltp_s = new WaveShaper(ac,1, ltp.mr1);
			ltp_s.addInput(cymb);
			
			
			//snareFilter3.setFrequency(400);
			//snareFilter3.setQ(200);
			snareFilter3.addInput(tick1);
			snareFilter3.addInput(ltp_s);
			tickGainEnvelope= new Envelope(ac,0f);
			tickGain1= new Gain(ac, 3, tickGainEnvelope);
			tickGain1.addInput(snareFilter3);
			reverb3.addInput(tickGain1);
			
	
			
			
			ac.out.addInput(reverb1);
			
			ac.out.addInput(reverb2);
			
			ac.out.addInput(reverb3);
	}			// connect the gain to the main out
			//ac.out.addInput(snareGain);

	
	
	
	
	














	void keyDown1(int midiPitch, int m,float valGlide){
		
//I also want to affect volume 
		if (m==100){
			snareGainEnvelope.clear();
			snareGainEnvelope.setValue(0);
			kickGainEnvelope.clear();
			kickGainEnvelope.setValue(0);
			tickGainEnvelope.clear();
			tickGainEnvelope.setValue(0);
		
		}
		
		// kick should trigger on C
		if( midiPitch == 0 )
		{	
			// attack segment
			kickGainEnvelope.addSegment(0.8f*valGlide, 2.0f);
			// decay segment
			kickGainEnvelope.addSegment(0.6f*valGlide, 5.0f);
			// release segment
			kickGainEnvelope.addSegment(0.1f*valGlide, 10.0f);
			kickGainEnvelope.addSegment(0.01f*valGlide, 10.0f);
			kickGainEnvelope.addSegment(0.001f*valGlide, 10.0f);
			
			kickGainEnvelope.addSegment(0.0f*valGlide,200.0f);
		}
		
		// snare should trigger on E
		if( midiPitch == 4 )
		{	
			// attack segment
		snareGainEnvelope.addSegment(0.8f*valGlide, 2.0f);
		// decay segment
		snareGainEnvelope.addSegment(0.6f*valGlide, 5.0f);
		// release segment
		snareGainEnvelope.addSegment(0.1f*valGlide, 10.0f);
		snareGainEnvelope.addSegment(0.01f*valGlide, 10.0f);
		snareGainEnvelope.addSegment(0.001f*valGlide, 10.0f);
		
		kickGainEnvelope.addSegment(0.0f*valGlide,200.0f);  //For the drums you want to be able to fill it so that the beats don't sound random.
		}
		
		
		
		
		// tickshould trigger on E
		if( midiPitch ==6 )
		{
			// attack segment
			tickGainEnvelope.addSegment(100f*valGlide, 5.00f);
			// decay segment
			tickGainEnvelope.addSegment(50f*valGlide, 4f);
			tickGainEnvelope.addSegment(25f*valGlide, 3f);
			tickGainEnvelope.addSegment(1f*valGlide,2f);
			tickGainEnvelope.addSegment(0.1f*valGlide, 5f);
			// release segment
			tickGainEnvelope.addSegment(0.0f*valGlide,1.0f);  //For the drums you want to be able to fill it so that the beats don't sound random.
		}
		
		
		  // masterGain.setValue(valGlide);
		
	}
	
	void keyDown2(int midiPitch, int m,float valGlide){
		
	

		if (m==100){
			snareGainEnvelope.clear();
			snareGainEnvelope.setValue(0);
			kickGainEnvelope.clear();
			kickGainEnvelope.setValue(0);
			tickGainEnvelope.clear();
			tickGainEnvelope.setValue(0);
		
		}
		
		
		// kick should trigger on C
		if( midiPitch == 0 )
		{
			// attack segment
			kickGainEnvelope.addSegment(0.8f*valGlide, 2.0f);
			// decay segment
			kickGainEnvelope.addSegment(0.6f*valGlide, 5.0f);
			// release segment
			kickGainEnvelope.addSegment(0.0f*valGlide,100.0f);
		
			
		}
		
		// snare should trigger on E
		if( midiPitch == 4 )
		{
			// attack segment
			snareGainEnvelope.addSegment(100f*valGlide, 2.00f);
			// decay segment
			snareGainEnvelope.addSegment(50f*valGlide, 5f);
			// release segment
			snareGainEnvelope.addSegment(0.0f*valGlide,100.0f);  //For the drums you want to be able to fill it so that the beats don't sound random.
			
		}
		
		//masterGain.setValue(valGlide);
		
		
	}
	
	
	
	void wave_gen2(AudioContext ac){


		
		// set up the envelope for kick gain
			kickGainEnvelope = new Envelope(ac, 0.0f);
			Newbuffers ltp = new Newbuffers(9);
			WaveShaper ltp_s = new WaveShaper(ac,1, ltp.mr1);
			ltp_s.setPreGain(0.6f);
			ltp_s.setPostGain(1.5f);
			// construct the kick WavePlayer
			kick = new WavePlayer(ac,20.0f, Buffer.SINE);
			ltp_s.addInput(kick);
			
			// set up the filters4
			kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP,50f, 300f);
			kickFilter.addInput(kick);
			// set up the Gain
			kickGain = new Gain(ac, 1, kickGainEnvelope);
			kickGain.addInput(ltp_s);
			Reverb mrev = new Reverb(ac);
			mrev.setSize(0.01f);
			mrev.setDamping(0f);
			mrev.setEarlyReflectionsLevel(1f);
			mrev.setLateReverbLevel(10f);
			mrev.addInput(kickGain);
		
			// connect the gain to the main out
			//ac.out.addInput(kickGain);
			
	
			ac.out.addInput(mrev);
			
			
			// set up the snare envelope
			snareGainEnvelope = new Envelope(ac, 0.0f);
			// set up the snare WavePlayers
			snareNoise = new WavePlayer(ac, 1f, Buffer.NOISE);
			snareTone = new WavePlayer(ac, 20.0f, Buffer.SINE);
			// set up the filters
			snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT, 2500.0f, 1f);
			snareFilter2 = new BiquadFilter(ac, BiquadFilter.BP_PEAK, 250.0f, 50f);
			snareFilter.addInput(snareNoise);
			snareFilter.addInput(snareTone);
			snareFilter2.addInput(snareFilter);
			
			
			Gain addtosnare = new Gain(ac,1, 50);
			// set up the Gain
			snareGain = new Gain(ac, 1, snareGainEnvelope);
			snareGain.addInput(snareFilter2);
			masterGain2.addInput(snareGain);
			ac.out.addInput(snareFilter2);
			
			// connect the gain to the main out
			//ac.out.addInput(snareGain);

	
	
	
	
	
}	
	
	
	
void wave_gen3(AudioContext ac){



		
		// set up the envelope for kick gain
			kickGainEnvelope = new Envelope(ac, 0.0f);
			Newbuffers ltp = new Newbuffers(9);
			WaveShaper ltp_s = new WaveShaper(ac,1, ltp.mr1);
			ltp_s.setPreGain(0.6f);
			ltp_s.setPostGain(1.5f);
			// construct the kick WavePlayer
			kick = new WavePlayer(ac,30.0f, Buffer.SINE);
			ltp_s.addInput(kick);
			
			// set up the filters4
			kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP,50f, 300f);
			kickFilter.addInput(kick);
			// set up the Gain
			kickGain = new Gain(ac, 1, kickGainEnvelope);
			kickGain.addInput(ltp_s);
		
			// connect the gain to the main out
			//ac.out.addInput(kickGain);
			
			ac.out.addInput(kickGain1);
			
			// set up the snare envelope
			snareGainEnvelope = new Envelope(ac, 0.0f);
			// set up the snare WavePlayers
			snareNoise = new WavePlayer(ac, 1f, Buffer.NOISE);
			snareTone = new WavePlayer(ac, 20.0f, Buffer.SINE);
			// set up the filters
			snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT, 2500.0f, 1f);
			snareFilter2 = new BiquadFilter(ac, BiquadFilter.BP_PEAK, 250.0f, 50f);
			snareFilter.addInput(snareNoise);
			snareFilter.addInput(snareTone);
			snareFilter2.addInput(snareFilter);
			
			
			Gain addtosnare = new Gain(ac,1, 50);
			// set up the Gain
			snareGain = new Gain(ac, 1, snareGainEnvelope);
			snareGain.addInput(snareFilter2);
			
	
			ac.out.addInput(snareGain);
			
			// connect the gain to the main out
			//ac.out.addInput(snareGain);

	
	
	
	
	
}	
}
