package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CursorHelpersImpl implements CursorHelpers{

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return new Cursor<X>() {
            int i=0;
            @Override
            public X getElement() {
                return list.get(i);
            }

            @Override
            public boolean advance() {
                if(i+1< list.size()){
                    i++;
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {
            int i=0;
            @Override
            public Integer getElement() {
                return i;
            }

            @Override
            public boolean advance() {
                if (i<105){
                    i++;
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
       return new Cursor<X>() {
         int i=0;
        @Override
        public X getElement() {
            return input.getElement();
        }

        @Override
        public boolean advance() {
           if (i<max-1 && input.advance()){
              i++;
             return true;
           } return false;
        }
       };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        consumer.accept(input.getElement());
       while (input.advance()) {
            consumer.accept(input.getElement());
       }
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        List<X> list = new ArrayList<>();
        int i=0;
         list.add(input.getElement());
        while (input.advance() && i+1<max) {
            list.add(input.getElement());
            i++;
        }
        return list;
    }

}
