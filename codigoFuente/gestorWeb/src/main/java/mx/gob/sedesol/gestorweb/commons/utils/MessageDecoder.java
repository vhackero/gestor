package mx.gob.sedesol.gestorweb.commons.utils;

import org.primefaces.push.Decoder;

import mx.gob.sedesol.gestorweb.commons.dto.Message;

public class MessageDecoder implements Decoder<String, Message>{

	@Override
	public Message decode(String s) {
		String[] userAndMessage = s.split(":");
        if (userAndMessage.length >= 2) {
            return new Message().setUser(userAndMessage[0]).setText(userAndMessage[1]);
        } 
        else {
            return new Message(s);
        }
	}

}
