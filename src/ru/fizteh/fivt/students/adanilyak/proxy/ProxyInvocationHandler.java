package ru.fizteh.fivt.students.adanilyak.proxy;

import ru.fizteh.fivt.students.adanilyak.logformater.XMLformatter;

import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * User: Alexander
 * Date: 23.11.13
 * Time: 11:13
 */
public class ProxyInvocationHandler implements InvocationHandler {
    private final Writer writer;
    private final Object implementation;

    public ProxyInvocationHandler(Writer givenWriter, Object object) {
        writer = givenWriter;
        implementation = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        XMLformatter formatter = new XMLformatter();
        formatter.writeTimeStamp();
        formatter.writeClass(implementation.getClass());
        formatter.writeMethod(method);
        formatter.writeArguments(args);
        try {
            result = method.invoke(implementation, args);
            formatter.writeReturnValue(result);
        } catch (InvocationTargetException exc) {
            Throwable targetException = exc.getTargetException();
            formatter.writeThrown(targetException);
            throw targetException;
        } catch (Exception exc) {
            // Something went wrong
        } finally {
            try {
                if (method.getDeclaringClass() != Object.class) {
                    writer.write(formatter.toString() + "\n");
                }
            } catch (Exception ignored) {}
        }
        return result;
    }
}