package com.ziroh.onedriveproject;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCopyParameterSet;
import com.microsoft.graph.models.DriveItemCreateLinkParameterSet;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.ItemReference;
import com.microsoft.graph.models.Permission;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.models.ItemReference;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.microsoft.graph.tasks.LargeFileUploadTask;
import com.google.gson.JsonPrimitive;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.DriveItemCopyParameterSet;
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
		    	
		    	result.setErrorCode(0);
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
	
	public FutureTask<Result> shareFile(GraphServiceClient graphClient, String fileId) {
		Result result = new Result();
		
		FutureTask<Result> shareFileFutureTask = new FutureTask<>(() -> {
			try {
					String type = "edit";
                    String scope = "anonymous";
			         Permission link = graphClient.me().drive().items(fileId)
			          .createLink(DriveItemCreateLinkParameterSet.newBuilder()
			  		.withType(type)
			  		.withScope(scope)
			  		.withExpirationDateTime(null)
			  		.withMessage(null)
			  		.build())
			  	.buildRequest()
			  	.post();
			         String Url = link.link.webUrl;
			         System.out.println(Url);
                    result.setErrorCode(0);
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
		},result);
		return shareFileFutureTask;
	}
	
	public FutureTask<Result> aboutInformation(GraphServiceClient graphClient) {
        //Stores the result obtain while performing aboutInformation()
        Result result = new Result();
        //The FutureTask which contains the code for aboutInformation()
        FutureTask<Result> storageQuotaFutureTask = new FutureTask<>(() -> {
            try {
            	Drive drive2 = graphClient.me().drive()
             			.buildRequest()
             			.get();
                 
                 System.out.println("deleted: "+drive2.quota.deleted);
                 System.out.println("remaining: "+drive2.quota.remaining);
                 System.out.println("state: "+drive2.quota.state);
                 System.out.println("total: "+drive2.quota.total);
                 System.out.println("used: "+drive2.quota.used);
                 result.setErrorCode(0);
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
        return storageQuotaFutureTask;
    }
	
	public FutureTask<Result> updateFile(GraphServiceClient graphClient, String fileId, String filePath) {
    	Result result = new Result();
    	
    	FutureTask<Result> updateFileFutureTask = new FutureTask<>(() -> {
    		try {
    			File file = new File(filePath);    	
    	    	byte[] fileContent = null;
    			try {
    				fileContent = Files.readAllBytes(file.toPath());
    			} catch (IOException e) {
    				e.printStackTrace();
    			}  	    			
    			graphClient.me().drive().items(fileId).content()
    			.buildRequest()
    			.put(fileContent);   
    			result.setErrorCode(0);
    			
    		} catch(GraphServiceException onedriveError) {
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
        return updateFileFutureTask;
    }
	public FutureTask<CreateDirectoryResult> createDirectory(GraphServiceClient graphClient, String directoryName, String parentId) {
		CreateDirectoryResult result = new CreateDirectoryResult();
		
		FutureTask<CreateDirectoryResult> createDirectoryFutureTask = new FutureTask<>(() -> {
			try {
				DriveItem driveItem = new DriveItem();
		    	driveItem.name = directoryName;
		    	Folder folder = new Folder();
		    	driveItem.folder = folder;
		    	driveItem.additionalDataManager().put("@microsoft.graph.conflictBehavior", new JsonPrimitive("rename"));
		    	graphClient.me().drive().items(parentId).children()
		    		.buildRequest()
		    		.post(driveItem);
		    	result.setId(driveItem.id);
				result.setErrorCode(0);
			} catch(GraphServiceException onedriveError) {
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
    	
//
//    	try {
//			System.out.println("Inside Onedrive func: "+createDirectoryFutureTask.get().getId());
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
    	return createDirectoryFutureTask;
    }
	
	
	public FutureTask<Result>downloadFile(GraphServiceClient graphClient, String fileId, String filePath) {
    	Result result = new Result();
    	
    	FutureTask<Result> downloadFileFutureTask = new FutureTask<>(() -> {
    		try {
    			InputStream stream = (InputStream) graphClient.customRequest("/me/drive/items/"+fileId+"/content", InputStream.class)
    	     			.buildRequest()
    	     			.get();
    			FileOutputStream fileOS = new FileOutputStream(filePath); {
            	    byte data[] = new byte[1024];
            	    int byteContent;
            	    while ((byteContent = stream.read(data, 0, 1024)) != -1) {
            	        fileOS.write(data, 0, byteContent);
            	    }
            	} 
        		result.setErrorCode(0);
        		stream.close();
    		} catch(GraphServiceException onedriveError) {
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
    	return downloadFileFutureTask;
    }
	
	public FutureTask<Result> copyFile(GraphServiceClient graphClient, String fileId, String directoryId,String fileName) {
		Result result = new Result();
		
		FutureTask<Result> copyFileFutureTask = new FutureTask<>(() -> {
			try {
				Drive drive = graphClient.me().drive()
		    			.buildRequest()
		    			.get();
				ItemReference parentReference = new ItemReference();
		    	parentReference.driveId = drive.id;
		    	parentReference.id = directoryId;
		    	graphClient.me().drive().items(fileId)
		    		.copy(DriveItemCopyParameterSet
		    			.newBuilder()
		    			.withName(fileName)
		    			.withParentReference(parentReference)
		    			.build())
		    		.buildRequest()
		    		.post();
		    
				result.setErrorCode(0);
			} catch(GraphServiceException onedriveError) {
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
		
		return copyFileFutureTask;
	}
	
	public FutureTask<Result> deleteFile(GraphServiceClient graphClient, String fileId) {
    	Result result = new Result();
    	
    	FutureTask<Result> deleteFileFutureTask = new FutureTask<>(() ->{
            try {
            	graphClient.me().drive().items(fileId)
            	.buildRequest()
            	.delete();
        		result.setErrorCode(0);
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
    	return deleteFileFutureTask;
    }

	
	public FutureTask<Result> deleteDirectory(GraphServiceClient graphClient, String directoryId) {
    	Result result = new Result();
    	
    	FutureTask<Result> deleteFileFutureTask = new FutureTask<>(() ->{
            try {
            	graphClient.me().drive().items(directoryId)
            	.buildRequest()
            	.delete();
        		result.setErrorCode(0);
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
    	return deleteFileFutureTask;
    }
	
	public FutureTask<Result> copyDirectory(GraphServiceClient graphClient, String sourceId, String destinationId,String fileName) {
		Result result = new Result();
		
		FutureTask<Result> copyDirectoryFutureTask = new FutureTask<>(() -> {
			try {
				Drive drive = graphClient.me().drive()
		    			.buildRequest()
		    			.get();
				ItemReference parentReference = new ItemReference();
		    	parentReference.driveId = drive.id;
		    	parentReference.id = destinationId;
		    	graphClient.me().drive().items(sourceId)
		    		.copy(DriveItemCopyParameterSet
		    			.newBuilder()
		    			.withName(fileName)
		    			.withParentReference(parentReference)
		    			.build())
		    		.buildRequest()
		    		.post();
		    
				result.setErrorCode(0);
			} catch(GraphServiceException onedriveError) {
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
   	return copyDirectoryFutureTask;
   }
	
}
