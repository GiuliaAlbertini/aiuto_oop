package a04.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return  new ListExtractor<X,Optional<X>>() {

            @Override
            public Optional<X> extract(List<X> list) {
                if (list.isEmpty()){
                    return Optional.empty();
                }
                    return Optional.of(list.get(0));
            }
            
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
       return new ListExtractor<X,List<Y>>() {

        @Override
        public List<Y> extract(List<X> list) {
            List<Y> newlist= new ArrayList<>();
            Y newelem;
            for (X elem : list) {
                if (!stopCondition.test(elem)){
                    newelem= mapper.apply(elem);
                    newlist.add(newelem);
                }else{
                    break;
                }
            }
            return newlist;
        }
       };
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
       return new ListExtractor<X,List<List<X>>>() {

        @Override
        public List<List<X>> extract(List<X> list) {
            List<List<X>> newlist = new ArrayList<>();
            List<X> inner_list= new ArrayList<>();
            
            int count=0;
            for (int i=0; i<= list.size()-1; i++){
                if (startCondition.test(list.get(i)) || count==1){  
                    inner_list.add(list.get(i));
                    newlist.add(new ArrayList<>(inner_list));
                    count=1;
                }
            }
            return newlist;
        }
        
       };
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countConsecutive'");
    }

}
