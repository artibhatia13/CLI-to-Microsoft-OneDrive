package com.ziroh.onedriveproject;

public class Result {
	/**<b>Short msg description, generally short descriptions of the problem</b>**/
    private String shortMsg = null;
    /**<b>Long message description, long description of the problem, may be stack</b>**/
    private String longMsg = null;
    /**<b>Error code. Success code is always 0</b>**/
    private int errorCode = -10;
    /**<b>Specific error message. For all errors, this value is always filled</b>**/
    private String errorMsg = null;

    /**
     * <b>Gets the short msg description of the problem</b>
     * @return the short msg
     */
    public String getShortMsg() {
        return shortMsg;
    }

    /**
     * <b>Sets the short msg description of the problem</b>
     * @param shortMsg the short msg
     */
    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    /**
     * <b>Gets the long message description of the problem</b>
     * @return the long msg
     */
    public String getLongMsg() {
        return longMsg;
    }

    /**
     * <b>Sets the long message description of the problem</b>
     * @param longMsg the long msg
     */
    public void setLongMsg(String longMsg) {
        this.longMsg = longMsg;
    }

    /**
     * <b>Gets the error code</b>
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * <b>Sets the error code</b>
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * <b>Gets the specific error message</b>
     * @return the error msg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * <b>Sets the specific error message</b>
     * @param errorMsg the error msg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}