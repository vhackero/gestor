package mx.gob.sedesol.gestorweb.commons.utils;

import org.primefaces.json.JSONObject;
import org.primefaces.push.Encoder;

import mx.gob.sedesol.gestorweb.commons.dto.Message;

public class MessageEncoder implements Encoder<Message, String>{

	@Override
	public String encode(Message message) {
		return new JSONObject(message).toString();
	}

}
