package springbook.cal;


public interface LineReadCallback<T> {
    T doSomethingWithLine(String line, T res);
}
