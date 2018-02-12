package cs445.a4;

import java.util.Set;

/**
 * A station in the streaming radio service.
 */
public interface Station {
	
	/**
     * Gets the name of radio station
     * @return  The radio station name
     */
	String getStationName();
	
	/**
     * Gets the song play-list for a radio station
     * @return song play-list
     */
	Set<Song> getSongPlayList();
}
