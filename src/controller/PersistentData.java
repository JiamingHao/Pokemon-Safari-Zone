package controller;

import java.io.Serializable;

public interface PersistentData<T> extends Serializable {

	public void restore(T data);
}
