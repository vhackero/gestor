package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class ReporteUtil {

    private static final Logger logger = Logger.getLogger(ReporteUtil.class);

    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String PDF_EXTENSION = ".pdf";
    public static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    public static final String EXCEL_EXTENSION = ".xls";
    public static final Integer REPORTE_PDF = 1;
    public static final Integer REPORTE_EXCEL = 2;

    /**
     */
    public static byte[] generar(ReporteConfig configuracionReporte) {
        JasperReport jasperReport;
        JRDataSource jrDataSource;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (CollectionUtils.isEmpty(configuracionReporte.getDatos())) {
            jrDataSource = new JREmptyDataSource();
        } else {
            jrDataSource = new JRBeanCollectionDataSource(
                    configuracionReporte.getDatos());
        }
        try {

            //logger.info(FacesContext.getCurrentInstance().getExternalContext().getRealPath(configuracionReporte.getPathJasper()));
            //logger.info(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/jasperReport/PlantillaGeneralSISI.jasper"));
            jasperReport = (JasperReport) JRLoader
                    .loadObject(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(configuracionReporte.getPathJasper()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, configuracionReporte.getParametros(), jrDataSource);
            if (REPORTE_EXCEL.equals(configuracionReporte
                    .getTipoReporte())) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                        baos));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(false);
                configuration.setRemoveEmptySpaceBetweenRows(true);
                configuration.setWhitePageBackground(true);
                configuration.setShowGridLines(false);
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } else {
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
                exporter.setConfiguration(new SimplePdfExporterConfiguration());
                exporter.exportReport();
            }
        } catch (JRException e) {
            logger.error(e.getMessage(), e);

        }
        return baos.toByteArray();
    }

    /**
     *
     * @param data
     * @param contentType
     * @param name
     * @return
     */
    public static DefaultStreamedContent getStreamedContentOfBytes(byte[] data, String contentType, String name) {
        DefaultStreamedContent streamc = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            is.mark(0);
           
            streamc = new DefaultStreamedContent(is, contentType, name.concat(".pdf"));
          
            is.reset();
            is.close();
        } catch (IOException e) {
            logger.error((Object) e.getMessage(), (Throwable) e);
        }
        return streamc;
    }

    /**
     */
    public static byte[] generarPorXML(ReporteConfig configuracionReporte, String XML) {
        JasperReport jasperReport;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            Document document = convertStringToDocument(XML);

            jasperReport = (JasperReport) JRLoader
                    .loadObject(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(configuracionReporte.getPathJasper()));

            Map<String, Object> params = configuracionReporte.getParametros();

            params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, configuracionReporte.getParametros());

            if (REPORTE_EXCEL.equals(configuracionReporte
                    .getTipoReporte())) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                        baos));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(false);
                configuration.setRemoveEmptySpaceBetweenRows(true);
                configuration.setWhitePageBackground(true);
                configuration.setShowGridLines(false);
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } else {
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
                exporter.setConfiguration(new SimplePdfExporterConfiguration());
                exporter.exportReport();
            }
        } catch (JRException e) {
            logger.error(e.getMessage(), e);

        }
        return baos.toByteArray();
    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
