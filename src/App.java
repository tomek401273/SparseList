/* package whatever; // don't place package name! */

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;

import static javafx.scene.input.KeyCode.T;

class SparseList<T> implements List<T> {


    Map<Integer, T> sparseMap = new HashMap<>();

    private int count;
    private T defaultPattern;

    public SparseList(T defaultPattern) {
        this.count = 0;
        this.defaultPattern = defaultPattern;
    }

    @Override
    public int size() {

        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (defaultPattern.equals(o)) {
            return true;
        }

        for (T t : sparseMap.values()) {
            if (Objects.equals(o, t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        for (int i = 0; i < size(); i++) {
            array[i] = defaultPattern;
        }
        for (Integer integer : sparseMap.keySet()) {
            array[integer] = sparseMap.get(integer);
        }

        return array;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        Object[] array = new Object[size()];

        for (int i = 0; i < a.length; i++) {
            array[i] = a[i];
        }

        for (int i = 0; i < size(); i++) {
            a[i] = (E) defaultPattern;
        }

        for (Integer integer : sparseMap.keySet()) {
            a[integer] = (E) sparseMap.get(integer);
        }
        return a;
    }

    @Override
    public boolean add(T o) {
        set(size(), o);
        return true;
    }

    @Override
    public T remove(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            count--;
            T t = sparseMap.remove(index);
            if (t.equals(null)) {
                return defaultPattern;
            } else {
                return t;
            }
        }
    }

    @Override
    public void clear() {

        for (int i = 0; i < size(); i++) {
            sparseMap.remove(i);
        }

    }

    @Override
    public boolean remove(Object o) {
        if (defaultPattern.equals(o)) {
            return true;
        }

        for (int i = 0; i < size(); i++) {
            T t = sparseMap.get(i);
            if (Objects.equals(o, t)) {
                sparseMap.remove(i);
                count--;
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        ArrayList<T> c2 = new ArrayList<>();
        c2.addAll(c);
        for (int i = 0; i < c2.size(); i++) {
            T t = c2.get(i);
            add(t);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {

        ArrayList<T> c2 = new ArrayList<>();
        c2.addAll(c);
        for (int i = 0; i < c2.size(); i++) {
            T t = c2.get(i);
            add(index, t);
            index++;
        }

        return true;


    }

    @Override
    public T get(int index) {

        if (sparseMap.get(index) == null) {
            return defaultPattern;
        } else {
            return sparseMap.get(index);

        }

    }

    @Override
    public T set(int index, T element) {
        if (defaultPattern.equals(element) && index < size()) {
            sparseMap.remove(index);


        } else if (defaultPattern.equals(element) && index >= size()) {
            count = index + 1;
        } else if (index < size()) {
            sparseMap.put(index, element);
        } else if (index >= size()) {
            count = index + 1;
            sparseMap.put(index, element);
        }
        return element;
    }

    @Override
    public void add(int index, T element) {
        if (index >= size()) {
            set(index, element);
        } else {
            count++;
            if (!element.equals(defaultPattern)) {
                sparseMap.put(index, element);
            }
        }

    }

    @Override
    public int indexOf(Object o) {
        int j = 0;
        if (defaultPattern.equals(o)) {

            throw new IndexOutOfBoundsException();
            /*
            zastosowałem ten wyjątek bo nie znam jeszcze innych a nastempnych rozdziałach kodilli są wyjątki
            więc poczekam na odpowiedni moduł aby zastosować odpowiedni wyjątek
             */
        }
        System.out.println("Tomek");

        for (int i = 0; i <= size(); i++) {
            T t = sparseMap.get(i);
            if (Objects.equals(o, t)) {
                j = i;
                break;
            }
        }
        return j;
    }

    @Override
    public int lastIndexOf(Object o) {
        int j = 0;
        if (defaultPattern.equals(o)) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = size(); i >= 0; i--) {
            T t = sparseMap.get(i);
            if (Objects.equals(o, t)) {
                j = i;
                break;
            }
        }

        return j;
    }

    //czym sie różni iterator od listIterator
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {

            private int cursor;

            @Override
            public boolean hasNext() {

                return (cursor < size());
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    int current = cursor;
                    cursor++;
                    T t = sparseMap.get(current);

                    if (null == t) {
                        t = defaultPattern;
                    }
                    return t;
                }
                throw new NoSuchElementException();
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public T previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {

            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        ArrayList<T> sub = new ArrayList<>();

        for (int i = fromIndex; i < toIndex; i++) {
            sub.add(sparseMap.get(i));
        }
        return sub;
    }

    @Override
    public boolean retainAll(Collection c) {
        ArrayList<T> c2 = new ArrayList();
        c2.addAll(c);

        ArrayList<T> c3 = new ArrayList<>();

        for (int i : sparseMap.keySet()) {
            T t1 = sparseMap.get(i);
            boolean remove = true;

            for (int j = 0; j < c2.size(); j++) {
                T t2 = c2.get(j);
                if (Objects.equals(t1, t2)) {
                    remove = false;
                    break;
                }
            }
            if (remove) {
                c3.add(sparseMap.get(i));
            }
        }

        for (T t : c3) {
            sparseMap.remove(t);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection c) {
        ArrayList<T> c2 = new ArrayList();
        c2.addAll(c);
        for (int i = 0; i < c2.size(); i++) {
            T t1 = c2.get(i);

            for (int j = 0; j < size(); j++) {
                T t2 = sparseMap.get(j);
                if (t1 != null) {
                    if (Objects.equals(t1, t2)) {
                        System.out.println("t1: " + t1 + " : t2: " + t2);
                        sparseMap.remove(j);
                        count--;
                    }
                }

            }

        }
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        ArrayList<T> c2 = new ArrayList<>();
        c2.addAll(c);
        ArrayList<Boolean> equals = new ArrayList<>();
        boolean check = false;


        for (int i = 0; i < c2.size(); i++) {
            T t1 = c2.get(i);

            for (int j = 0; j < size(); j++) {
                for (T t2 : sparseMap.values()) {
                    if (defaultPattern.equals(t1)) {
                        check = true;
                        break;
                    }
                    check = false;

                    if (Objects.equals(t1, t2)) {

                        check = true;
                        break;
                    }


                }
            }
            equals.add(check);

        }
        System.out.println("equals size: " + equals.size());
        for (int i = 0; i < equals.size(); i++) {
            boolean b = equals.get(i);
            System.out.println("b: " + b);
        }
        if (equals.contains(false)) {
            return false;
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SparseList<?> that = (SparseList<?>) o;

        if (count != that.count) return false;
        if (sparseMap != null ? !sparseMap.equals(that.sparseMap) : that.sparseMap != null) return false;
        return defaultPattern != null ? defaultPattern.equals(that.defaultPattern) : that.defaultPattern == null;
    }

    @Override
    public int hashCode() {
        int result = sparseMap != null ? sparseMap.hashCode() : 0;
        result = 31 * result + count;
        result = 31 * result + (defaultPattern != null ? defaultPattern.hashCode() : 0);
        return result;
    }
}

class Ideone {
    public static void main(String[] args) throws java.lang.Exception {

//        System.out.println("SparseList String");
//        SparseList<String> name = new SparseList<>("r");
//
//        name.set(0, "Ala");
//        name.set(1, "ma");
//        name.set(2, "kota");
//        name.set(3, "r");
//        name.set(4, "ee");
//        name.set(5, "a");
//
//
//        System.out.println("Size name is: " + name.size());
//
//        System.out.println("name O: " + name.get(0));
//        for (int i = 0; i < name.size(); i++) {
//            System.out.println("i: " + i + ": name: " + name.get(i));
//        }
//
//        System.out.println();

        System.out.println("SparseList Integer");
        SparseList<Integer> n = new SparseList<>(1);

        n.set(0, 0);
        n.set(1, 1);
        n.set(2, 3);
        n.set(3, 2);
        n.set(4, 4);
        n.set(5, 54);
        n.set(6, 12);
        n.set(7, 72);
        n.set(8, 82);
        n.set(9, 92);
        n.set(10, 10);


        int maxIdx = 0;
        for (Integer integer : n.sparseMap.keySet()) {

            if (integer > maxIdx) {
                maxIdx = integer;
            }
        }
//        System.out.println("Size sparseList: " + maxIdx);
//        System.out.println();

        for (int j = 0; j <= maxIdx; j++) {
            System.out.println("j " + j + " : " + n.get(j));
        }
//
//        System.out.println("/////////////////////////////////////////");
//        System.out.println("IndexOf: " + n.indexOf(32));
//        System.out.println("IndexLastOf: " + n.lastIndexOf(32));
//        System.out.println("/////////////////////////////////////////");


//        System.out.println("Sparse LIST 2");
//        ArrayList<Integer> n2 = new ArrayList<Integer>();
////        n2 = (ArrayList<Integer>) n.subList(0, 3);
//        n2.add(1);
//        n2.add(2);
//        n2.add(3);
//        n2.add(4);
//
//
//        for (int i = 0; i < n2.size(); i++) {
//            System.out.println("n2: " + n2.get(i));
//        }
//        System.out.println("/////////////////////////////////////////");
//
//        System.out.println("Remove");
//        System.out.println("n size:" + n.size());
//        n.removeAll(n2);
//
//        System.out.println("After REMOVE");
//        System.out.println("n size:" + n.size());
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.sparseMap.get(i));
//        }
//        System.out.println("//////////////////////////////////////////");
//            System.out.println("Retain");
//            n.retainAll(n2);
//
//            for (Integer i : n.sparseMap.keySet()) {
//                System.out.println(i + " : " + n.get(i));
//            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        System.out.println("Contins all element with n2");
//
//        System.out.println(n.containsAll(n2));
//


//////////////////////////////////////////////////Iterator//////////////////////////////////////////////////////////////
//        System.out.println();
//        System.out.println("Pętla FOR");

//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.get(i));
//        }
//
//        System.out.println();
//        System.out.println("Iterator");
//        Iterator<Integer> it = n.listIterator();
//
//        int i = 0;
//
//        while (it.hasNext()) {
//            Object t = it.next();
//            System.out.println(i + " : " + t);
//            i++;
//        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        System.out.println(" Delte Object");
//
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.sparseMap.get(i));
//        }
//        System.out.println("After removing");
//        Integer x = 2 ;
//        n.remove(x);
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.get(i));
//        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        System.out.println("Add collection");
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.sparseMap.get(i));
//        }
//
//        ArrayList<Integer> n3 = new ArrayList<>();
//        n3.add(22);
//        n3.add(23);
//        n3.add(24);
//        n3.add(25);
//        n3.add(26);
//        n.addAll(n3);
//
//        System.out.println("After add collection");
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.sparseMap.get(i));
//        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        System.out.println("Add collection  index");
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.sparseMap.get(i));
//        }
//
//        ArrayList<Integer> n3 = new ArrayList<>();
//        n3.add(22);
//        n3.add(23);
//        n3.add(24);
//        n3.add(25);
//        n3.add(26);
//        n.addAll(22,n3);
//
//        System.out.println("After add collection");
//        for (int i = 0; i < n.size(); i++) {
//            System.out.println(i + " : " + n.get(i));
//        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("toArray[]");
        Integer[] a = new Integer[n.size()];
        Integer[] a2;
        a2 = n.toArray(a);

       // T[] b = new T[n.size()];


        for (int i = 0; i < a2.length; i++) {
            System.out.println(a2[i]);
        }

    }
}