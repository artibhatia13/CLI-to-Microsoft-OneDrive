package com.ziroh.onedriveproject;

public class CreateDirectoryResult extends Result{
	/**<b>Generated ID of the file or directory that got created</b>**/
    private String id;

    /**
     * <b>Gets the generated ID of the file or directory that got created</b>
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * <b>Sets the generated ID of the file or directory that got created</b>
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}