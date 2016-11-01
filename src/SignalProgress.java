import java.util.ArrayList;


public class SignalProgress {
	private static int[] PPGInputdata ;
    private static int InputDataLeng=0;
    public static float[] OutputData;
    public  static int OutputDataLeng=0;
    static int fs=125;

    public void InputIntData(int InputArray[],int leng){
        this.InputDataLeng=leng;
        this.PPGInputdata=InputArray;
    }
    /*
	 * 冒泡排序法
	 * 
	 * 
	 */
	public static void bubbleSort() {
		int temp; // 记录临时中间值
		int size = Middletemp.length; // 数组大小
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size-i-1; j++) {
				if (Middletemp[j] > Middletemp[j+1]) { // 交换两数的位置
					temp = Middletemp[j];
					Middletemp[j] = Middletemp[j+1];
					Middletemp[j+1] = temp;
				}
			}
		}
	}
	/*
	 * 中值滤波器
	 */
	static  int MiddleLength=2;
	static  int [] Middletemp = new int[MiddleLength*2+1];
	private static void MiddleFilter()
	{
		for(int i=MiddleLength;i<PPGInputdata.length-MiddleLength;i++)
		{
			for(int j=0;j<MiddleLength*2+1;j++)
				Middletemp[j]=PPGInputdata[i-MiddleLength+j];
			bubbleSort();
			PPGInputdata[i]=Middletemp[MiddleLength];
		}

	}
    /*
	 * 
	 * 滤基线
	 * 
	 */
	public static void MM_bs(int fs)
	{
		int M2=(int)(0.2*fs);
		int []B2=new int[M2];
		for(int i=0;i<M2;i++)
			B2[i]=1;
		int N1=InputDataLeng;
		//腐蚀
		int[]ER=new int[N1];
		for(int i=0;i<ER.length;i++)
			ER[i]=0;
		int[]ERm=new int[M2];
		for(int i=0;i<ERm.length;i++)
			ERm[i]=0;
		for(int n=0;n<N1-M2;n++)
		{
			for(int m=0;m<M2;m++)
				ERm[m]=(PPGInputdata[n+m]-B2[m]);
			ER[n]=MininArry(ERm,ERm.length);
		}
		//开运算
		int[]OP=new int[N1];
		for(int i=0;i<OP.length;i++)
			OP[i]=0;
		int[]OPm=new int[M2];
		for(int i=0;i<OPm.length;i++)
			OPm[i]=0;
		for(int n=M2;n<N1;n++)
		{
			for(int m=0;m<M2;m++)
				OPm[m]=(ER[n-m]+B2[m]);
			OP[n]=MaxinArry(OPm,OPm.length);
		}
		//膨胀
		int[]DI=new int[N1];
		for(int i=0;i<DI.length;i++)
			DI[i]=0;
		int[]DIm=new int[M2];
		for(int i=0;i<DIm.length;i++)
			DIm[i]=0;
		for(int n=M2;n<N1;n++)
		{
			for(int m=0;m<M2;m++)
				DIm[m]=(OP[n-m]+B2[m]);
			DI[n]=MaxinArry(DIm,DIm.length);
		}
		//闭运算
		int[]CL=new int[N1];
		for(int i=0;i<CL.length;i++)
			CL[i]=0;
		int[]CLm=new int[M2];
		for(int i=0;i<CLm.length;i++)
			CLm[i]=0;
		for(int n=M2;n<N1;n++)
		{
			for(int m=0;m<M2;m++)
				CLm[m]=(DI[n-m]+B2[m]);
			CL[n]=MininArry(CLm,CLm.length);
		}
		for(int i=0;i<PPGInputdata.length;i++)
			PPGInputdata[i]=PPGInputdata[i]-CL[i];
		/*for(int i=0;i<CL.length;i++)
			System.out.println(CL[i]);*/
	}
	public static void filppgdetection(int InputArray[])
	{
		//int N=InputArray.length;
		int lengthppg = InputArray.length;
		float[] dif1ppg=new float[lengthppg-1];
		//Calculate 1st derivarive and it's abs
		for(int i=1;i<lengthppg;i++)
		{
			dif1ppg[i-1]=InputArray[i]-InputArray[i-1];
			if(dif1ppg[i-1]<0)
				dif1ppg[i-1]=0-dif1ppg[i-1];
		}
		//Detection zero point
		int stepnum = lengthppg/80-1;
		int j1=0;
		ArrayList dif1ppgzero=new ArrayList();
		{
			int WindowsLength = 80;
			float[]Temp=new float[WindowsLength];
			
			for(int i=0;i<stepnum;i++)
			{
				float zerovalue=100000;
				int zeroposition=0;
				System.arraycopy(dif1ppg, i*WindowsLength, Temp, 0, WindowsLength);
				for(int j=0;j<Temp.length;j++)
				{
					if(Temp[j]<zerovalue)
					{
						zerovalue=Temp[j];
					    zeroposition=j;
					}
				}
				if(zerovalue<0.7)
				{
					dif1ppgzero.add(zeroposition+80*i);
					j1++;
				}	
			}
		}
		///////////////////////////////////////////////////
		int f=1;
		int p=1;
		float meanppg=MeaninArry(InputArray,InputArray.length);
		ArrayList foot_position0 = new ArrayList();
		ArrayList peak_position0 = new ArrayList();
		for(int i=0;i<j1-2;i++)
		{
			int position=Integer.valueOf(dif1ppgzero.get(i).toString());
			if(InputArray[position]<meanppg)
			{
				foot_position0.add(position);
				f++;
			}
			else
			{
				peak_position0.add(position);
				p++;
			}
		}
		// peak or foot, remove error points  
		int p2=0;
		ArrayList foot_position = new ArrayList();
		ArrayList peak_position = new ArrayList();
		for(int i=1;i<p-1;i++)
		{
			if(Integer.valueOf(peak_position0.get(i).toString())-Integer.valueOf(peak_position0.get(i-1).toString())>50)
				peak_position.add(peak_position0.get(i));
		}
		int f2=0;
		for(int i=1;i<f-1;i++)
		{
			if(Integer.valueOf(foot_position0.get(i).toString())-Integer.valueOf(foot_position0.get(i-1).toString())>50)
				foot_position.add(foot_position0.get(i));
		}
		///////////////////////////////////////////////////////////
		int p1=peak_position.size();
		int f1=foot_position.size();
		//error removing
		int p3=0;
		ArrayList foot_position2 = new ArrayList();
		ArrayList peak_position2 = new ArrayList();
		for(int i=1;i<p1;i++)
		{
			int max_position=0,max_value=0;
			int temp=0;
			for(int j=0;j<40;j++)
			{
				temp=Integer.valueOf(peak_position.get(i).toString())-20+j;
				if(InputArray[temp]>max_value)
				{
					max_value=InputArray[temp];
					max_position=temp;	
				}	
			}
			peak_position2.add(max_position+1);
			max_value=0;
		}
		for(int i=1;i<f1;i++)
		{
			int min_position=10000,min_value=100000;
			int temp=0;
			for(int j=0;j<60;j++)
			{
				temp=Integer.valueOf(foot_position.get(i).toString())-30+j;
				if(InputArray[temp]<min_value)
				{
					min_value=InputArray[temp];
					min_position=temp;
				}				
			}
			foot_position2.add(min_position+1);
			min_value=100000;
		}
//		for(int i=0;i<j1;i++)
			System.out.println("end");
//		float[]temp1=new float[j1-1];
//		System.arraycopy(dif1ppgzero, 0, temp1, 0, j1-1);	
		
		
	}
    public static float HeartRate(int InputArray[], int leng)
    {

        float[] InputArrayToOne=new float[leng];
        int MaxInArray=MaxinArry(InputArray,leng);
        for(int i=0;i<leng;i++)
            InputArrayToOne[i]=(float)InputArray[i]/MaxInArray;
        for(int i=0;i<leng;i++)
        {
            if(InputArrayToOne[i]>0.45)
                InputArrayToOne[i]=1;
            else
                InputArrayToOne[i]=0;
        }
        int UpPostionNum=0;
        int[] UpPostion=new int[10];
        for (int i=0;i<leng;i++)
        {
            if((InputArrayToOne[i]==1)&&(InputArrayToOne[i-1]==0))
            {
                UpPostion[UpPostionNum]=i;
                UpPostionNum++;
            }
        }
        int HeartRateNum=1;
        float TempRate=0;
        float[] HeartRate=new float[10];//9位置用于存储此次数组中存在几个心率的计数，后面保存心率值
        int ErrorDect=0;//0代表没有误检，1代表前一个为噪声心率过快，2代表前面没检测到，心率过慢
        for(int i=1;i<UpPostionNum;i++)     //计算心率检点
        {
            if(UpPostionNum<2)
                return 61;
            if(ErrorDect==1)
                TempRate=(60*fs)/(float)(UpPostion[i]-UpPostion[i-2]);
            else
                TempRate=(60*fs)/(float)(UpPostion[i]-UpPostion[i-1]);
            if (TempRate>110)
            {
                ErrorDect=1;
                continue;
            }
            else if(TempRate<45)
            {
                TempRate=(2*60*fs)/(float)(UpPostion[i]-UpPostion[i-1]);
                HeartRate[HeartRateNum]=TempRate;
                HeartRateNum++;
            }
            else
            {
                HeartRate[HeartRateNum]=TempRate;
                HeartRateNum++;
                ErrorDect=0;
            }

        }
        HeartRate[9]=HeartRateNum;
        float SumHeartRate=0;
        for(int i=0;i<HeartRateNum;i++)
            SumHeartRate+=HeartRate[i];
        OutputData=InputArrayToOne;
        OutputDataLeng=leng;
        return SumHeartRate/(float)(HeartRateNum-1);       //返回平均心率
    }
    private static float MeaninArry(int[]array,int DataLeng)
    {
    	long sum=0;
    	for(int i=0;i<DataLeng;i++)
    		sum+=array[i];
    	return (float)sum/DataLeng;
    }
    private static int MaxinArry(int[] array,int DataLeng)
    {
        int max = array[0];
        for(int i = 1; i <DataLeng ; i++){
            if(max < array[i])
				/*if(array[i]>12000000)
					array[i]=array[i-1];
				else*/
                max = array[i];
        }
        return max;
    }

    private static int MininArry(int[] array,int DataLeng)
    {
        int min = array[0];
        for(int i = 1; i < DataLeng; i++){
            if(min > array[i]) min = array[i];
        }
        return min;
    }
}


