package com.scushare.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

public class ObjectRedisSerializer implements RedisSerializer<Object>{
	private final ObjectMapper objectMapper;
	final byte[] EMPTY_ARRAY = new byte[0];
	
	//构造1
	public ObjectRedisSerializer() {
		this((String) null);
	}
	//构造2
	public ObjectRedisSerializer(String classTypeName) {
		this(new ObjectMapper());
		
		if(StringUtils.hasText(classTypeName)) {
			objectMapper.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, classTypeName);
		}else {
			objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
		}
	}
	//构造3
	public ObjectRedisSerializer(ObjectMapper mapper) {
		this.objectMapper = mapper;
	}
	
	@Override
	public byte[] serialize(Object source) throws SerializationException {
		if(source == null) {
			return EMPTY_ARRAY;
		}
		
		try {
			return objectMapper.writeValueAsBytes(source);
		} catch (Exception e) {
			throw new SerializationException("Could not serialize: " + e.getMessage(), e);
		}
	}
	
	@Override
	public Object deserialize(byte[] source) throws SerializationException {
		return Deserializer(source, ObjectMapper.class);
	}
	
	public <T> T Deserializer(byte[] source, Class<T> type) throws SerializationException{
		if(type == null) {
			throw new SerializationException("Deserialization type must not be null! Please provide Object.class to make use.");
		}
		
		if(source == null || source.length == 0) {
			return null;
		}
		
		try {
			return objectMapper.readValue(source, type);
		} catch (Exception e) {
			throw new SerializationException("Could not deserialize: " + e.getMessage(), e);
		}
	}
	
}
