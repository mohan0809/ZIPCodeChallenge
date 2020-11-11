import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MergeIntervals {
	private static class Interval {
		private int begin;
		private int end;
		public Interval(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
		@Override
		public String toString() {
			return "Interval [i=" + begin + ", j=" + end + "]";
		}

		public int getBegin() {
			return begin;
		}

		public int getEnd() {
			return end;
		}
	}

	private static class Points implements Comparable<Points> {

		int i;
		boolean isStart;

		public Points(int i, boolean isStart) {
			super();
			this.i = i;
			this.isStart = isStart;
		}

		@Override
		public int compareTo(Points o) {

			if (this.i == o.i) {

				return this.isStart ? -1 : 1;

			}

			return this.i < o.i ? -1 : 1;
		}

	}
	private static List<Interval> mergeHelper(List<Interval> intervals) {
		List<Points> pointsList = new ArrayList<Points>();
		for (Interval interval : intervals) {
			Points p = new Points(interval.begin, true);
			Points p2 = new Points(interval.end, false);
			pointsList.add(p);
			pointsList.add(p2);
		}
		Collections.sort(pointsList);
		int num = 0;
		Points start = null;
		List<Interval> result = new ArrayList<>();
		for (Points p : pointsList) {
			if (p.isStart) {
				num++;
				if (num == 1) {
					start = p;
				}
			} else {
				num--;
				if (num == 0) {
					result.add(new Interval(start.i, p.i));
				}
			}
		}
		return result;
	}
	
	
	public static List<List<Integer>> mergeInterval(List<List<Integer>> inputs){
		
		List<Interval> intervals = inputs.stream().map(input -> {		
			    Interval interval = new Interval(input.get(0), input.get(1));
			    return interval;				
		}).collect(Collectors.toList());	
		List<Interval> result=mergeHelper(intervals);	
		return result.stream().map(interval ->
			{		
			List<Integer> intermediateResult = new ArrayList<Integer>();
			intermediateResult.add(interval.getBegin());
			intermediateResult.add(interval.getEnd());
			return intermediateResult;
		}).collect(Collectors.toList());
		
	}

public static void main(String args[]) {
		
		List<List<Integer>> input = new ArrayList<List<Integer>>();
		List<Integer> inp1 = Arrays.asList(94133,94133);
		List<Integer> inp2 = Arrays.asList(94200,94299);
		List<Integer> inp3 = Arrays.asList(94226,94399);
		input.add(inp1);
		input.add(inp2);
		input.add(inp3);
		assert mergeInterval(input).size() == 2;
		
		input.clear();
		input.add(inp1);
		input.add(inp2);
		assert mergeInterval(input).size() == 2;
		
		input.clear();
		input.add(inp1);
		input.add(inp2);
		inp3 = Arrays.asList(94100,94399);
		input.add(inp3);
		assert mergeInterval(input).size() == 1;

		input.clear();
		input.add(inp1);
		input.add(inp2);
		inp3 = Arrays.asList(94100,94199);
		input.add(inp3);
		assert mergeInterval(input).size() == 2;




	}
}
