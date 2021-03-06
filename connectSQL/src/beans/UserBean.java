package beans;

import database.SQLcon;

public class UserBean {
	private String name, password, username;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;

	}

	public boolean validate(UserBean bean) {

		if (SQLcon.connectSQL()) {
			return SQLcon.stateSQL(bean);
		}

		return false;
	}

	public void resetUserBean() {
		this.password = null;
		this.name = null;
	}

}
