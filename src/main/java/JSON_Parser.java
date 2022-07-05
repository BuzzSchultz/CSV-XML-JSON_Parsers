import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON_Parser {

    public static void createJSON() {
        JSONObject obj1 = new JSONObject();
        obj1.put("id", 1);
        obj1.put("firstName", "John");
        obj1.put("lastName", "Smith");
        obj1.put("country", "USA");
        obj1.put("age", 25);

        JSONObject obj2 = new JSONObject();
        obj2.put("id", 2);
        obj2.put("firstName", "Inav");
        obj2.put("lastName", "Petrov");
        obj2.put("country", "RU");
        obj2.put("age", 23);

        JSONArray list = new JSONArray();
        list.add(0, obj1);
        list.add(1, obj2);

        try (FileWriter file = new FileWriter("new_data.json")) {
            file.write(list.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readString(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return String.valueOf(sb);
    }

    public static List<Employee> jsonToList(String json) {
        List<Employee> staff = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JSONParser parser = new JSONParser();
        try {
            JSONArray employeeArray = (JSONArray) parser.parse(json);
            for (Object obj : employeeArray) {
                Employee employee = gson.fromJson(obj.toString(), Employee.class);
                staff.add(employee);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}
