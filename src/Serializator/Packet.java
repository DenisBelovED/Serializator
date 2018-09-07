package Serializator;

import java.util.Vector;

public class Packet {
    private String ObjectName;
    private Vector<FieldInfo> ObjectFields;

    Packet(String objectName){
        ObjectName = objectName;
        ObjectFields = new Vector<FieldInfo>();
    }

    Vector<FieldInfo> getObjectFields() {
        return ObjectFields;
    }

    public void AddFieldInfo(FieldInfo fieldInfo){
        ObjectFields.add(fieldInfo);
    }

    public String getObjectName() {
        return ObjectName;
    }
}
