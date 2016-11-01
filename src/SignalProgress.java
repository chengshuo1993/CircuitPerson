
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
        float[] HeartRate=new float[10];//9λ�����ڴ洢�˴������д��ڼ������ʵļ��������汣������ֵ
        int ErrorDect=0;//0����û����죬1����ǰһ��Ϊ�������ʹ��죬2����ǰ��û��⵽�����ʹ���
        for(int i=1;i<UpPostionNum;i++)     //�������ʼ��
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
        return SumHeartRate/(float)(HeartRateNum-1);       //����ƽ������
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


