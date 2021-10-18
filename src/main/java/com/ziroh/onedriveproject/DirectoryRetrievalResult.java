package com.ziroh.onedriveproject;

/**
 * <b>Stores information about the directory retrieved via {@link S3#getCloudDirectory(String, String, int)}</b>
 */
public class DirectoryRetrievalResult extends Result {

    /**<b>Retrieved directory</b>**/
    private Directory directoryRetrieved;
    /**<b>Next marker retrieved from the response received from S3</b>**/
    private String marker;

    /**
     * <b>Gets the next {@link DirectoryRetrievalResult#marker}, while listing the contents of a directory.</b>
     * @return the marker
     */
    public String getMarker() {
        return marker;
    }

    /**
     * <b>Sets the next {@link DirectoryRetrievalResult#marker}, while listing the contents of a directory.</b>
     * @param marker the marker
     */
    public void setMarker(String marker) {
        this.marker = marker;
    }

    /**
     * <b>Gets the {@link DirectoryRetrievalResult#directoryRetrieved}</b>
     * @return the directory retrieved
     */
    public Directory getDirectoryRetrieved() {
        return directoryRetrieved;
    }

    /**
     * <b>Sets the {@link DirectoryRetrievalResult#directoryRetrieved}</b>
     * @param directoryRetrieved the directory retrieved
     */
    public void setDirectoryRetrieved(Directory directoryRetrieved) {
        this.directoryRetrieved = directoryRetrieved;
    }
}