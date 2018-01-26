
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.core.UGen;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.Phasor;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.WaveShaper;
public  class Implment_music{
public float preset_values[];
public float preset_values2[];
public Bead[][] send_to_g = new Bead[8][4];	
public Bead[][] send_to_env = new Bead[4][10];
Bead[] slv1 = new Bead[9];
Bead[] slv2 = new Bead[9];
 Bead tm[] = new Bead[40];
public AudioContext ac = new AudioContext();
public Waves_1 imp_wave = new Waves_1();
Gain sineGain;
Envelope gainEnvelope;
Glide gainGlide;
float p[];

void receive_matfile(){
	float li_pref[] = preset_values;
	
		//This is where you start implementing the waves.
		
		Waves_1 imp_wave = new Waves_1();
	//So I understand it  the imp_wave is a whole new object 
											//but the call to imp wave is not an object even though 
	//wave player buffers
	
		for (int i=1;i<=3;i++){//I will just specifically this now since it is like this. 
				imp_wave.wave_generate(i, ac,(int) li_pref[i+10], 440);//Will change the frequency if it is needed. 
				send_to_g[i][1]=imp_wave.wav_beads[i];
			
				 //(int) preset_values[9+i]
			}
		
			
		
		
	
	if (li_pref[1]==1){//wave player shapes
		
		for (int i =0;i<=3; i++){
		imp_wave.wave_shape_generate(i, ac, (int) li_pref[13+i], 20);	
		send_to_g[i][2]=imp_wave.wav_shape[i];
		}
	}
	
	//Obligatory envelopes for music so the IF condition is removed. 
	
	for (int i=1;i<=3;i++)	{
		
		imp_wave.constr_env_n(i, ac);	
		
		send_to_g[i][3]=imp_wave.env_wav[i];
	
	}
	

	
	if (li_pref[8]==1 || li_pref[9]==1 ||li_pref[10]==1){//Total effects 
		
		float[] wav_eff = {li_pref[29],li_pref[30],li_pref[31],li_pref[32],li_pref[33]};
		int[] turn_on_eff = {(int) li_pref[8],(int) li_pref[9],(int) li_pref[10]};
		imp_wave.constr_tot_eff(turn_on_eff, ac,wav_eff);	
		send_to_g[1][4]=imp_wave.phasor_t;
		send_to_g[2][4]=imp_wave.panner_t;
		send_to_g[3][4]=imp_wave.reverb_t;
	}
	
	if (li_pref[3]==1 || li_pref[4]==1 ||li_pref[5]==1||li_pref[6]==1||li_pref[7]==1){//wave player shapes
		
		float[] wav_eff = {li_pref[34],li_pref[35],li_pref[36],li_pref[37],li_pref[38]};
		int[] turn_on_eff = {(int) li_pref[3],(int) li_pref[4],(int) li_pref[5],(int) li_pref[6],(int) li_pref[7]};
		imp_wave.constr_tot_eff(turn_on_eff, ac,wav_eff);	
		send_to_g[1][5]=imp_wave.effect_wav[1];
		send_to_g[2][5]=imp_wave.effect_wav[2];
		send_to_g[3][5]=imp_wave.effect_wav[3];
		send_to_g[4][5]=imp_wave.effect_wav[4];
		send_to_g[5][5]=imp_wave.effect_wav[5];
		}
	//item #1,2, 5   are not true freqnuences but actually gains  
}
	
