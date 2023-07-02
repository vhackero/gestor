package mx.gob.sedesol.basegestor.service;

public class ServiceException extends Exception{
	private Exception exception;
	private String errorCode;

	
    public ServiceException(String msg, Exception exception) {
        super(msg);
        this.exception = exception;
    }
	
	public Exception getException() {
		return this.exception;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return super.getMessage();
	}

}
