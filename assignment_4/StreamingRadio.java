package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

	/**
	 * Adds a new song to the system.
	 * @param theSong song to be added
	 * @param theAdminUser user with administrative privilege
	 * @param accessToken authentication token to identify user administrative privilege and will expire
	 * @return the song added to system with songId populated
	 * @throws IllegalArgumentException if the song already exists in system,
	 * or theAdminUser does not exist in system,
	 * or invalid / expired access token, or access token without sufficient privilege
	 * @throws NullPointerException if either the song or user is null or accessToken is null
	 */
    Song addSong(Song theSong, User theAdminUser, String accessToken)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Removes an existing song from the system.
     * @param theSong song to be removed
     * @param theAdminUser user with administrative privilege
     * @param accessToken authentication token to identify user administrative privilege and will expire
	 * @throws IllegalArgumentException if the song does not exist in system,
	 * or theAdminUser does not exist in system,
	 * or invalid / expired access token, or access token without sufficient privilege
	 * @throws NullPointerException if either the song or user is null or accessToken is null
     */
    void removeSong(Song theSong, User theAdminUser, String accessToken)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Adds an existing song to the play-list of an existing radio station.
     * @param theSong the song to be added to radio station play-list
     * @param theStation the radio station
     * @param theStationUser radio station user
     * @param accessToken authentication token to identify user administrative privilege and will expire
     * @return 0 if theSong is added to station,
     * -1 if the song is found in the radio station play-list,
     * @throws IllegalArgumentException if the station cannot be found in system,
     * or the song cannot be found in system,
     * or the user does not exist in system,
	 * or invalid / expired access token, or access token without sufficient privilege
     * @throws NullPointerException if the station or the song or the user is null
	 * 
     */
    int addToStation(Station theStation, Song theSong, User theStationUser, String accessToken)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Removes a song from the radio station play-list.
     * @param theStation the radio station
     * @param theSong the song to be removed
     * @param theStationUser radio station user
     * @param accessToken authentication token to identify user administrative privilege and will expire
	 * @throws IllegalArgumentException if the station cannot be found in system,
	 * or the song cannot be found in system, or the song is not in radio station play-list,
	 * or the user does not exist in system
	 * or invalid / expired access token or access token without sufficient privilege
     * @throws NullPointerException if the station or the song or the user is null or accessToke is null
     */
    void removeFromStation(Station theStation, Song theSong, User theStationUser, String accessToken)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Sets a user's rating for a song, as a number of stars from 1 to 5.
     * Previous song rating set by the same user will be overridden.
     * @param theSong the song to be rated
     * @param theUser the theUser performed the song rating
     * @param theRating the song rating 
     * @return 0 if the song is assigned the rating, 1 if the previous song rating is overridden
     * @throws NullPointerException if the song or the user is null 
	 * @throws IllegalArgumentException if either the song, or the user cannot be found in system,
	 * or the rating is UNRATED
     */
    int rateSong(Song theSong, User theUser, EnumSongRating theRating)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Clears a theUser's rating on a song. If this theUser has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this theUser has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser theUser whose rating should be cleared
     * @param theSong song from which the theUser's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * 	rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Predicts the rating a user will assign to a song that they have not yet
     * rated, as a number of stars from 1 to 5.
     * The search engine prediction algorithms are based on
     * (1) Previous rating of the same artist assigned by user
     * (2) Previous rating of the Song Country, Language, Artist Group,
     *     Lyric Type, Musical Instrument, Musical Tempo assigned by user
     * (3) Average song rating set by other users
     * 
     * @param theSong song to be predicted for rating
     * @param theUser theUser of which the prediction is for
     * @return song rating
     * @throws IllegalArgumentException if the song or user does not exist in system,
     * 	or the song has already been rated by user
     * @throws NullPointerException if either the user or the song is null
     */
    EnumSongRating predictRating(Song theSong, User theUser)
    	throws IllegalArgumentException, NullPointerException;

    /**
     * Suggests a song predicted to be the user favorite.
     * The search engine recommendation algorithms are based on
     * (1) Song artists previous added by user
     * (2) Song Country, Language, Artist Group, Lyric Type, Musical Instrument,
     *     Musical Tempo of the songs previous added by user
     * (3) Songs with high rating
     * (4) Popular Seasonal Songs
     * 
     * @param theUser the theUser to be suggested for song
     * @return song predicted to be theUser's favorite
     * @throws IllegalArgumentException if the user does not exist in system
     * @throws NullPointerException if the user is null
     */
    Song suggestSong(User theUser)
    	throws IllegalArgumentException, NullPointerException;

}