	void music_implements1(){
		
		
		
	}

	
	public Buffer generateBuffer(int arg0) {
		
	   	
		   int iter=0;
		   float[] kls = new float[300];
		   float[] kls2 = new float[300];
		   BufferFactory jk;
		   String filename, filename2;
		   filename="C:\\Users\\Joshua\\Documents\\MATLAB\\prefmread.txt";
		   filename2="C:\\Users\\Joshua\\Documents\\MATLAB\\prefmread2.txt";
		   Path file = Paths.get(filename);
		   Path file2 = Paths.get(filename2);
		   try (InputStream in = Files.newInputStream(file);
		    	    BufferedReader reader =
		    	      new BufferedReader(new InputStreamReader(in))) {
		    	    String line = null;
		    	    while ((line = reader.readLine()) != null) {

		    	    	
		    	    	System.out.println(line);
		    	    	float result = Float.parseFloat(line);			
		    	        kls[iter++]= result;
		    	    	
		    	    }
		    	} catch (IOException x) {
		    	    System.err.println(x);
		    	}
		    	this.preset_values=kls;
	
		    	 iter=0;
		    	try (InputStream in = Files.newInputStream(file2);
				    	    BufferedReader reader =
				    	      new BufferedReader(new InputStreamReader(in))) {
				    	    String line = null;
				    	    while ((line = reader.readLine()) != null) {

				    	    	
				    	    	System.out.println(line);
				    	    	float result = Float.parseFloat(line);			
				    	        kls2[iter++]= result;
				    	    	
				    	    }
				    	} catch (IOException x) {
				    	    System.err.println(x);
				    	}
				    	this.preset_values2=kls2;
				
	return null;
	}

void  implement_gain(){
gainEnvelope = new Envelope(ac, 0.0f);	
float klt[]= new float[4];
klt[1]=(float) 0.03;//!!!Replace this with gains in file 2. 
klt[2]=(float) 0.002;
klt[3]=(float) 0.1;
float klt_tr;	


	for (int i=0;i<=3;i++){ 
	klt_tr=klt[i];
	gainGlide = new Glide(ac, klt_tr, 100.0f);  //3rd value does not mean anything in a continous function
	sineGain = new Gain(ac,1, gainGlide);	
	tm[i]=sineGain;
	} 
	for (int i=3;i<=6;i++){ 
	    sineGain= new Gain(ac,1,(UGen) send_to_g[i][3]);
		tm[i]=sineGain;  
	}


	((UGen) tm[1]).addInput((UGen) send_to_g[1][1]);
	((UGen) tm[2]).addInput((UGen) send_to_g[2][1]);
	((UGen) tm[3]).addInput((UGen) send_to_g[3][1]);
	
if (preset_values[1]==1){//wave player shapes	
	for (int i=0;i<=3;i++){ 
		((UGen) send_to_g[i][2]).addInput(((UGen) tm[i]));
		}
}   	
	
if (preset_values[1]==0){//wave shapees with envelopes.  	
	for (int i=3;i<=6;i++){ 
		((UGen) tm[i]).addInput((UGen) tm[i-3]); //This sine gain envelope adds to another sine gain to control the level of the envelope
		}
}   	



if (preset_values[1]==1){//wave player shapees with envelopes.  	
	for (int i=3;i<=6;i++){ 
		((UGen) tm[i]).addInput((UGen) send_to_g[i-3][2]);
		}
}   	
int y1_count=0;int y2_count=0;	int iterbn=0;//These are total effects.  
if (preset_values[8]==1 || preset_values[9]==1 ||preset_values[10]==1){//Total effects 
	if (preset_values[8]==1){
		tm[7]=send_to_g[1][4]; tm[8]=send_to_g[1][4]; tm[9]=send_to_g[1][4];//7 8 9 is being added on the phasor 
		if (preset_values[1]==1){ 
			((UGen) tm[7]).addInput((UGen)tm[4]);
			((UGen) tm[8]).addInput((UGen)tm[5]);
			((UGen) tm[9]).addInput((UGen)tm[6]);
		}
	
	
	}

	if (preset_values[9]==1){
		tm[10]=send_to_g[2][4]; tm[11]=send_to_g[2][4]; tm[12]=send_to_g[2][4];

		if (preset_values[8]!=1){
		((UGen) tm[10]).addInput((UGen)tm[4]);
		((UGen) tm[11]).addInput((UGen)tm[5]);
		((UGen) tm[12]).addInput((UGen)tm[6]);
		}
		
		if (preset_values[8]==1){ 
			((UGen) tm[10]).addInput((UGen)tm[7]);
			((UGen) tm[11]).addInput((UGen)tm[8]);
			((UGen) tm[12]).addInput((UGen)tm[9]);
		}
	
	}
	if (preset_values[10]==1){
		tm[13]=send_to_g[3][4]; tm[14]=send_to_g[3][4]; tm[15]=send_to_g[3][4];
		
		if (preset_values[9]!=1 && preset_values[8]!=1){
			((UGen) tm[13]).addInput((UGen)tm[4]);
			((UGen) tm[14]).addInput((UGen)tm[5]);
			((UGen) tm[15]).addInput((UGen)tm[6]);
			}
			
			if (preset_values[8]==1 && preset_values[9]!=1){ 
				((UGen) tm[13]).addInput((UGen)tm[7]);
				((UGen) tm[14]).addInput((UGen)tm[8]);
				((UGen) tm[15]).addInput((UGen)tm[9]);
			}
			if (preset_values[9]==1){ 
				((UGen) tm[13]).addInput((UGen)tm[10]);
				((UGen) tm[14]).addInput((UGen)tm[11]);
				((UGen) tm[15]).addInput((UGen)tm[12]);
			}
		
	
	
	
	}
	
	
	for (int i=6;i<=15;i++){
		//7,8,9 ; 10,11,12 are different sets of the output of the effects,   
		if (tm[i].equals(tm[i])){
			tm[i]=slv1[i-6];	
			
			
			y1_count= y1_count+1;
		}	
	}
}
//This section works on 2nd layer of the effect input 
if (preset_values[3]==1 || preset_values[4]==1 ||preset_values[5]==1||preset_values[6]==1||preset_values[7]==1)
{//Total effects
if (preset_values[3]==1){tm[16]=send_to_g[1][5];}

if (preset_values[4]==1){tm[17]=send_to_g[2][5];
}
if (preset_values[5]==1){tm[18]=send_to_g[3][5];
}
if (preset_values[6]==1){tm[19]=send_to_g[4][5];
}
if (preset_values[7]==1){tm[20]=send_to_g[5][5];
}
}
for (int i=15;i<=20;i++){
if (tm[i].equals(tm[i])){
		tm[i]=slv2[i-15];	
		y2_count= y2_count+1;
	}	
}

	
if (y2_count >0){
	if (preset_values[3]==1 && tm[7].equals(tm[7])){((UGen) slv1[16]).addInput((UGen) tm[7]);}//This is adding input to the individual wave effectsthat is why I sepearted them. 
	if (preset_values[3]==1 && tm[10].equals(tm[10])){((UGen) slv1[16]).addInput((UGen) tm[10]);}
	if (preset_values[3]==1 && tm[13].equals(tm[13])){((UGen) slv1[16]).addInput((UGen) tm[13]);}
	if (preset_values[3]==1 && tm[7].equals(tm[7])== false){((UGen) slv1[16]).addInput((UGen) tm[4]);}//I am going to leave this code but  coould program it better. 
	
	if (preset_values[4]==1 && tm[8].equals(tm[8])){((UGen) slv1[17]).addInput((UGen) tm[8]);}
	if (preset_values[4]==1 && tm[11].equals(tm[11])){((UGen) slv1[17]).addInput((UGen) tm[11]);}
	if (preset_values[4]==1 && tm[14].equals(tm[14])){((UGen) slv1[17]).addInput((UGen) tm[14]);}
	if (preset_values[4]==1 && tm[8].equals(tm[8])== false){((UGen) slv1[17]).addInput((UGen) tm[5]);}
//There will only be one set that will be positive it cannot be
	if (preset_values[5]==1 && tm[10].equals(tm[10])){((UGen) slv1[18]).addInput((UGen) tm[10]);}
	if (preset_values[5]==1 && tm[7].equals(tm[7])){((UGen) slv1[18]).addInput((UGen) tm[7]);}
	if (preset_values[5]==1 && tm[13].equals(tm[10])){((UGen) slv1[18]).addInput((UGen) tm[13]);}
	if (preset_values[5]==1 && tm[10].equals(tm[10])== false){((UGen) slv1[18]).addInput((UGen) tm[4]);}

	if (preset_values[6]==1 && tm[8].equals(tm[8])){((UGen) slv1[19]).addInput((UGen) tm[8]);}
	if (preset_values[6]==1 && tm[11].equals(tm[8])){((UGen) slv1[19]).addInput((UGen) tm[11]);}
	if (preset_values[6]==1 && tm[14].equals(tm[8])){((UGen) slv1[19]).addInput((UGen) tm[14]);}
	if (preset_values[6]==1 && tm[11].equals(tm[8])== false){((UGen) slv1[19]).addInput((UGen) tm[5]);}
	
}
//Add the sound to AC-out 
if (y2_count <1 && y1_count <1 ){
	ac.out.addInput((UGen) tm[4]);
	ac.out.addInput((UGen) tm[5]);
	ac.out.addInput((UGen) tm[6]);
}


if (y1_count >=1 && y2_count==0){

if (preset_values[8]==1 && preset_values[9]==1 && preset_values[10] ==1){
	ac.out.addInput((UGen) tm[13]);
	ac.out.addInput((UGen) tm[14]);
	ac.out.addInput((UGen) tm[15]);
	} 	
if (preset_values[8]==1 && preset_values[9]==1 && preset_values[10] !=1){
	ac.out.addInput((UGen) tm[10]);
	ac.out.addInput((UGen) tm[11]);
	ac.out.addInput((UGen) tm[12]);
	} 


if (preset_values[8]==1 && preset_values[9]!=1 && preset_values[10] !=1){
	ac.out.addInput((UGen) tm[7]);
	ac.out.addInput((UGen) tm[8]);
	ac.out.addInput((UGen) tm[9]);
	} 
}
int filled[]= {0,0};int f_c;
if (y1_count >=1 && y2_count>=1){
	
	if (preset_values[3]==1 && preset_values[5]==1){((UGen) slv1[18]).addInput((UGen) slv1[16]);ac.out.addInput((UGen) slv1[18]); filled[0]=1;}
	if (preset_values[4]==1 && preset_values[6]==1){}{((UGen) slv1[19]).addInput((UGen) slv1[17]);ac.out.addInput((UGen) slv1[19]);filled[1]=1;}
	
	
	if (preset_values[3]==1 && preset_values[5]==0){ac.out.addInput((UGen) slv1[16]);filled[0]=1;}
	if (preset_values[4]==1 && preset_values[6]==0){ac.out.addInput((UGen) slv1[17]);filled[1]=1;}
	
	
	if (preset_values[5]==1 && preset_values[3]==0){ac.out.addInput((UGen) slv1[18]);filled[0]=1;}
	if (preset_values[6]==1 && preset_values[4]==0){ac.out.addInput((UGen) slv1[19]);filled[1]=1;}

	f_c= filled[0] + filled[1];
	switch (f_c)
	{
	case 1:
		
	case 2:
		
	}
	
	
}



//Amp modulation may be tricky but I will give this a shot. 		
		//((UGen) slv2[i]).addInput((UGen) tm[i+3]);		
		
	
	

y2_count= 0;
y1_count=0;





	
	ac.start();
	//However this will be passed to midi/key up initiations.    I am going to make gain Evenlope Global.
	for (int i=1;i<=3;i++)	{
		int add_4 =0;
		float[] env_arr = {preset_values[17+add_4],preset_values[18+add_4],preset_values[19+add_4],preset_values[20+add_4]};
		imp_wave.constr_env_ns(gainEnvelope, ac,env_arr, 1);	
		add_4 =add_4+4;
		
	}



}
//What if you create it as a function.  What will happen 	
}

