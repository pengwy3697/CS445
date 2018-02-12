package cs445.a4;

public interface UserRating {
	
	/**
     * Gets the user has performed the song rating
     * @return The user
     */
	User getRatingUser();
	
	/**
     * Gets the song rating
     * @return The song rating
     */
	SongRatingEnum getSongRating();
}


