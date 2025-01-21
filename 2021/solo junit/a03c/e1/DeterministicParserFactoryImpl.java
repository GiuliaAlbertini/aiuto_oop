package a03c.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeterministicParserFactoryImpl implements DeterministicParserFactory {

    @Override
    public DeterministicParser oneSymbol(String s) {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                List<String> list = new ArrayList<>();
                int count = 0;
                list.addAll(tokens);
                for (String elem : tokens) {
                    if (elem.equals(s)) {
                        list.remove(elem);
                        count = 1;
                    }
                }
                if (count == 1) {
                    return Optional.of(list);
                }
                return Optional.empty();
            }

        };
    }

    @Override
    public DeterministicParser twoSymbols(String s1, String s2) {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                List<String> list = new ArrayList<>(tokens);
                int cs1 = 0;
                int cs2 = 0;
                for (String elem : tokens) {
                    if (elem == s1) {
                        list.remove(elem);
                        cs1++;
                    }
                    if (elem == s2) {
                        list.remove(elem);
                        cs2++;
                    }
                }
                if (cs1 == 1 && cs2 == 1) {
                    return Optional.of(list);
                }
                return Optional.empty();
            }

        };
    }

    @Override
    public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                int count = 0;
                List<String> sublist = new ArrayList<>();
                for (int i = 0; i < tokens.size() - 1; i++) {
                    int current = Integer.parseInt(tokens.get(i));
                    int next = Integer.parseInt(tokens.get(i + 1));
                    // se il primo è negativo interrompo e restituisco optional.empty
                    if (current <= 0) {
                        break;
                    }

                    if (next < current) {
                        count = 1;
                        sublist = tokens.subList(i + 1, tokens.size());
                        break;
                    }
                }
                if (count == 1) {
                    count = 0;
                    return Optional.of(sublist);
                }
                return Optional.of(tokens);
            }

        };
    }

    @Override
    public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
            DeterministicParser element) {
        return new DeterministicParser() {
            int count=0;
            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                List<String> sublist= tokens.subList(tokens.size()-1, tokens.size());
                for(int i=0; i< tokens.size()-1; i++){
                    if(tokens.get(0) != start){
                        count=1;
                    }
                    if(tokens.get(tokens.size()-2) != stop){
                        count=1;
                    }

                    if(i%2 == 0 && i!= 0 && i != tokens.size()-2){//se è un numero pari quello che è in posizione
                        if (tokens.get(i) != delimiter){ //se è diverso da ;
                            count=1;
                        }
                    } else if (i%2 != 0 && i!= 0 && i != tokens.size()){
                        Optional<List<String>> result = element.accepts(List.of(tokens.get(i)));
                        if (result.isEmpty()) {
                            count = 1;
                        }
                    }
                    
                }
                if (count==0){
                    return Optional.of(sublist);
                } return Optional.empty();
            }
            
        };
    }





    @Override
    public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
        return new DeterministicParser() {
            int count=0;
            @Override
            public Optional<List<String>> accepts(List<String> tokens){
                List<String> sublist= new ArrayList<>(tokens);
                
                for(int i=0; i<tokens.size()-1; i++){
                        Optional<List<String>> result= first.accepts(List.of(tokens.get(i)));
                        if(result.isPresent()){
                            sublist.remove(tokens.get(i));
                            count++;
                        }    

                        Optional<List<String>> result2= second.accepts(List.of(tokens.get(i),tokens.get(i+1)));
                        if(result2.isPresent()){
                            sublist.remove(tokens.get(i));
                            sublist.remove(tokens.get(i+1));
                            count++;
                        }
                       
                }
                if (count == 2) {
                    return Optional.of(sublist);
                }
                return Optional.empty();
            }
            
        };
    
    }

}
