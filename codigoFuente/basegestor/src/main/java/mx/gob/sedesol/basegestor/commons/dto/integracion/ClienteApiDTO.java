package mx.gob.sedesol.basegestor.commons.dto.integracion;

import java.io.Serializable;

public class ClienteApiDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String secretHash;
    private String scope;
    private String ipsPermitidas;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getSecretHash() { return secretHash; }
    public void setSecretHash(String secretHash) { this.secretHash = secretHash; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public String getIpsPermitidas() { return ipsPermitidas; }
    public void setIpsPermitidas(String ipsPermitidas) { this.ipsPermitidas = ipsPermitidas; }
}
