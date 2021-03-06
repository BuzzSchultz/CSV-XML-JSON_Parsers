package ru.homeworks.abatsanov;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLtoJSON_Parser {
    private static final String TAG_STAFF = "staff";
    private static final String TAG_EMPLOYEE = "employee";
    private static final String TAG_ID = "id";
    private static final String TAG_FIRSTNAME = "firstName";
    private static final String TAG_LASTNAME = "lastName";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_AGE = "age";
    private static final List<Employee> staff = new ArrayList<>();

    public static void createXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element staff = document.createElement(TAG_STAFF);
            document.appendChild(staff);
            Element employee = document.createElement(TAG_EMPLOYEE);
            staff.appendChild(employee);
            Element id = document.createElement(TAG_ID);
            id.appendChild(document.createTextNode("1"));
            employee.appendChild(id);
            Element firstName = document.createElement(TAG_FIRSTNAME);
            firstName.appendChild(document.createTextNode("John"));
            employee.appendChild(firstName);
            Element lastName = document.createElement(TAG_LASTNAME);
            lastName.appendChild(document.createTextNode("Smith"));
            employee.appendChild(lastName);
            Element country = document.createElement(TAG_COUNTRY);
            country.appendChild(document.createTextNode("USA"));
            employee.appendChild(country);
            Element age = document.createElement(TAG_AGE);
            age.appendChild(document.createTextNode("25"));
            employee.appendChild(age);

            employee = document.createElement(TAG_EMPLOYEE);
            staff.appendChild(employee);
            id = document.createElement(TAG_ID);
            id.appendChild(document.createTextNode("2"));
            employee.appendChild(id);
            firstName = document.createElement(TAG_FIRSTNAME);
            firstName.appendChild(document.createTextNode("Inav"));
            employee.appendChild(firstName);
            lastName = document.createElement(TAG_LASTNAME);
            lastName.appendChild(document.createTextNode("Petrov"));
            employee.appendChild(lastName);
            country = document.createElement(TAG_COUNTRY);
            country.appendChild(document.createTextNode("RU"));
            employee.appendChild(country);
            age = document.createElement(TAG_AGE);
            age.appendChild(document.createTextNode("23"));
            employee.appendChild(age);

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("data.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseXML(String fileName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileName);
        Node root = document.getDocumentElement();
        return read(root);
    }

    //  ???????????????????? ?????????????????????? ???????????? ru.homeworks.abatsanov.Employee ???? xml-?????????? ?? ?????????? ???????????????? ??????????????????????
    private static List<Employee> read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node iterationNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == iterationNode.getNodeType()
                    && iterationNode.getNodeName().equals(TAG_EMPLOYEE)) {
                Element el = (Element) iterationNode;
                NodeList elements = el.getChildNodes();
                long id = 0;
                String firstName = "";
                String lastName = "";
                String country = "";
                int age = 0;
                for (int j = 0; j < elements.getLength(); j++) {
                    Node element = elements.item(j);
                    if (Node.ELEMENT_NODE == element.getNodeType()) {
                        switch (element.getNodeName()) {
                            case TAG_ID: {
                                id = Long.parseLong(element.getTextContent());
                                break;
                            }
                            case TAG_FIRSTNAME: {
                                firstName = element.getTextContent();
                                break;
                            }
                            case TAG_LASTNAME: {
                                lastName = element.getTextContent();
                                break;
                            }
                            case TAG_COUNTRY: {
                                country = element.getTextContent();
                                break;
                            }
                            case TAG_AGE: {
                                age = Integer.parseInt(element.getTextContent());
                                break;
                            }
                        }
                    }
                }
                staff.add(new Employee(id, firstName, lastName, country, age));
            }
            read(iterationNode);
        }
        return staff;
    }
}





