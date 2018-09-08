package Serializator;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serializer implements ISerialization {
    @Override
    public byte[] Serialize(Object obj) {
        return SerialObj(obj).getBytes(StandardCharsets.UTF_8);
    }

    private String SerialObj(Object obj) {
        if (obj == null)
            return "null";
        StringBuilder data = new StringBuilder();
        Class<?> mObj = obj.getClass();
        data.append("<")
                .append(mObj.getName())
                .append("|");
        for(Class<?> c = mObj; c != null; c = c.getSuperclass()) {
            for (Field f: c.getDeclaredFields()) {
                try {
                    data.append("[")
                            .append(f.getType().getTypeName())
                            .append(";")
                            .append(f.getName())
                            .append(";");
                    if(IsPrimitiveType(f.getType()))
                        data.append(f.get(obj));
                    else
                        data.append(SerialObj(f.get(obj)));
                    data.append("]");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        data.append(">");
        return data.toString();
    }

    private boolean IsPrimitiveType(Class<?> cType){
        return byte.class.equals(cType) ||
                int.class.equals(cType) ||
                char.class.equals(cType) ||
                short.class.equals(cType) ||
                long.class.equals(cType) ||
                float.class.equals(cType) ||
                double.class.equals(cType) ||
                boolean.class.equals(cType) ||
                String.class.equals(cType);
    }

    @Override
    public <T> T Deserialize(byte[] rawData) {
        String mData = new String(rawData, StandardCharsets.UTF_8);
        Packet p = ConfigurePacket(ObjectParser(mData));

        return (T)rawData;
    }

    private Packet ConfigurePacket(String obj){
        Pattern splitterPattern = Pattern.compile("(.+?)\\|(.+)");
        Matcher matches = splitterPattern.matcher(obj);
        matches.find();
        Packet p = new Packet(matches.group(1));
        p.AddVectorFieldInfo(FieldsParser(matches.group(2)));
        return p;
    }

    private Vector<FieldInfo> FieldsParser(String str){
        Vector<String> nestedObject = new Vector<String>();
        Vector<FieldInfo> result = new Vector<FieldInfo>();
        int startIndex = -1;
        int count = 0;
        for(int i=0; i<str.length();i++){
            if (str.charAt(i) == '<'){
                if (startIndex == -1) startIndex = i;
                count++;
            }
            if (str.charAt(i) == '>'){
                if (--count == 0) {
                    nestedObject.add(str.substring(startIndex, i+1));
                    startIndex = -1;
                }
            }
        }
        String preparedStr = str;
        for (String s : nestedObject){
            int len = preparedStr.indexOf(s);
            preparedStr = preparedStr.substring(0, len) + preparedStr.substring(len + s.length());
        }
        preparedStr = preparedStr.substring(1, preparedStr.length()-1);
        String[] fieldsStr = preparedStr.split("\\]\\[");
        for (String f : fieldsStr){
            String[] fields = f.split(";");
            if(fields.length == 2) {
                result.add(new FieldInfo(fields[0], fields[1], nestedObject.firstElement()));
                nestedObject.remove(0);
            }
            else
                result.add(new FieldInfo(fields[0], fields[1],fields[2]));
        }
        return result;
    }

    private Vector<String> ObjectParser(String raw){
        Pattern objPattern = Pattern.compile("<(.+?)>((?=<)|(?!]))");
        Matcher objMatches = objPattern.matcher(raw);
        Vector<String> objects = new Vector<String>();
        while (objMatches.find()){
            objects.add(objMatches.group(1));
        }
        return objects;
    }
}
