package ua.com.juja.serzh.sqlcmd.model.service;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by Serzh on 9/22/16.
 */
public class MockitoFactoryBean<T> implements FactoryBean {

    private Class<T> classToBeMocked;

    /**
     * Creates a Mockito mock instance of the provided class.
     * @param classToBeMocked The class to be mocked.
     */
    public MockitoFactoryBean(Class<T> classToBeMocked) {
        this.classToBeMocked = classToBeMocked;
    }

    @Override
    public T getObject() throws Exception {
        return Mockito.mock(classToBeMocked);
    }

    @Override
    public Class<?> getObjectType() {
        return classToBeMocked;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
