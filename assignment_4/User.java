package cs445.a4;

/**
 * A user of the streaming radio service.
 */
public interface User {
	
	/**
     * Gets the login of user
     * @return The user login name
     */
	String getUserLogin();
	
	/**
	 * Gets the email address of user
	 * @return The user email address
	 */
	String getUserEmailAddress();

}