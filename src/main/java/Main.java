import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }

    public static void writeString(String originalString, String destinationFileName) {
        try (FileWriter file = new FileWriter(destinationFileName)) {
            file.write(originalString);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        Конвртирование из .csv в .json;
        CSVtoJSON_Parser.createCSV();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = CSVtoJSON_Parser.parseCSV(columnMapping, fileName);
        String json1 = listToJson(list);
        writeString(json1, "data.json");
//        Конвртирование из .csv в .json закончено.

//        Конвртирование из .xml в .json
        XMLtoJSON_Parser.createXML();
        String fileName2 = "data.xml";
        List<Employee> list2 = XMLtoJSON_Parser.parseXML(fileName2);
        String json2 = listToJson(list2);
        writeString(json2, "data2.json");
//        Конвртирование из .xml в .json закончено.

//        Конвртирование из .json в List<Employee>
        JSON_Parser.createJSON();
        String fileName3 = "new_data.json";
        String json3 = JSON_Parser.readString(fileName3);
        List<Employee> list3 = JSON_Parser.jsonToList(json3);
        for (Employee employee : list3) {
            System.out.println(employee);
//        Конвртирование из .json в List<Employee> закончено
        }
    }
}
