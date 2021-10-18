package com.ziroh.onedriveproject;

import java.time.OffsetDateTime;

/**
 * The type File.
 */
public class File {

    /**<b>Identifier of the file. Each file has a unique identifier.</b>**/
    private String id = null;
    /**<b>Name of the file. Name do not include extensions</b>**/
    private String name = null;
    /**<b> Mime-Type of the File. We follow the Mime-Types as found in<br/>
     https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIM E_types
     </b>**/
    private String mimeType = null;
    /**<b>Common extension of the file</b>**/
    private String extension = null;
    /**<b>ID of the parent</b>**/
    private String parentId = null;
    /**<b>Time when the file is created. We follow unix-time-convention</b>**/
    private OffsetDateTime createdOn ;
    /**<b>Time at which the file is last modified</b>**/
    private OffsetDateTime lastModified ;
    /**<b>Time at which file was last accessed. This is different from modified last. Modified last is applied when a file is last updated.</b>**/
    private double lastAccessed = 0;
    /**<b>The absolute path of the file at the source. Source may be your desktop, laptop or a mobile phone device</b>**/
    private String originatingAbsolutePath = null;
    /**<b>Device name from where the file originated</b>**/
    private String originDeviceName = null;
    /**<b>Information on where the file is currently stored</b>**/
//    private CloudStorageInfo storeInCLoud = null;//
    /**<b>Absolute path with Ziroh File System</b>**/
    private String absolutePath = null;
    /**<b>User IDs with whom the file is shared</b>**/
    private String[] sharedUserIdCollection = null;
    /**<b>The thumb nail bytes</b>**/
    private String thumbNailBytes = null;//
    /**<b>The migration source data</b>**/
 //   private MigrateData migrationSourceData;//
    /**<b>The parent identifier to restore from Recycle Bin</b>**/
    private String recycleBinRestoreParentId = null;//
    /**<b> Version id of the item, when updated last. Server will calculate the hash of the current Version ID at server, if matches, will update the record.</b>**/
    private String version = null;//
    /**<b>Mounted cloud name</b>**/
    private String mountedCloudName = null;//
    /**<b>Expiry date of the file if shared</b>**/
    private long sharedExpiryDate = 0;

    /**
     * <b>Gets the unique identifier of a file</b>
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * <b>Sets the unique identifier of a file</b>
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <b>Gets the name of a file without extension</b>
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <b>Sets the name of a file without extension</b>
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <b>Gets the mime type of a file</b>
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * <b>Sets the mime type of a file</b>
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * <b>Gets the common extension of a file</b>
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * <b>Sets the common extension of a file</b>
     * @param extension the extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * <b>Gets the ID of the parent</b>
     * @return the parent id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * <b>Sets the ID of the parent</b>
     * @param parentId the parent id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * <b>Gets the time when the file is created</b>
     * @return the created on
     */
    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    /**
     * <b>Sets the time when the file is created</b>
     * @param createdDateTime the created on
     */
    public void setCreatedOn(OffsetDateTime createdDateTime) {
        this.createdOn = createdDateTime;
    }

    /**
     * <b>Gets the time at which the file is last modified</b>
     * @return the last modified
     */
    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    /**
     * <b>Sets the time at which the file is last modified</b>
     * @param lastAccessedDateTime the last modified
     */
    public void setLastModified(OffsetDateTime lastAccessedDateTime) {
        this.lastModified = lastAccessedDateTime;
    }

    /**
     * <b>Gets the time at which the file is last accessed</b>
     * @return the last accessed
     */
    public double getLastAccessed() {
        return lastAccessed;
    }

