import java.util.Arrays;
import java.util.Random;
public class ParallelMerge{
	static int cores=13;
	static int[] data=new int[100];
	static{
		Random r=new Random(6);
	for(int i=0;i<100;i++){
		data[i]=r.nextInt()/100000;
	}
	}
	//recursively mergesort the data using threads
	public static int[] recursiveMergeSort(int threads,int[] array){
		//if only one core available, 1 thread will provide maximum speed
	    if(threads == 1)
	    {
	    	return mergeSort(array);			//;  
	    }
	    //otherwise split the data into sublists to be broken down with threads and merged recursively
	    else{
	    int mp = array.length / 2;
	    //split the data evenly
	    int[] ll = Arrays.copyOfRange(array, 0, mp);
	    int[] rl = Arrays.copyOfRange(array, mp, array.length);
	    //spawn off a new thread to work on sorting half the data
	    Thread leftThread = new Thread(new Runnable(){
	    	public void run(){
	    		recursiveMergeSort(threads/2,ll);
	    	}	
	    });
	    //spawn another thread to work on sorting the other half		
	    Thread rightThread=new Thread(new Runnable(){
	    	public void run(){
	    		recursiveMergeSort(threads/2,rl);
	    	}
	    });
	    //start the threads
	    leftThread.start();
	    rightThread.start();
	    try {
	        leftThread.join();
	        rightThread.join();
	    } catch (InterruptedException e) {
	    	System.out.println("unexpected error!");
	        e.printStackTrace();
	    }
	    //return merged data
	    return merge(ll, rl,array);
	    }
	}

	
	public static void main(String[]args) throws InterruptedException{

		long startTime = System.currentTimeMillis();
		System.out.println(Arrays.toString(data));
		System.out.println(Arrays.toString(ParallelMerge.recursiveMergeSort(cores,data)));
		ParallelMerge.recursiveMergeSort(cores,data);

		long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(cores+"-thread MergeSort takes: " + (float)elapsedTime/1000 + " seconds");

	}	
		//mergesort function
		public static int[] mergeSort(int[] d) {
			// TODO Auto-generated method stub
			//while the list has more than one element, divide
			if(d.length<=1)
				return d;
						//d;
				//set the midpoint
				int mp=d.length/2;
				//create a left list and a right list either side of the midpoint to be recursively split and then
				//conquered once they are of length one
				int[]ll= Arrays.copyOfRange(d, 0, mp);
				int[]rl= Arrays.copyOfRange(d, mp, d.length);
				//ll=
				mergeSort(ll);
				//rl=
				mergeSort(rl); 
				//return 
				return merge(ll,rl,d);
			
		}
		//merge function
		public static int[] merge(int[] ll,int[] rl,int[]data1){
				//System.out.println(Arrays.toString(ll));
				//System.out.println(Arrays.toString(rl));
				//int[] data1=new int[ll.length+rl.length];
				//initialise the counter variables
				int i=0;
				int j=0;
				int k=0;
				//i holds the position for the right list, j for the left list
				//use a while loop to check that elements are still left in each list
				//k holds the position of the conquered array
				while(i<ll.length && j<rl.length){
					//conquer array by adding the smallest element from the leftlist or rightlist until 
					//one of the lists is empty, in which case add the rest of the elements of the non empty list
					if (ll[i]<=rl[j]){
						data1[k]=ll[i];
						i+=1;
					}
					else{
						data1[k]=rl[j];
						j+=1;
					}
					k+=1;
				}
				
				while(i<ll.length){
					data1[k]=ll[i];
					i+=1;
					k+=1;
				}
				while (j<rl.length){
					data1[k]=rl[j];
					j+=1;
					k+=1;
					
				}
			return data1; 
		}
}
 
