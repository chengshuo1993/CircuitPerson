import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
public class He {
	 /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
	static int []PPGInputdata=new int[4000]; 
	static int []Inputdata = new int[400];
	
	
    public static void readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
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
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
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
	 * 滑动平均滤波器
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
	 * 滤基线
	 * 
	 */
	public static void MM_bs(int fs)
	{
		int M2=(int)(0.2*fs);
		int []B2=new int[M2];
		for(int i=0;i<M2;i++)
			B2[i]=1;
		int N1=Inputdata.length;
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
				ERm[m]=(Inputdata[n+m]-B2[m]);
			ER[n]=MininArry(ERm);
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
			OP[n]=MaxinArry(OPm);
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
			DI[n]=MaxinArry(DIm);
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
