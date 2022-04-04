import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		Heap heap = new Heap(n);
		for(int i = 0; i < n; i++){
			long next = sc.nextLong();
			if (next == 0){
				heap.insert(sc.nextLong());
			}
			if (next == 1){
				System.out.println(heap.extract());
			}
		}
	}
}

class Heap {
	private int n = 0;
	private long[] a;
	
	public Heap(int num){
		a = new long[num];
	}
	
	public void insert(long x){
		a[n++] = x;
		int i = n - 1;
		while (i > 0 && a[i] > a[(i - 1) / 2]){
			long temp = a[i];
			a[i] = a[(i - 1) / 2];
			a[(i - 1) / 2] = temp;
			i = (i - 1) / 2;
		}
		/*for (int j = 0; j < n; j++){
			System.out.print(a[j]+" ");
		}*/
	}
	
	public long extract(){
		long res = a[0];
		a[0] = a[--n];
		int i = 0;
		while(true){
			int j = i;
			if (2 * i + 1 < n && a[2 * i + 1] > a[j]){
				j = 2 * i + 1;
			}
			if (2 * i + 2 < n && a[2 * i + 2] > a[j]){
				j = 2 * i + 2;
			}
			if (j == i){
				break;
			} else {
				long temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				i = j;
			}
		}
		/*for (int j = 0; j < n; j++){
			System.out.print(a[j]+" ");
		}*/
		return res;
	}
}