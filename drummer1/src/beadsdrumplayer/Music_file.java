package beadsdrumplayer;

public class Music_file {

	public float[] mNote = new float[100];
	public float[] mTime = new float[100];
	public float[] mVel = new float[100];

	public float[] mChannel = new float[100];



Music_file(int file_n){
	
	
	 read_playfile st_mus = new read_playfile(file_n);
	 
	 for (int i=0; i < st_mus.musr1.length-1;i++){
	 
	if (i==1){	 
		mTime[i]=0;
		st_mus.musr1[i][3]=0;
		}
	if (i>1){	 
			mTime[i]=(st_mus.musr1[i+1][3]*1000)-(st_mus.musr1[i][3]*1000);		
			}
	 mNote[i]=st_mus.musr1[i][1];
	 mVel[i]=st_mus.musr1[i][2];
	 mChannel[i]=st_mus.musr1[i][4];
	 
	 }
	 
 		
   
    
	
	
	 
	 
	 
	 
	 
	 
	
	
}
	
//float return_notetime(int m)	{

//It is better to preload rather than doing the calculations now. 
	//float kle=0; int klp = 0;
	//kle= ((mTime[m+1])*(1000))- ((mTime[m])*(1000));
	//klp = (int)kle;
	//dts.setDelay(klp);
 // return klp;


//}
}
