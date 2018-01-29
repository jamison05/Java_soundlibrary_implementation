
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleAudioFormat;
import net.beadsproject.beads.data.audiofile.AudioFileType;
import net.beadsproject.beads.data.audiofile.AudioFileWriter;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.Panner;
import net.beadsproject.beads.ugens.RecordToSample;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;
import net.beadsproject.beads.ugens.WavePlayer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class Granular_Example_01
{
	
	static public float[][] file_holder1 = new float[15][500]; 
	public static void main(String[] args)
	{
		//Try 4 different sound modulations.  
		
		String filename  = "nit_1step2_sound_mod.dat";
		file_holder1[1] = get_fuzzy_strings(filename);
		filename = "nit_1step2_sound_mod.dat";
		file_holder1[2] = get_fuzzy_strings(filename);
		filename = "nit_1step3_sound_mod.dat";
		file_holder1[3] = get_fuzzy_strings(filename);
		filename = "nit_1step4_sound_mod.dat";
		file_holder1[4] = get_fuzzy_strings(filename);	
		filename = "nit_2step2_sound_mod.dat";
		file_holder1[5] = get_fuzzy_strings(filename);
		filename = "nit_2step3_sound_mod.dat";
		file_holder1[6] = get_fuzzy_strings(filename);
		filename = "nit_2step4_sound_mod.dat";
		file_holder1[7] = get_fuzzy_strings(filename);
		
		
		filename = "nit_2step2_sound_mod.dat";
		file_holder1[4] = get_fuzzy_strings(filename);	
		
		filename = "nit_2step3_sound_mod.dat";
		file_holder1[5] = get_fuzzy_strings(filename);
		
		filename = "nit_2step4_sound_mod.dat";
		file_holder1[6] = get_fuzzy_strings(filename);
		
		filename = "nit_3step2_sound_mod.dat";
		file_holder1[7] = get_fuzzy_strings(filename);
		
		filename = "nit_3step3_sound_mod.dat";
		file_holder1[8] = get_fuzzy_strings(filename);
		
		filename = "nit_3step4_sound_mod.dat";
		file_holder1[9] = get_fuzzy_strings(filename);
		
		filename = "nit_4step2_sound_mod.dat";
		file_holder1[10] = get_fuzzy_strings(filename);
		
		filename = "nit_4step3_sound_mod.dat";
		file_holder1[11] = get_fuzzy_strings(filename);
		
		
		// instantiate the AudioContext and Audio Player
		
		Generate_audio  gen_audio = new Generate_audio();
		gen_audio.generate_audio1(2, audio_modulation(1,1));  //This is where I select if from the code
		// load the source sample from a file

			}

public static float[] get_fuzzy_strings( String filename ){
	
	
	
    String content = null;
    float [] fuzzy_value = new float[200];
    File file = new File(filename); //for ex foo.tx
    FileReader reader = null;
    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        
	        reader.read(chars);
	        content = new String(chars);
	        String[] content_ar= content.split(",");
	        int it=0;
	        for (String value: content_ar){
	        	fuzzy_value[it++] = Float.parseFloat(value);
	        } 
	        it=0;
	        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
	        if(reader !=null){try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }    
    }
  
	return fuzzy_value;
	
	
}


	static String audio_modulation (int mode_1, int step){
		
			//String var1 = "C:\\Users\\deepj\\workspace\\HashMap_utilityAudio\\src\\kl9newlat.wav";
			String var1_start = "C:\\Users\\deepj\\OneDrive\\Cajun_taste\\Music_Renderings\\Audio_1O";
			String temp_var1, temp_var2, temp_var3, concat; 
			temp_var1 ="\\aud";
			temp_var2 = "_phase";
			concat = var1_start + temp_var1 + mode_1 + temp_var2 + step;
			return concat;
			
		}
	
	
}