package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util;

public enum ResourceBundles {
    MESSAGES("messages"),
    ERRORS("errors"),
    CONFIG("configuration");
    private final String bundleName;  

    ResourceBundles(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleName() {
        return bundleName;
    }

    @Override
    public String toString() {
        return bundleName;
    }
}
