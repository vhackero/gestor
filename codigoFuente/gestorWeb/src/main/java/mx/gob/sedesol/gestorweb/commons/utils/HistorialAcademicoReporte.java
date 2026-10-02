package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import mx.gob.sedesol.basegestor.commons.utils.ColumnasHistorial;
import net.sf.jasperreports.engine.*;

/**
 * Conserva el encabezado institucional y construye la tabla con la misma seleccion
 * de columnas de la vista. Compila el JRXML vigente, sin depender de un .jasper antiguo.
 */
public final class HistorialAcademicoReporte {
    private static final String JR = "http://jasperreports.sourceforge.net/jasperreports";
    private static final String COMPONENTES = "http://jasperreports.sourceforge.net/jasperreports/components";

    private HistorialAcademicoReporte() { }

    public static byte[] generar(InputStream plantilla, Map<String, Object> parametros,
            List<ColumnasHistorial> columnas, Map<String, String> cabeceras) throws Exception {
        JasperReport reporte = compilar(plantilla, columnas, cabeceras);
        JasperPrint impresion = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource(1));
        return JasperExportManager.exportReportToPdf(impresion);
    }

    public static JasperReport compilar(InputStream plantilla, List<ColumnasHistorial> columnas) throws Exception {
        return compilar(plantilla, columnas, java.util.Collections.<String, String>emptyMap());
    }

    public static JasperReport compilar(InputStream plantilla, List<ColumnasHistorial> columnas,
            Map<String, String> cabeceras) throws Exception {
        if (plantilla == null) throw new IllegalArgumentException("No se encontro la plantilla del historial.");
        if (columnas == null || columnas.isEmpty()) throw new IllegalArgumentException(ColumnasHistorial.ERROR);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document doc = factory.newDocumentBuilder().parse(plantilla);
        Element tabla = (Element) doc.getElementsByTagNameNS(COMPONENTES, "table").item(0);
        Element prototipo = (Element) tabla.getElementsByTagNameNS(COMPONENTES, "column").item(0);
        prototipo = (Element) prototipo.cloneNode(true);
        NodeList anteriores = tabla.getElementsByTagNameNS(COMPONENTES, "column");
        while (anteriores.getLength() > 0) tabla.removeChild(anteriores.item(0));

        Element dataset = (Element) doc.getElementsByTagNameNS(JR, "subDataset").item(0);
        NodeList campos = dataset.getElementsByTagNameNS(JR, "field");
        while (campos.getLength() > 0) dataset.removeChild(campos.item(0));
        // Modalidad se necesita para la leyenda No aplica, aunque esa columna este oculta.
        agregarCampo(doc, dataset, "modalidad", "java.lang.String");
        int peso = 0;
        for (ColumnasHistorial columna : columnas) peso += columna.getAncho();
        int ancho = peso > 650 ? 797 : 550;
        if (ancho > 550) {
            doc.getDocumentElement().setAttribute("pageWidth", "842");
            doc.getDocumentElement().setAttribute("pageHeight", "595");
            doc.getDocumentElement().setAttribute("columnWidth", "802");
            doc.getDocumentElement().setAttribute("orientation", "Landscape");
        }
        Element elementoTabla = (Element) ((Element) tabla.getParentNode())
                .getElementsByTagNameNS(JR, "reportElement").item(0);
        elementoTabla.setAttribute("width", Integer.toString(ancho));
        int ocupado = 0;
        for (int i = 0; i < columnas.size(); i++) {
            ColumnasHistorial columna = columnas.get(i);
            String campo = columna.getPropiedad();
            if (columna != ColumnasHistorial.MODALIDAD) {
                String tipo = columna == ColumnasHistorial.FECHA ? "java.util.Date"
                        : columna == ColumnasHistorial.PROMEDIO ? "java.lang.Double"
                        : columna == ColumnasHistorial.ASISTENCIA ? "java.lang.Integer" : "java.lang.String";
                agregarCampo(doc, dataset, campo, tipo);
            }
            int medida = i == columnas.size() - 1 ? ancho - ocupado : ancho * columna.getAncho() / peso;
            ocupado += medida;
            Element copia = (Element) prototipo.cloneNode(true);
            copia.removeAttribute("uuid");
            copia.setAttribute("width", Integer.toString(medida));
            NodeList elementos = copia.getElementsByTagNameNS(JR, "reportElement");
            for (int j = 0; j < elementos.getLength(); j++) {
                Element elemento = (Element) elementos.item(j);
                elemento.removeAttribute("uuid");
                elemento.setAttribute("width", Integer.toString(medida));
            }
            Element cabecera = (Element) copia.getElementsByTagNameNS(COMPONENTES, "columnHeader").item(0);
            cabecera.setAttribute("height", "50");
            ((Element) cabecera.getElementsByTagNameNS(JR, "reportElement").item(0)).setAttribute("height", "50");
            Element detalle = (Element) copia.getElementsByTagNameNS(COMPONENTES, "detailCell").item(0);
            Element textoDetalle = (Element) detalle.getElementsByTagNameNS(JR, "textField").item(0);
            textoDetalle.setAttribute("isBlankWhenNull", "true");
            textoDetalle.setAttribute("isStretchWithOverflow", "true");
            NodeList fuentes = copia.getElementsByTagNameNS(JR, "font");
            for (int j = 0; j < fuentes.getLength(); j++) ((Element) fuentes.item(j)).setAttribute("size", "8");
            Element texto = (Element) detalle.getElementsByTagNameNS(JR, "textElement").item(0);
            Element fuente = doc.createElementNS(JR, "font");
            fuente.setAttribute("size", "8");
            texto.appendChild(fuente);
            // El texto editable es contenido XML, nunca una expresion Java.
            Element anterior = (Element) cabecera.getElementsByTagNameNS(JR, "textField").item(0);
            Element estatico = doc.createElementNS(JR, "staticText");
            estatico.appendChild(anterior.getElementsByTagNameNS(JR, "reportElement").item(0).cloneNode(true));
            estatico.appendChild(anterior.getElementsByTagNameNS(JR, "textElement").item(0).cloneNode(true));
            Element etiqueta = doc.createElementNS(JR, "text");
            etiqueta.setTextContent(cabeceras.containsKey(columna.getClave())
                    ? cabeceras.get(columna.getClave()) : columna.getEtiqueta());
            estatico.appendChild(etiqueta);
            cabecera.replaceChild(estatico, anterior);
            String expresion = "$F{" + campo + "}";
            if (columna == ColumnasHistorial.FECHA) {
                expresion = "$F{fecha} == null ? \"\" : new java.text.SimpleDateFormat(\"dd/MM/yyyy\").format($F{fecha})";
            } else if (columna == ColumnasHistorial.PROMEDIO || columna == ColumnasHistorial.ASISTENCIA) {
                expresion = "\"En línea\".equals($F{modalidad}) ? \"No aplica\" : (" + expresion
                        + " == null ? \"\" : " + expresion + ".toString())";
            }
            detalle.getElementsByTagNameNS(JR, "textFieldExpression").item(0).setTextContent(expresion);
            tabla.appendChild(copia);
        }
        ByteArrayOutputStream xml = new ByteArrayOutputStream();
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(xml));
        return JasperCompileManager.compileReport(new ByteArrayInputStream(xml.toByteArray()));
    }

    private static void agregarCampo(Document doc, Element dataset, String nombre, String tipo) {
        Element campo = doc.createElementNS(JR, "field");
        campo.setAttribute("name", nombre);
        campo.setAttribute("class", tipo);
        dataset.appendChild(campo);
    }
}
