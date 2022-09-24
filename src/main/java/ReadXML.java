import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

class XmlElement {
    public boolean enabled = false;
    public String fileName;
    public String format;
}

public class ReadXML {
    private static XmlElement load = new XmlElement();
    private static XmlElement save = new XmlElement();
    private static XmlElement log = new XmlElement();

    public static XmlElement getLoad() {
        return load;
    }

    public static XmlElement getSave() {
        return save;
    }

    public static XmlElement getLog() {
        return log;
    }

    public static void readXML(File xmlFile) {
        if (!xmlFile.exists()) {
            System.out.println("Xml file not found");
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            Node config = doc.getDocumentElement();
            read(config);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == currentNode.getNodeType()) {
                if ("load".compareTo(currentNode.getNodeName()) == 0) {
                    readXmlElement(currentNode, load);
                } else if ("save".compareTo(currentNode.getNodeName()) == 0) {
                    readXmlElement(currentNode, save);
                } else if ("log".compareTo(currentNode.getNodeName()) == 0) {
                    readXmlElement(currentNode, log);
                }
            }
        }
    }

    private static void readXmlElement(Node node, XmlElement element) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == currentNode.getNodeType()) {
                String value = currentNode.getChildNodes().item(0).getTextContent();
                if ("enabled".compareTo(currentNode.getNodeName()) == 0) {
                    element.enabled = Boolean.parseBoolean(value);
                }
                if ("fileName".compareTo(currentNode.getNodeName()) == 0) {
                    element.fileName = value;
                }
                if ("format".compareTo(currentNode.getNodeName()) == 0) {
                    element.format = value;
                }
            }
        }
    }
}
