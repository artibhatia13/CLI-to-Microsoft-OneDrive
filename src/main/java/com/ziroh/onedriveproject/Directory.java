package com.ziroh.onedriveproject;

/**
 * <b>Stores information about a directory</b>
 */
public class Directory extends File {

    /**<b>Collection of subdirectories present in the current directory</b>**/
    private Directory[] subDirectoryCollection;
    /**<b>Collection of files present in the current directory</b>**/
    private File[] fileCollection;

    /**
     * <b>To get the subdirectories inside the current directory at level 1</b>
     * @return the directory [ ] the sub-directory collection
     */
    public Directory[] getSubDirectoryCollection() {
        return subDirectoryCollection;
    }

    /**
     * <b>To set the subdirectories inside the current directory at level 1</b>
     * @param subDirectoryCollection the sub-directory collection
     */
    public void setSubDirectoryCollection(Directory[] subDirectoryCollection) {
        this.subDirectoryCollection = subDirectoryCollection;
    }

    /**
     * <b>To get the files inside the current directory at level 1</b>
     * @return the file [ ] inside the current directory at level 1
     */
    public File[] getFileCollection() {
        return fileCollection;
    }

    /**
     * <b>To set the files inside the current directory at level 1</b>
     * @param fileCollection the file collection
     */
    public void setFileCollection(File[] fileCollection) {
        this.fileCollection = fileCollection;
    }

}