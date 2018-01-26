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
	
	
	public Amp_Mod1 makwav2[] = new Amp_Mod1[5];
	public GRa_Player makwav3;
	public Simp_wav makwav4[]= new Simp_wav[5];
	public Wave_generation makwav[]= new Wave_generation[5];
	//public GRa_Player makwav3x,makwav3x1,makwav3x2;
	AudioContext ac[] = new AudioContext[5];
	Music_file obt_seq[] = new Music_file[4]; 
	public static void main(String[] args)
		
	{
		read_playfile rp = new read_playfile(1);
		drum_machine_01 synth = new drum_machine_01();
		synth.setup();
	}
	 int delay = 150;//In order to go at faster speeds you will have to shorten the envelope
	 Timer dts;
	// construct the synthesizer
	public void setup()
	{
		for (int i= 0; i <5; i++){
			ac[i] = new AudioContext();
		
		}
		
		for (int i= 0; i <5; i++){

		makwav[i] = new Wave_generation(ac[0]);
		makwav[i].wave_gen(ac[0]);
		makwav2[i] = new Amp_Mod1(ac[1]);
		makwav2[i].wave_gen(ac[1]);
		makwav4[i] = new Simp_wav(ac[2]);
		makwav4[i].wave_gen(ac[2]);
		
		
		}
	ac[0].start();
	ac[1].start();
	ac[2].start();
	ac[3].start();
	ac[4].start();
   
	obt_seq[1]= new Music_file(1);
	obt_seq[2]= new Music_file(2);
	obt_seq[3]= new Music_file(3);
	dts = new Timer(delay, taskPerformer);
		   dts.start();
	 
	
	
	}
	
	//Create the panner. 
	
	int iter1=1;int ltp=0;int phony_iter=0;float noteconv;int last_note[]= new int[4]; int last_n; int open_ph [] = new int[4];
	int note_increase=0; int iternote=0; int msz[] = {0,0,0,0,4,0,0,0}; int result1=0;
	
	 ActionListener taskPerformer = new ActionListener(){public void actionPerformed(ActionEvent evt){
	        
	        	
	        	makwav[1].keyDown1(0,iter1,1);
	        	noteconv=obt_seq[1].mNote[iter1]; 
	        	
	        	//So this is just for one note I will probably put this in a function.  
	        	
	        	for (int i= 0; i< last_note.length;i++){
	        	if (noteconv != last_note[i] && last_note[i]==0){
	        	  makwav[phony_iter].keyDown1((int)noteconv, iter1, 1);  //The last item will be changed to volume  127
	        		open_ph[phony_iter]= (int) noteconv;
	        	  phony_iter++;
	        		last_note[i]=(int) noteconv;
	        		i = last_note.length;
	        		break;
	        	}
	         	if (noteconv == last_note[i]){ 
	        		for (int k= 0; k < open_ph.length;k++){
	        		if (open_ph[k]==noteconv){ result1= k; open_ph[k]=0;//Frees up the polyphony space }
	        		}
	         		last_note[i]=0;	//Frees up that position for the last note
	        		 makwav[result1].keyDown1((int) noteconv, iter1, 0); //Here is where you make the velocity to 0. 
	        		 i = last_note.length;
		        		break;
	        		}
	         	}

	        	}
	         	if (phony_iter>3){phony_iter=0;};
	        	
	        	
	        	last_n= (int)noteconv;
	        	iter1++; 		
	  	        ltp++;
	        	
	        	
	        	
	        	
	        		        	
	        	//makwav2.keyDown1(35, 1,  ac);
	        	
	        		//makwav2x.keyDown1(49, 1, ac1);
	       
	  
	        	
	        	
	        	
	        	
	        	
	        	
	        	//To make polyphone that is not too costly I am going to have to do a sample and hold.  
	        	//Meaning I am going to take an array of note pitch values every.
	        	//So every time a note pitch is received it is saved within this array.
	        	//if the next signal received is the same pitch but with velocity off the  note is released but if the note pitch is  
	        	//different  it is  not deleted and the and the note is not keyed off until that same note pitch is inputed.  
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	//makwav3.keyDown1(1, 1);

	        	
	        	
	        	
	        	
	        	
	        	
	        	// makwav3.keyDown1(1,1,ac);
	        // makwav4.keyDown1(68,1);
	        	//makwav2.keyDown1(25+note_increase, 1);
	        	
	        	//switch (iternote){
	        	//case  2:
	        	//	makwav.keyDown1(0,m,2f);
	        	//	break;
	        	
	        	//case 5:	
	        	
	        	//	makwav.keyDown1(4,m,2f);
	        	//	break;
	        	
	        	//case 7:
	        
	        	//	makwav.keyDown1(4,m,2f);
	        	//	break;
	        	//
	        	//case 8:
	        	
	        	//	makwav.keyDown1(0,m,2f);
	        	///	iternote=0;break;
	        	//}
	        	
	        	
	        	//makwav.keyDown2(0,m,1);
	        	iternote++;
	        	note_increase= note_increase+5;
	        	
		    }
	 };
}
	

	        

