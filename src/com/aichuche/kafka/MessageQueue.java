package com.aichuche.kafka;
//package com.bianfeng.bfas.bfrd.kafka;
//
//import java.io.Serializable;
//
//import javax.transaction.NotSupportedException;
//
//public interface MessageQueue<T extends Serializable> {
//	public void put(T entity, Object key) throws NotSupportedException;
//
//	public void put(Iterable<T> entities, Object key) throws NotSupportedException;
//
//	public Message<T> take() throws NotSupportedException;
//
//	public boolean enableProducer();
//
//	public boolean enableConsumer();
//
//	public void putProp(String key, String val);
//
//	public void close();
//}
