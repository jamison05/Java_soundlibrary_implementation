import java.awt.Event;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.sound.midi.ShortMessage;


import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.core.UGen;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Phasor;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;
public class Waves_1 {
	
public Buffer[] buffs = {Buffer.SINE, Buffer.TRIANGLE, Buffer.SQUARE,Buffer.SAW, Buffer.NOISE};
public Bead[] wav_beads = new Bead[5];	//Bead number should be adjusted to the appropriate amount of audio objexts. 
public Bead[] wav_shape = new Bead[5];	
public Bead[] env_wav= new Bead[5];	
public Bead[][] env_shape= new Bead[5][10];	
public Bead phasor_t;
public Bead reverb_t;
public Bead panner_t;
public Bead effect_wav[]=new Bead[6];

void wave_generate(int wav_num, AudioContext ac, int wave_desr, float freq){
	
	WavePlayer wave1 = new WavePlayer(ac, freq, buffs[wave_desr]);//You loop the wave so that it can make a bead number.
	wav_beads[wav_num]=wave1;
		
}
	

void wave_shape_generate(int wav_num, AudioContext ac, int wave_desr, float freq){
	
	Newbuffers tlr1 = new Newbuffers(0);
	tlr1.generateBuffer(wave_desr);
	WaveShaper ws = new WaveShaper(ac, tlr1.mr1);//
	 ws.setPreGain(10f);
	 ws.setWetMix(1f);
	wav_shape[wav_num]=ws;
		
	// connect the gain to the WaveShaper		
}

void constr_env_n(int wav_num, AudioContext ac){
	//d=1;//Untill I figure out how to add it into  the envelopes. I may even more easily affect thiis at the object stage
	Envelope gainEnvelope = new Envelope(ac, 0.0f);	
	env_wav[wav_num]= gainEnvelope;
	
		
}//!!!Remember the following methods have inputs that are sequential.  It did not make sense to create a multi-dimensional array
void constr_env_ns(Envelope gainEnvelope, AudioContext ac, float[] env_arr, int d){
	//d=1;//Untill I figure out how to add it into  the envelopes. I may even more easily affect thiis at the object stage


		
         gainEnvelope.addSegment(0.0f,env_arr[1]/d);  //Ass the millseconds increase the l
		gainEnvelope.addSegment(0.25f,env_arr[1]/d);	
		gainEnvelope.addSegment(0.5f, env_arr[1]/d); 
		gainEnvelope.addSegment(0.8f, env_arr[2]/d);  //Sooner the attack 
		gainEnvelope.addSegment(1.0f, env_arr[2]/d);
		gainEnvelope.addSegment(0.8f, env_arr[3]/d);
		gainEnvelope.addSegment(0.5f, env_arr[3]/d);
		// ramp the gain to 0.0f over 500 ms
		gainEnvelope.addSegment(0.0f, env_arr[4]/d);
		
}//!!!Remember the following methods have inputs that are sequential.  It did not make sense to create a multi-dimensional array

//Effects consists of phasor, reverb, and panner for total effects. 

//I am also going to add gain especially after the reverb.   
void constr_tot_eff(int bo_ol[],AudioContext ac, float freq[] ){
if (bo_ol[0]==1){
	Phasor ph1 = new Phasor(ac, freq[0]);
	phasor_t= ph1;
}; 	
if (bo_ol[1]==1){
	Reverb rvb= new Reverb(ac);
	rvb.setSize(freq[1]);
	rvb.setDamping(freq[2]);
	rvb.setLateReverbLevel(freq[3]);
	reverb_t=rvb;
}; 

if (bo_ol[2]==1){
	WavePlayer panLFO = new WavePlayer(ac, freq[4], Buffer.SINE);//Do not set the frequency below 1. 
	Panner p = new Panner(ac, panLFO);
	panner_t= p;
	
}; 
}

void constr_wav_eff(int bo_ol[], int wav_num, AudioContext ac, float freq[]){
	if (bo_ol[0]==1){
		Phasor ph1 = new Phasor(ac, freq[0]);
		effect_wav[1]= ph1;
	}; 	
	if (bo_ol[1]==1){
		Phasor ph1 = new Phasor(ac, freq[1]);//Second wave, decided not to double array. 
		effect_wav[2]= ph1;
	}; 	
	
	if (bo_ol[2]==1){
		WavePlayer panLFO = new WavePlayer(ac, freq[2], Buffer.SINE);//Do not set the frequency below 1. 
		Panner p = new Panner(ac, panLFO);
		effect_wav[3]= p;
		
	}; 
	
	if (bo_ol[3]==1){
		WavePlayer panLFO = new WavePlayer(ac, freq[3], Buffer.SINE);//Do not set the frequency below 1. 
		Panner p = new Panner(ac, panLFO);
		effect_wav[4]= p;
		
	}; 

	if (bo_ol[4]==1){
		
		//WavePlayer carrier = new WavePlayer(ac,300,Buffer.SINE);
		//WavePlayer modulator = new WavePlayer(ac,90f, Buffer.SINE);
		Gain sineGain = new Gain(ac, 1, (UGen) wav_beads[1]);
		sineGain.addInput( (UGen) wav_beads[2]);
		Glide gainValue = new Glide(ac,  freq[4], 100); 
		Gain g = new Gain(ac, 1, gainValue);
		g.addInput(sineGain); // connect the filter to the gain
		//obj.carrier,obj.modulator, obj.sinegain(modulator), obj.input.carrier,  obj.panLFO,obj.reverb
		// create a WavePlayer to control the gain
		effect_wav[5]=g;
	
	}; 


}




//Here functions/routines will be created I will have  at least 3 wave files. 
	
	
	
	
	
	
	
	

}
