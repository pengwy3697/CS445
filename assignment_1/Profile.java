package a1;


public class Profile implements ProfileInterface {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 3529997638549299013L;
	
	String name;
	String about;
	SetInterface<ProfileInterface> followSet;

	/**
	 * 
	 * @param newName
	 * @param newAbout
	 */
	public Profile(String newName, String newAbout) {
		setName(newName);
		setAbout(newAbout);
		followSet = new Set<ProfileInterface>();
	}
	
    /**
     * Sets this profile's name.
     *
     * <p> If newName is not null, then setName modifies this profile so that
     * its name is newName. If newName is null, then setName throws
     * IllegalArgumentException without modifying the profile, for example:
     *
     * <p> {@code throw new IllegalArgumentException("Name cannot be null")}
     *
     * @param newName  The new name
     * @throws IllegalArgumentException  If newName is null
     */
    public void setName(String newName) throws IllegalArgumentException {
    	if (newName == null || newName.length() == 0)
    	{
    		throw new IllegalArgumentException("Profile-Name cannot be null or empty");
    	}
    	
    	name = newName;
    }

    /**
     * Gets this profile's name.
     *
     * @return  The name
     */
    public String getName() {
    	return name;
    }

    /**
     * Sets this profile's "about me" blurb.
     *
     * <p> If newAbout is not null, then setAbout modifies this profile so that
     * its about blurb is newAbout. If newAbout is null, then setAbout throws
     * IllegalArgumentException without modifying the profile.
     *
     * @param newAbout  The new blurb
     * @throws IllegalArgumentException  If newAbout is null
     */
    public void setAbout(String newAbout) throws IllegalArgumentException {
    	if (newAbout == null || newAbout.length() == 0)
    	{
    		throw new IllegalArgumentException("Profile-Name cannot be null or empty");
    	}
    	
    	about = newAbout;
    }

    /**
     * Gets this profile's "about me" blurb
     *
     * @return  The blurb
     */
    public String getAbout() {
    	return about;
    }

    /**
     * Adds another profile to this profile's following set.
     *
     * <p> If this profile's following set is at capacity, or if other is null,
     * then follow returns false without modifying the profile. Otherwise, other
     * is added to this profile's following set and follow returns true. If this
     * profile already followed other, then follow returns true even though no
     * changes were needed.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
    public boolean follow(ProfileInterface other) {
    	if (other == null) {
    		return false;
    	}
    	
    	try {
    		followSet.add(other);
    	} catch (SetFullException sfe) { // profileSet is at capacity
    		return false;
    	}
    	return true;
    }

    /**
     * Removes the specified profile from this profile's following set.
     *
     * <p> If this profile's following set does not contain other, or if other
     * is null, then unfollow returns false without modifying the profile.
     * Otherwise, this profile in modified in such a way that other is removed
     * from this profile's following set.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
    public boolean unfollow(ProfileInterface other) {
    	if (other == null || !followSet.contains(other)) {
    		return false;
    	}
    	
    	followSet.remove(other);
    	return true;
    }

    /**
     * Returns a preview of this profile's following set.
     *
     * <p> The howMany parameter is a maximum desired size. The returned array
     * may be less than the requested size if this profile is following fewer
     * than howMany other profiles. Clients of this method must be careful to
     * check the size of the returned array to avoid
     * ArrayIndexOutOfBoundsException.
     *
     * <p> Specifically, following returns an array of size min(howMany, [number
     * of profiles that this profile is following]). This array is populated
     * with arbitrary profiles that this profile follows.
     *
     * @param howMany  The maximum number of profiles to return
     * @return  An array of size &le;howMany, containing profiles that this
     * profile follows
     */
    public ProfileInterface[] following(int howMany) {
    	
    	Object[] profileArray = followSet.toArray();
    	for (Object o : profileArray) {
    		ProfileInterface p=(ProfileInterface)o;
    		//System.out.println("follower=" + p.getName());
    	}
    	int profileSize =followSet.getCurrentSize();
    	int arraySize = (profileSize < howMany ? profileSize : howMany);
    	
    	ProfileInterface[] followArray = null;
    	if (arraySize > 0) {
    		followArray = new Profile[arraySize];
    		System.arraycopy(profileArray, 0, followArray, 0, arraySize);
    	} else {
    		followArray = new Profile[0];
    	}
    	
    	return followArray;
    }

    /**
     * Recommends a profile for this profile to follow. This returns a profile
     * followed by one of this profile's followed profiles. Should not recommend
     * this profile to follow someone they already follow, and should not
     * recommend to follow oneself.
     *
     * <p> For example, if this profile is Alex, and Alex follows Bart, and Bart
     * follows Crissy, this method might return Crissy.
     *
     * @return  The profile to suggest, or null if no suitable profile is found
     * (only if all of this profile's followees' followees are already followed
     * or this profile itself).
     */
    public ProfileInterface recommend() {
    	SetInterface<ProfileInterface> recommSet=new Set<ProfileInterface>(20);
    	// add all followers' followers to the recommSet
    	Object[] followArray = followSet.toArray();
    	for (Object obj : followArray) { 
    		ProfileInterface follower = (ProfileInterface)obj;
    		ProfileInterface[] followerChildwArray = follower.following(10);
    		for (ProfileInterface followerChild : followerChildwArray) {
    			try {
    				//System.out.println("adding to recommSet: " + followerChild.getName());
        			recommSet.add(followerChild); 
        		} catch (SetFullException sef) {
        			break;
        		}
    		}
    	}
    	
    	// add followers' child to recommSet
    	while (!recommSet.isEmpty()) {
    		Object[] recommArray=recommSet.toArray();
    		for (Object obj : recommArray) {
    			ProfileInterface recommProfile=(ProfileInterface)obj;
    			//System.out.println("recommSet contains " + recommProfile.getName());
    			if (!followSet.contains(recommProfile) && !this.equals(recommProfile)) {
    				return recommProfile;
    			} else {
    				ProfileInterface[] followerChildwArray = recommProfile.following(10);
    				for (ProfileInterface profile : followerChildwArray) {
    					if (!followSet.contains(profile) && !this.equals(recommProfile)) {
	    	    			try {
	    	    				//System.out.println("adding to recommSet: " + profile.getName());
	    	        			recommSet.add(profile); 
	    	        		} catch (SetFullException sef) {
	    	        		}
    					}
    				}
    				recommSet.remove(recommProfile);
    			}
    		}
    	}
    	
    	return null;
    }
}
