package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	
	public byte[] zip(List<DocumentoPDF>  lista)
	{
		
		ByteArrayOutputStream baos = null;
		ZipOutputStream zos = null;
		
		try{
				
			baos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(baos);
			
			for (DocumentoPDF documentoPDF:  lista){
				zos.putNextEntry (new ZipEntry(documentoPDF.getNombre()));
				zos.write        (documentoPDF.getStream() );
			}
			
		
		  } catch(IOException ioe) {
			  ioe.printStackTrace();
		  }
			
		return baos.toByteArray();
	}

}
