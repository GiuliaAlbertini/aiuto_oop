package a01a.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer, Integer>() {
            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> result = new LinkedList<>();
                int sum = 0;
                int count = 0;

                if (!list.isEmpty()) {
                    for (Integer n : list) {
                        sum += n;
                        count++;
                        if (count == 3) {
                            result.add(sum);
                            sum = 0;
                            count = 0;
                        }
                    }
                }
                if (count > 0) {
                    result.add(sum);
                }
                return result;
            }
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X, List<X>>() {

            @Override
            public List<List<X>> combine(List<X> list) {

                List<List<X>> result = new LinkedList<>();

                List<X> subList = new LinkedList<>();
                int count = 0;

                for (X x : list) {
                    subList.add(x);
                    count++;
                    if (count == 3) {
                        result.add(subList);
                        subList = new LinkedList<>();
                        count = 0;
                    }

                }
                if (count > 0) {
                    result.add(subList);
                }

                return result;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<Integer, Integer>() {
            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> result = new LinkedList<>();
                int count = 0;

                for (Integer n : list) {
                    if (n == 0) {
                        result.add(count);
                        count = 0;
                    } else {
                        count++;
                    }
                }
                if (count > 0) {
                    result.add(count);
                }
                return result;
            }
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X, Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                List<Y> result = new LinkedList<>();
                for (X x : list) {
                    result.add(function.apply(x));
                }
                return result;
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer, List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                List<List<Integer>> result = new LinkedList<>();

                List<Integer> subList = new LinkedList<>();
                int sum = 0;

                for (Integer n : list) {
                    sum += n;
                    subList.add(n);
                    if (sum > threshold || sum == threshold) {
                        result.add(subList);
                        subList = new LinkedList<>();
                        sum = 0;
                    }
                }

                if (!subList.isEmpty()) {
                    result.add(subList);
                }

                return result;
            }

        };
    }

}
