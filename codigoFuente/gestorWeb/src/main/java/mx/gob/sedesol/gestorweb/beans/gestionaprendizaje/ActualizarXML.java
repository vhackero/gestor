/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.File;
import java.util.Arrays;
import javax.validation.constraints.NotNull;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author annelkaren
 */
public class ActualizarXML {

    private static final Logger LOG = Logger.getLogger(ActualizarXML.class);
    
    /**
     * Método que agrega un node xml en el arreglo de tags que se pasa como
     * último parámetro
     * @param doc Documento xml
     * @param idioma Idioma para {@code xml:lang}
     * @param valor Valor que se escribe en el último tag del arreglos tags
     * @param tags Arreglo de tags donde se escribe el valor
     * @throws TransformerException 
     */
    public void agregarElemento(@NotNull Document doc, String idioma, 
            @NotNull String valor, @NotNull String... tags) throws TransformerException {
        LOG.info("Tags: " + Arrays.toString(tags));
        NodeList nodeList = doc.getElementsByTagName(tags[0]);
        for(int i=1; i<(tags.length -1); i++) {
            nodeList = getNodeList((Element)nodeList.item(0), tags[i]);
        }
        cambiaValorTag(nodeList, tags[tags.length-1], valor, idioma);
        guardaReporte(doc);
    }
    
    public void actualizarElemento(@NotNull Document doc, String idioma, @NotNull String... tags) throws TransformerException {
        LOG.info("Tags: " + Arrays.toString(tags));
        NodeList nodeList = doc.getElementsByTagName(tags[0]);
        cambiaValorTag(nodeList, tags[1], tags[2], idioma);
        guardaReporte(doc);
    }

    private void cambiaValorTag(NodeList nodeList, String tag, String valor, String idioma) throws DOMException {
        Element emp;
        for (int i = 0; i < nodeList.getLength(); i++) {
            emp = (Element) nodeList.item(i);
            if (emp.getElementsByTagName(tag).getLength() > 0) {
                Node name = emp.getElementsByTagName(tag).item(0);
                name.getFirstChild().setNodeValue(valor);
                if(name.getAttributes().getNamedItem("xml:lang") != null) {
                    name.getAttributes().getNamedItem("xml:lang").setNodeValue(idioma);
                }
            }
        }
    }
    
    private NodeList getNodeList(Element element, String tag) {
        return element.getElementsByTagName(tag);
    }

    public void guardaReporte(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(System.getProperty("user.home") + ConstantesGestorWeb.NOMBRE_XML));
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }
}
