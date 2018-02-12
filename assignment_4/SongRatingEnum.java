package cs445.a4;

public enum SongRatingEnum {
    UNRATED  (0),
    DONT_LIKE  (1),
    MAY_LIKE  (2),
    DO_LIKE  (3),
    DO_LOVE (4),
	IS_FAV (5);
	
	int numStars;
	
	private SongRatingEnum(int numStars)
    {
        this.numStars =  numStars;
    }
}

