package mx.gob.sedesol.basegestor.commons.dto.admin;

public class DatoAdjuntoDTO {
    /**
     * Contenido del attachment en forma de array de bytes
     */
    private byte[] data;

    /**
     * MIME Type o Content Type
     */
    private String mimeType;

    /**
     * Nombre del attachment
     */
    private String name;

    /**
     * Descripcion del attachment
     */
    private String description;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
