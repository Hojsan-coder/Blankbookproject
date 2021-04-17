package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostBean {

// Create Arraylist for each coloumn in database	
	public List<String> postListCreator = new ArrayList<String>();
	public List<String> postListContent = new ArrayList<String>();
	public List<String> postListTag = new ArrayList<String>();
	public List<String> postListDate = new ArrayList<String>();

	private ResultSet rs;

	public PostBean() {
	}
     
	public PostBean(ResultSet rs) {
    this.rs = rs;
    
    addToPostList();
}

	// Add data from database to arraylists
	private void addToPostList() {
		if (rs != null) {
			try {
				while (rs.next()) {
					postListCreator.add(rs.getString(2));
					postListContent.add(rs.getString(3));
					postListTag.add(rs.getString(4));
					postListDate.add(rs.getString(5));
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public ResultSet getResultSet() {
		return rs;
	}

	public void setResultSet(ResultSet rs) {
		this.rs = rs;
	}

}
