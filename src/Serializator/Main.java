package Serializator;

import Serializator.TestClasses.ClassC;

public class Main {
    public static void main(String[] args) {
	    Serializer serializer = new Serializer();
	    ClassC pObj = new ClassC();
        ClassC res = serializer.Deserialize(serializer.Serialize(pObj));
        System.out.println(res.toString());
    }
}
