package mx.gob.sedesol.gestorweb.commons.dto;

public class ServicioCURP {
	
	private RespuestaCURP response;
	private String errors;
	private String serviceStatus;
	private String serviceResponse;
	private String serviceDetails;
	public RespuestaCURP getResponse() {
		return response;
	}
	public void setResponse(RespuestaCURP response) {
		this.response = response;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getServiceResponse() {
		return serviceResponse;
	}
	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	public String getServiceDetails() {
		return serviceDetails;
	}
	public void setServiceDetails(String serviceDetails) {
		this.serviceDetails = serviceDetails;
	}
	@Override
	public String toString() {
		return "ServicioCURP [response=" + response + ", errors=" + errors
				+ ", serviceStatus=" + serviceStatus + ", serviceResponse="
				+ serviceResponse + ", serviceDetails=" + serviceDetails + "]";
	}

}
