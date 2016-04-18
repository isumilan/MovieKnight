
public class ServerClientDialogue {
	private int requestType;
	private Object DialogueContent;
	
	public ServerClientDialogue(int type, Object content){
		requestType = type;
		DialogueContent = content;
	}
	
	public int getRequestType(){
		return requestType;
	}
	public Object getDialogueContent(){
		return DialogueContent;
	}
}
