package mx.gob.sedesol.gestorweb.chat.endpoint;

import javax.servlet.ServletContext;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.sedesol.gestorweb.beans.chat.ChatUsers;
import mx.gob.sedesol.gestorweb.commons.dto.Message;
import mx.gob.sedesol.gestorweb.commons.utils.MessageDecoder;
import mx.gob.sedesol.gestorweb.commons.utils.MessageEncoder;

@PushEndpoint("{room}/{user}")
@Singleton
public class ChatResource {
	
	@PathParam("room")
    private String room;
 
    @PathParam("user")
    private String username;
 
    @Autowired
    private ServletContext context;
 
    @OnOpen
    public void onOpen(RemoteEndpoint r) {
    	EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(room + "/*", new Message(String.format("%s se ha conectado '%s'",  username, room), true));
    }
 
    @OnClose
    public void onClose(RemoteEndpoint r) {
        ChatUsers users= (ChatUsers) getContext().getAttribute("chatUsers");
        users.remove(username);
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(room + "/*", new Message(String.format("%s se ha desconectado", username), true));
    }
 
    @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
    public Message onMessage(Message message) {
        return message;
    }

	private ServletContext getContext() {
		return context;
	}

	private void setContext(ServletContext context) {
		this.context = context;
	}

}