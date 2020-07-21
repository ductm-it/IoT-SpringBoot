package com.iot.platform.Interface.Sequence;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public interface SequenceInterface<T> {

    Serializable generate(T object, Serializable currentCounter);

    Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException;

    void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException;

}