import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class EquivalentItemCounter<T> implements Iterable<T> {
    private Map<T, Integer> counts = new HashMap<T, Integer>();

    public void addItem(T item){
        counts.merge(item, 1, Integer::sum);
    }

    public Integer getCount( T item ){
        Integer occurrences = counts.get(item);
        return Objects.requireNonNullElse(occurrences, 0);
    }

    public int size() {
        return counts.size();
    }

    @Override
    public Iterator<T> iterator() {
        return counts.keySet().iterator();
    }
}
