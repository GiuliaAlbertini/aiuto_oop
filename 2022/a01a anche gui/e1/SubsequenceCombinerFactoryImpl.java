import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    /*
     * X: Tipo degli elementi della lista di input (in questo caso Integer).
     * Y: Tipo degli elementi della lista di output (in questo caso anche Integer).
     * 
     */
    @Override
    // consegna di factory
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        // devo ritornare una nuova sequenza
        return new SubsequenceCombiner<Integer, Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {

                // creo una nuova lista
                List<Integer> result = new ArrayList<>();
                int counter = 0;
                int sum = 0;

                for (Integer integer : list) {
                    if (!list.isEmpty()) {
                        counter++;
                        sum += integer;
                        if (counter == 3) {
                            result.add(sum);
                            counter = 0;
                            sum = 0;
                        }
                    }
                }
                if(counter != 0){
                    result.add(sum);
                }

                return result;
            }

        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X, List<X>>(){

            @Override
            //metodo base
            public List<List<X>> combine(List<X> list) {
                //lista di liste
                List<List<X>> result = new ArrayList<>();
                int counter = 0;
                List<X> subList = new ArrayList<>();

                for (X elem : list) {
                    if (!list.isEmpty()){
                        counter++;
                        //aggiungo elem alla sottolista
                        subList.add(elem);
                        if(counter==3){
                            //aggiungo la sottolosta creata a result (lista di liste)
                            result.add(subList);
                            subList=new ArrayList<>();
                            counter=0;
                        }
                    }
                }
                if (counter != 0 ){
                    result.add(subList);
                }
                
                return result;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<Integer,Integer>() {
            //devo fare i metodi della classe base
            @Override
            public List<Integer> combine(List<Integer> list) {
                //creo una nuova lista
                List<Integer> result =new ArrayList<>();
                //per ogni elemento della lista
                int counter=0;
                for (Integer elem : list) {
                    if(elem !=0){
                        counter++;
                    }else{
                        result.add(counter);
                        counter=0;
                    }
                }
                if(counter !=0){
                    result.add(counter);
                }
                return result;
            }
            
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X,Y>() {
            //metodi classe base
            @Override
            public List<Y> combine(List<X> list) {
                List<Y> result= new ArrayList<>();
                for (X elem : list) {
                    result.add(function.apply(elem));
                }
                return result;
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumulateToList'");
    }

}
