/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util;

/**
 *
 * @author macuco
 */
public class ErrorWS extends Exception{
    
    
    private String message;
    private String errorcode;
    private String stacktrace;
    private String debuginfo;
    private String exception;
    private String reproductionlink;
    private String json;

    public ErrorWS(){
        super();
    }
    
    public ErrorWS(String message){
        super(message);
        this.message = message;
    }
    
    public ErrorWS(String message,Throwable cause){
        super(message,cause);
        this.message = message;
    }
    
    /**
     * @return the error
     */
    public String getError() {
        return message;
    }
    
    @Override
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.message = error;
    }

    /**
     * @return the errorcode
     */
    public String getErrorcode() {
        return errorcode;
    }

    /**
     * @param errorcode the errorcode to set
     */
    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    /**
     * @return the stacktrace
     */
    public String getStacktrace() {
        return stacktrace;
    }

    /**
     * @param stacktrace the stacktrace to set
     */
    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    /**
     * @return the debuginfo
     */
    public String getDebuginfo() {
        return debuginfo;
    }

    /**
     * @param debuginfo the debuginfo to set
     */
    public void setDebuginfo(String debuginfo) {
        this.debuginfo = debuginfo;
    }

    /**
     * @return the reproductionlink
     */
    public String getReproductionlink() {
        return reproductionlink;
    }

    /**
     * @param reproductionlink the reproductionlink to set
     */
    public void setReproductionlink(String reproductionlink) {
        this.reproductionlink = reproductionlink;
    }

    /**
     * @return the exception
     */
    public String getException() {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }
    
    
}
