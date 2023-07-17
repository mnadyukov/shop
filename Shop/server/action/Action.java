package server.action;

import server.User;
import server.utilities.Structure;

public interface Action {

	public String execute(Structure message, User user);
	
}
