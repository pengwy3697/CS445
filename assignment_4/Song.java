package cs445.a4;

import java.util.Date;
import java.util.Set;

/**
 * A song, which can be used in the radio streaming service.
 */
public interface Song {
	
    /**
     * Gets the title of song
     * @return  The song title
     */
	String getSongTitle();
	
	/**
     * Gets the artist of song
     * @return  The song artist
     */
	String getSongArtist();
	
	/**
     * Gets the composer of song
     * @return  The song composer
     */
	String getSongComposer();
	
	/**
     * Gets the system generated unique id of song to facilitate searching
     * @return  The system generated unique song Id
     */
	String getSongId();
	
	/**
     * Gets the list of users and corresponding song rating
     * @return  The list of user and corresponding song srating
     */
	Set<UserRating> getUserRatingList();
	
	/**
     * Gets the average rating of song in single decimal
     * @return The average song rating
     */
	double getAverageRating();

	/**
	 * Gets the language of song
	 * @return the language of the song
	 */
	EnumLanguage  getSongLanguage();
	/**
	 * Gets the tempo of song 
	 * @return the tempo of the song
	 */
	EnumMusicalTempo getSongTempo();
	/**
	 * Gets the main instruments used in song
	 * @return main instrument of the song
	 */
	EnumMusicalInstrument getSongInstrument();
	/**
	 * Gets the type of lyrics of the song
	 * @return the type of lyrics of the song
	 */
	EnumLyricType getSongLyricType();

}
