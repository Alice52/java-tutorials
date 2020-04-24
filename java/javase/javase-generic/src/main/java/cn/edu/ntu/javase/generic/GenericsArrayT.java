package cn.edu.ntu.javase.generic;

/**
 * @author zack <br>
 * @create 2020-04-22 15:21 <br>
 */
public class GenericsArrayT<T> {
  private Object[] array;

  public GenericsArrayT(int size) {
    array = new Object[size];
  }

  public void put(int index, T item) {
    array[index] = item;
  }

  public T get(int index) {
    return (T) array[index];
  }

  public T[] rap() {
    return (T[]) array;
  }
}
