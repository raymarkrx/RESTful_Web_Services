package com.aichuche.kafka;

import java.io.Serializable;

public interface Message<T extends Serializable> {

	public abstract T get();

	public abstract String key();
	
	public abstract long offset();

	public abstract void acknowledge();

}