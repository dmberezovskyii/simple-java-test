package myprojects.automation.assignment5.parse;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;

public class Parser {
    public static XmlPath rawToXML(Response response) {
        String resp = response.asString();
        XmlPath xmlPath = new XmlPath(resp);
        return xmlPath;

    }

    public static JsonPath rawToJSON(Response response) {
        String resp = response.asString();
        JsonPath json = new JsonPath(resp);
        return json;
    }
}
