package Serializator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

class ClassA<T> {
    T t;
    String stringA;
}

class ClassB<String> extends ClassA {
    int a = 1;
    ClassA cl = new ClassA();
}

class ClassC extends ClassB {

}

public class Main {
    public static void main(String[] args) {
	    Serializer serializer = new Serializer();
	    ClassC pObj = new ClassC();
	    ClassB pObj2 = new ClassB();
	    ClassA pObj3 = new ClassA();
        ByteArrayOutputStream test = new ByteArrayOutputStream();
        try {
            test.write(serializer.Serialize(pObj));
            test.write(serializer.Serialize(pObj));
            test.write(serializer.Serialize(pObj2));
            test.write(serializer.Serialize(pObj3));
            test.write(serializer.Serialize(pObj));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(serializer.Deserialize(test.toByteArray()).toString());
    }
}