    /**
     * <b>Gets the time at which the file is last accessed</b>
     * @param lastAccessed the last accessed
     */
    public void setLastAccessed(double lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    /**
     * <b>Gets the absolute path of the file at source</b>
     * @return the originating absolute path
     */
    public String getOriginatingAbsolutePath() {
        return originatingAbsolutePath;
    }

    /**
     * <b>Sets the absolute path of the file at source</b>
     * @param originatingAbsolutePath the originating absolute path
     */
    public void setOriginatingAbsolutePath(String originatingAbsolutePath) {
        this.originatingAbsolutePath = originatingAbsolutePath;
    }

    /**
     * <b>Gets the device name from where the file originated</b>
     * @return the origin device name
     */
    public String getOriginDeviceName() {
        return originDeviceName;
    }

    /**
     * <b>Sets the device name from where the file originated</b>
     * @param originDeviceName the origin device name
     */
    public void setOriginDeviceName(String originDeviceName) {
        this.originDeviceName = originDeviceName;
    }

    /**
     * <b>Gets the information on where the file is currently stored</b>
     * @return the store in c loud
     */
//    public CloudStorageInfo getStoreInCLoud() {
//        return storeInCLoud;
//    }

    /**
     * <b>Sets the information on where the file is currently stored</b>
     * @param storeInCLoud the store in c loud
     */
//    public void setStoreInCLoud(CloudStorageInfo storeInCLoud) {
//        this.storeInCLoud = storeInCLoud;
//    }

    /**
     * <b>Gets the absolute path with Ziroh File System</b>
     * @return the absolute path
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * <b>Sets the absolute path with Ziroh File System</b>
     * @param absolutePath the absolute path
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    /**
     * <b>Gets the user IDs with whom the file is shared </b>
     * @return the string [ ]
     */
    public String[] getSharedUserIdCollection() {
        return sharedUserIdCollection;
    }

    /**
     * <b>Sets the user IDs with whom the file is shared </b>
     * @param sharedUserIdCollection the shared user id collection
     */
    public void setSharedUserIdCollection(String[] sharedUserIdCollection) {
        this.sharedUserIdCollection = sharedUserIdCollection;
    }

    /**
     * <b>Gets the thumb nail bytes</b>
     * @return the thumb nail bytes
     */
    public String getThumbNailBytes() {
        return thumbNailBytes;
    }

    /**
     * <b>Sets the thumb nail bytes</b>
     * @param thumbNailBytes the thumb nail bytes
     */
    public void setThumbNailBytes(String thumbNailBytes) {
        this.thumbNailBytes = thumbNailBytes;
    }

    /**
     * <b>Gets the migration source data</b>
     * @return the migration source data
     */
//    public MigrateData getMigrationSourceData() {
//        return migrationSourceData;
//    }
//
//    /**
//     * <b>Sets the migration source data</b>
//     * @param migrationSourceData the migration source data
//     */
//    public void setMigrationSourceData(MigrateData migrationSourceData) {
//        this.migrationSourceData = migrationSourceData;
//    }

    /**
     * <b>Gets the parent identifier to restore from Recycle Bin</b>
     * @return the recycle bin restore parent id
     */
    public String getRecycleBinRestoreParentId() {
        return recycleBinRestoreParentId;
    }

    /**
     * <b>Sets the parent identifier to restore from Recycle Bin</b>
     * @param recycleBinRestoreParentId the recycle bin restore parent id
     */
    public void setRecycleBinRestoreParentId(String recycleBinRestoreParentId) {
        this.recycleBinRestoreParentId = recycleBinRestoreParentId;
    }
    /**
     * <b>Gets the version id of the item, when last updated</b>
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * <b>Sets the version id of the item, when last updated</b>
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * <b>Gets the mounted cloud name</b>
     * @return the mounted cloud name
     */
    public String getMountedCloudName() {
        return mountedCloudName;
    }

    /**
     * <b>Sets the mounted cloud name</b>
     * @param mountedCloudName the mounted cloud name
     */
    public void setMountedCloudName(String mountedCloudName) {
        this.mountedCloudName = mountedCloudName;
    }

    /**
     * <b>Gets the expiry date of the file if shared</b>
     * @return the shared expiry date
     */
    
    public long getSharedExpiryDate() {
        return sharedExpiryDate;
    }

    /**
     * <b>Sets the expiry date of the file if shared</b>
     * @param sharedExpiryDate the shared expiry date
     */
    public void setSharedExpiryDate(long sharedExpiryDate) {
        this.sharedExpiryDate = sharedExpiryDate;
    }
}