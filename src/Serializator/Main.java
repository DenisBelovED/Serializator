package Serializator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

class Class0 {

}

class ClassA<T> {
    T t;
    String stringA;
    Class0 ooo = new Class0();
}

class ClassB<String> extends ClassA {
    int a = 1;
    ClassA cl = new ClassA();
    ClassA c2 = new ClassA();
}

class ClassC extends ClassB {

}

public class Main {
    public static void main(String[] args) {
	    Serializer serializer = new Serializer();
	    ClassC pObj = new ClassC();
	    ClassB pObj2 = new ClassB();
	    ClassA pObj3 = new ClassA();
        //ByteArrayOutputStream test = new ByteArrayOutputStream();
        /*try {
            test.write(serializer.Serialize(pObj));
            test.write(serializer.Serialize(pObj));
            test.write(serializer.Serialize(pObj2));
            test.write(serializer.Serialize(pObj3));
            test.write(serializer.Serialize(pObj));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ClassC res = serializer.Deserialize(serializer.Serialize(pObj));
        System.out.println(res.toString());
    }
}
