import java.util.Comparator;
import java.util.PriorityQueue;

//class to find median and efficiently store data
public class Median {
	PriorityQueue<Double> minHeap;		//Store the larger half using a min heap
	PriorityQueue<Double> maxHeap;		//Store the smaller half using a max heap
	int size;							//number of contributions
	int total;							//total amount of contributions
	//Initialize structure in constructor
	public Median() {
		minHeap = new PriorityQueue<Double>(new Comparator<Double>() {
			public int compare(Double a, Double b) {
				return (int)(a - b);
			}
		});
		maxHeap = new PriorityQueue<Double>(new Comparator<Double>() {
			public int compare(Double a, Double b) {
				return (int)(b - a);
			}
		});
		size = 0;
		total = 0;
	}
	//Load data
	public void addNum(double num) {
		// numbers add to max heap, then pop to min heap
		size++;
		total += num;
		maxHeap.add(num);
		minHeap.add(maxHeap.poll());
		if (minHeap.size() > maxHeap.size() + 1) {
			maxHeap.add(minHeap.poll());
		}
	}
	//Output median
	public int findMedian() {
		double median = 0;
		int intMedian = 0;
		if (size % 2 == 0) {
			median = (maxHeap.peek() + minHeap.peek()) / 2;
		} else {
			median = minHeap.peek();
		}
		//Round rules: larger decimal > 0.5 round up, otherwise drop it.
		if(median -(int)median >= 0.5){
			intMedian = (int)median + 1;
		}
		else{
			intMedian = (int)median;
		}
		return intMedian;
	}
}
