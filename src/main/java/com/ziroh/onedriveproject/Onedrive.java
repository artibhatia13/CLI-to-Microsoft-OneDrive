package com.ziroh.onedriveproject;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.*;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.microsoft.graph.tasks.LargeFileUploadTask;
import com.microsoft.graph.http.GraphServiceException;

public class Onedrive implements ICloudIO {
    
	public FutureTask<FileUploadResult> uploadFile(GraphServiceClient graphClient, String filePath, String onedrivepath) {
        FileUploadResult uploadResult = new FileUploadResult();
        
        
        FutureTask<FileUploadResult> uploadResultFutureTask = new FutureTask<>(() -> {
            try {
            	LargeFileUploadResult<DriveItem> result = null;
            	DriveItemCreateUploadSessionParameterSet uploadParams =
            			DriveItemCreateUploadSessionParameterSet.newBuilder()
            			.withItem(new DriveItemUploadableProperties()).build();  	
    			File file = new File(filePath);
    			InputStream fileStream = null;
    			try {
    				fileStream = new FileInputStream(file);		
    				long streamSize = file.length();				
    				UploadSession uploadSession = graphClient
    				.me()
    				.drive()
    				.root()
    				.itemWithPath(onedrivepath)
    				.createUploadSession(uploadParams)
    				.buildRequest()
    				.post();			
    				LargeFileUploadTask<DriveItem> largeFileUploadTask =
    					new LargeFileUploadTask<DriveItem>
    					(uploadSession, graphClient, fileStream, streamSize, DriveItem.class);
    				
    				try {
    					result = largeFileUploadTask.upload();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			} catch (FileNotFoundException e1) {			
    					e1.printStackTrace();
    			}		
            	uploadResult.setId(result.responseBody.id);
            	uploadResult.setErrorCode(0);
            	fileStream.close();
            } catch(GraphServiceException onedriveError) {
            	uploadResult.setErrorMsg(onedriveError.getMessage());
            	uploadResult.setErrorCode(1);
            	uploadResult.setShortMsg(String.valueOf(onedriveError.getResponseCode()));
            	StringWriter errors = new StringWriter();
            	onedriveError.printStackTrace(new PrintWriter(errors));
                uploadResult.setLongMsg(errors.toString());
            }
            catch (Exception e) {
                uploadResult.setErrorMsg(e.getMessage());
                uploadResult.setErrorCode(1);
                uploadResult.setShortMsg(Arrays.toString(e.getStackTrace()));
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                uploadResult.setLongMsg(errors.toString());
            }
        }, uploadResult);
        
        return uploadResultFutureTask;
	}
	
	public FutureTask<Result> renameFile(GraphServiceClient graphClient, String fileId, String newName) {
		Result result = new Result();
		
		FutureTask<Result> renameFileFutureTask = new FutureTask<>(() -> {
			try {
				System.out.println("Inside Try. GraphClient: "+graphClient);
				DriveItem driveItem = new DriveItem();
		    	driveItem.name = newName;
		    	
		    	graphClient.me().drive().items(fileId)
		    		.buildRequest()
		    		.patch(driveItem);		    	
			} 
			catch(GraphServiceException onedriveError) {
            	result.setErrorMsg(onedriveError.getMessage());
            	result.setErrorCode(1);
            	result.setShortMsg(String.valueOf(onedriveError.getResponseCode()));
            	StringWriter errors = new StringWriter();
            	onedriveError.printStackTrace(new PrintWriter(errors));
                result.setLongMsg(errors.toString());
            } 
			catch(Exception e) {
				result.setErrorMsg(e.getMessage());
                result.setErrorCode(1);
                result.setShortMsg(Arrays.toString(e.getStackTrace()));
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                result.setLongMsg(errors.toString());
			}
		}, result);
		
		return renameFileFutureTask;
	}
	
//	public FutureTask<CreateDirectoryResult> createDirectory(GraphServiceClient graphClient, String directoryName, String parentId) {
//		CreateDirectoryResult result = new CreateDirectoryResult();
//		
//		FutureTask<CreateDirectoryResult> createDirectoryFutureTask = new FutureTask<>(() -> {
//			try {
//				BoxFolder parentFolder;
//				if(parentId.equals("0"))
//					parentFolder = BoxFolder.getRootFolder(api);
//				else
//					parentFolder = new BoxFolder(api, parentId);
//				
//				BoxFolder.Info childFolderInfo = parentFolder.createFolder(directoryName);
//				System.out.println("\nID of the newly created directory: " + childFolderInfo.getID());
//				result.setId(childFolderInfo.getID());
//				result.setErrorCode(0);
//			} catch(BoxAPIException boxError) {
//            	result.setErrorMsg(boxError.getMessage());
//            	result.setErrorCode(1);
//            	result.setShortMsg(String.valueOf(boxError.getResponseCode()));
//            	StringWriter errors = new StringWriter();
//                boxError.printStackTrace(new PrintWriter(errors));
//                result.setLongMsg(errors.toString());
//            }
//			catch(Exception e) {
//				result.setErrorMsg(e.getMessage());
//                result.setErrorCode(1);
//                result.setShortMsg(Arrays.toString(e.getStackTrace()));
//                StringWriter errors = new StringWriter();
//                e.printStackTrace(new PrintWriter(errors));
//                result.setLongMsg(errors.toString());
//			}
//		}, result);
//	
//		return createDirectoryFutureTask;
//	}
}