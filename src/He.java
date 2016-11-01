import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
public class He {
	 /**
     * ���ܣ�Java��ȡtxt�ļ�������
     * ���裺1���Ȼ���ļ����
     * 2������ļ��������������һ���ֽ���������Ҫ��������������ж�ȡ
     * 3����ȡ������������Ҫ��ȡ�����ֽ���
     * 4��һ��һ�е������readline()��
     * ��ע����Ҫ���ǵ����쳣���
     * @param filePath
     */
	static int []PPGInputdata=new int[4000]; 
	static int []Inputdata = new int[400];
	
	
    public static void readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//���ǵ������ʽ
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    int i=0;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        //System.out.println(lineTxt);
                    	Inputdata[i]=Integer.parseInt(lineTxt);
                        i++;
                    }
                    read.close();
        }else{
            System.out.println("�Ҳ���ָ�����ļ�");
        }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }
     
    }
    static int MaxinArry(int[] array)
	{
		int max = array[0];
		for(int i = 1; i < array.length; i++){
			if(max < array[i])
					max = array[i];
		}
		return max;
	}

	static int MininArry(int[] array)
	{
		int min = array[0];
		for(int i = 1; i < array.length; i++){
			if(min > array[i]) min = array[i];
		}
		return min;
	}
	/*
	 * ð������
	 * 
	 * 
	 */
	public static void bubbleSort() {
		int temp; // ��¼��ʱ�м�ֵ
		int size = Middletemp.length; // �����С
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size-i-1; j++) {
				if (Middletemp[j] > Middletemp[j+1]) { // ����������λ��
					temp = Middletemp[j];
					Middletemp[j] = Middletemp[j+1];
					Middletemp[j+1] = temp;
				}
			}
		}
	}
	/*
	 * ��ֵ�˲���
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
	 * ����ƽ���˲���
	 */
//	private static void MoveMeanFilter()
//	{
//		int MeanLength=5;
//		long Sum=0;
//		for(int i=MeanLength;i<InputDataLeng-MeanLength;i++)
//		{
//			for(int j=0;j<MeanLength*2+1;j++)
//				Sum+=PPGInputdata[i-MeanLength+j];
//			PPGInputdata[i]=(int)(Sum/(MeanLength*2+1));
//			Sum=0;
//		}
//	}
	/*
	 * 
	 * �˻���
	 * 
	 */
	public static void MM_bs(int fs)
	{
		int M2=(int)(0.2*fs);
		int []B2=new int[M2];
		for(int i=0;i<M2;i++)
			B2[i]=1;
		int N1=Inputdata.length;
		//��ʴ
		int[]ER=new int[N1];
		for(int i=0;i<ER.length;i++)
			ER[i]=0;
		int[]ERm=new int[M2];
		for(int i=0;i<ERm.length;i++)
			ERm[i]=0;
		for(int n=0;n<N1-M2;n++)
		{
			for(int m=0;m<M2;m++)
				ERm[m]=(Inputdata[n+m]-B2[m]);
			ER[n]=MininArry(ERm);
		}
		//������
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
			OP[n]=MaxinArry(OPm);
		}
		//����
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
			DI[n]=MaxinArry(DIm);
		}
		//������
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
			CL[n]=MininArry(CLm);
		}
		for(int i=0;i<Inputdata.length;i++)
			Inputdata[i]=Inputdata[i]-CL[i];
		/*for(int i=0;i<CL.length;i++)
			System.out.println(CL[i]);*/
	}
	static int []aa={2,3,4,6,2,1};
	static MM_bs MytestClass;
	static SignalProgress MySignalProgress;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "E:\\Cloud\\study\\Circuit Person\\JAVA\\Test2\\src\\log.txt";
        readTxtFile(filePath);
		System.out.println("hello world!");
//		MiddleFilter();
//		
//		for(int i=0;i<Inputdata.length;i++)
//			Inputdata[i]=PPGInputdata[i];
//		MM_bs(125);
//		for(int i=0;i<Inputdata.length;i++)
//		{
//			//System.out.print(i+" ");
//			System.out.println(Inputdata[i]);
//		}
//		
		/*for(int i=0;i<Inputdata.length;i++)
			System.out.println(Inputdata[i]);*/
		System.out.println(MySignalProgress.
				HeartRate(Inputdata,Inputdata.length));

		/*for(int i=0;i<PPGInputdata.length-1;i++)
		{
			System.out.print(i+" ");
			System.out.println(PPGInputdata[i]);
		}*/
		
		//String Datapath="PPG_Data.txt";

	}

}
