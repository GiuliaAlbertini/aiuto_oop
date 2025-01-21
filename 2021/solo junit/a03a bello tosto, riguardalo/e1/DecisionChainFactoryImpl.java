package a03a.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return new DecisionChain<A, B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);

            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return this.next(a);
            }

        };
    }

    @Override
public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
    return new DecisionChain<A, B>() {

        @Override
        public Optional<B> result(A a) {
           return Optional.empty();
        }

        @Override
        public DecisionChain<A, B> next(A a) {
            if (predicate.test(a)){ // se è > 0
                return new DecisionChain<A,B>() {

                    @Override
                    public Optional<B> result(A a) {
                        return Optional.of(positive);
                    }

                    @Override
                    public DecisionChain<A, B> next(A a) {
                        return this;
                    }  
                };
            }else{
                return new DecisionChain<A,B>() {

                    @Override
                    public Optional<B> result(A a) {
                        return Optional.of(negative);
                    }

                    @Override
                    public DecisionChain<A, B> next(A a) {
                        return this;
                    }
                    
                };
            }
        }
    };
}


@Override
public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
   if (mapList.isEmpty()){
    return new DecisionChain<A,B>() {

        @Override
        public Optional<B> result(A a) {
            return Optional.of(defaultReply);
        }

        @Override
        public DecisionChain<A, B> next(A a) {
           return this;
        }
        
    };
   }
   
   Pair<A,B> currentPair= mapList.get(0);
   List<Pair<A, B>> subList = mapList.subList(1, mapList.size());

   return new DecisionChain<A,B>() {

    @Override
    public Optional<B> result(A a) {
        if (currentPair.get1()== a){
            return Optional.of(currentPair.get2());
        }
        return Optional.empty();
    }

    @Override
    public DecisionChain<A, B> next(A a) {
        //delego alla lista
        return enumerationLike(subList, defaultReply);
    }
    
   };
   
}

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive, DecisionChain<A, B> negative) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                if (predicate.test(a)){
                    return positive.result(a);
                }else{
                    return negative.result(a);
                }
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                if (predicate.test(a)) {
                    // Se il predicato è vero, delega al decider positivo
                    // Verifica che il prossimo decider non chiami ancora next() inutilmente
                    Optional<B> result = positive.result(a);
                    if (result.isPresent()) {
                        return positive; // Restituisci il decider positivo senza chiamare next() ricorsivamente
                    }
                    return positive.next(a); // Altrimenti, passa al prossimo decider (se necessario)
                } else {
                    // Se il predicato è falso, delega al decider negativo
                    Optional<B> result = negative.result(a);
                    if (result.isPresent()) {
                        return negative; // Restituisci il decider negativo senza chiamare next() ricorsivamente
                    }
                    return negative.next(a); // Altrimenti, passa al prossimo decider (se necessario)
                }
            }

        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchChain'");
    }

}
