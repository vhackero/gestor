package mx.gob.sedesol.basegestor.commons.utils;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DatoAdjuntoDTO;

public class CorreoUtil {
	
	private static Logger log = Logger.getLogger(CorreoUtil.class);
	
	
	private static String getEncezado(String mensaje) {
		
        String contenido = "";

        contenido += "<html>";
        contenido += "<head>";
        contenido += "<title> eLearning Sedesol </title>";
        contenido += "</head>";
        contenido += "<body  style=\"margin:0.0pt;\" bgcolor=\"#DDDDDD\">";
        
        contenido +="<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"90%\" border=\"0\" bgcolor=\"White\">\n" + 
                    "  <tbody>\n" + 
                    "    <tr>\n" + 
                    "      <td>\n" + 
                    "      <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"708px\" border=\"0\">\n" + 
                    "           <tr>\n" + 
                    "          <td align=\"center\" style=\"background-color:#c1c1c1;\"><!-- Header -->\n" + 
//                    "            <img border=\"0\" src=\"cid:image1\" />\n" + 
                    
//                    String.format("<img src=\"%s\" width=\"86\" height=\"35\" border=\"0\"/> \n", rutaAplicacion + "/resources/images/logo.gif")+
                    
                    "          </td>\n" + 
                    "        </tr>\n" + 
                    "      </table>\n" + 
                    "      </td>\n" + 
                    "    </tr>\n" + 
                    "    <tr>\n" + 
                    "     <td>\n" + 
                    "      <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"708px\" border=\"0\">\n" + 
                    "           <tr>\n" + 
                    "             <td align=\"center\" style=\"background-color:#c1c1c1;\">&nbsp;</td>\n" + 
                    "           </tr>\n" + 
                    "        <tr>\n" + 
                    "         <td align=\"center\" style=\"background-color:#c1c1c1;\" >\n" + 
                    			mensaje +"\n" + 
                    "             </td>\n" + 
                    "        </tr>\n" + 
                    "        <tr>\n" + 
                    "             <td align=\"center\" style=\"background-color:#c1c1c1;\">&nbsp;</td>\n" + 
                    "           </tr>\n" + 
                    "      </table>\n" + 
                    "      </td>\n" + 
                    "    </tr>";
        
        contenido += "<tr>";
        contenido += "<td align=\"center\" height=\"100%\"><p>";
        contenido += "</td></tr>";
    
        return contenido;
    }
	
	private static String getFooter() {
        String contenido = "";
        
        contenido += "<tr>";
        contenido += "<td align=\"center\" height=\"100%\"> <p>";
        contenido += "</p></td>";
        contenido += "</tr>";
        contenido += "<tr>";
        contenido += "<td></td>";
        contenido += "</tr>";
        contenido += "<tr>";
        contenido += "<td style=\"height: 20px; font-family: Arial, Helvetica, sans-serif; font-size: 9px\" height=\"20px\">";
        contenido += "<p align=\"center\" style=\"height: 20px; font-family: Arial, Helvetica, sans-serif; font-size: 9px\"> Derechos Reservados S.A de C.V. <br>";
        contenido += "</p>";
        contenido +=" <br/>";
        contenido += "</td>";
        contenido += "</tr>";
        contenido += "</tbody>";
        contenido += "</table>";
        contenido += "</body>";
        contenido += "</html>";
        return contenido;
    }
	
	
    public static void sendMail(final CorreoDTO dto) throws EmailException {
    
    	try{
    		
        	if(ObjectUtils.isNotNull(dto)){
        		
        		Properties emailProperties = new Properties();
        		
        		emailProperties.put("mail.smtp.host", dto.getHost());
        		emailProperties.put("mail.smtp.port", dto.getPort());
        		emailProperties.put("mail.smtp.auth", "true");
        		emailProperties.put("mail.smtp.starttls.enable", "true");

                Authenticator auth = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(dto.getUsuarioCorreo(), dto.getPasswordCorreo());
                    }
                };
         
                Session session = Session.getInstance(emailProperties, auth);
               
               
                
                HtmlEmail email = new HtmlEmail();
                email.setMailSession(session);
                email.setFrom(dto.getRemitente(),"eLearning");
        		
        		if(!ObjectUtils.isNullOrEmpty(dto.getDestinatarios())){
        			for (String to : dto.getDestinatarios()) {
        				email.addTo(to);
        			}
        		}
        		
        		if(!ObjectUtils.isNullOrEmpty(dto.getDestinatariosCC())){
        			for (String toCC : dto.getDestinatariosCC()) {
        				email.addCc(toCC);
        			}
        		}
        		
        		email.setSubject(dto.getAsunto());
        		String header = getEncezado(dto.getTitulo());
        		String content = dto.getContenido();
        		String footer = getFooter();
        		
        		StringBuilder htmlEmail = new StringBuilder();
//        		htmlEmail.append(header);
//        		htmlEmail.append("<BR />");
        		htmlEmail.append(content);
//        		htmlEmail.append("<BR />");
//        		htmlEmail.append(footer);
        		
        		
        		email.setHtmlMsg(htmlEmail.toString());
        		
        		// set the alternative message
        		email.setTextMsg("Tu cliente de Email no soporta mensajes HTML");
        		
        		// Adjuntar archivos al correo
        		agregarAttachments(email, dto.getAdjuntos());
        		email.send();
        		log.info("Mensaje enviado... validar en bandeja.");
        	}
    		
    	}catch (Exception e) {
    		log.error(e.getMessage(),e);
    	}
    
    	
    }
    
    private static void agregarAttachments(HtmlEmail email,
            List<DatoAdjuntoDTO> attachments) throws EmailException {
        for (DatoAdjuntoDTO attachment : attachments) {
            email.attach(new ByteArrayDataSource(attachment.getData(),
                    attachment.getMimeType()), attachment.getName(), attachment
                    .getDescription());
        }
    }
	
}
