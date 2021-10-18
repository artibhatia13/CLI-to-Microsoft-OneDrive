package com.ziroh.onedriveproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import junit.framework.Assert;

public class OnedriveTest
{

	private GraphServiceClient authenticate()
	{			
		final InteractiveBrowserCredential interactiveBrowserCredential = new InteractiveBrowserCredentialBuilder()
    			.tenantId("consumers")
                .clientId("010e4508-08fb-46d4-b21f-63922bb0aa9b")               
                .redirectUrl("http://localhost:8080")
                .build();
    	final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider( interactiveBrowserCredential);
    	
    	GraphServiceClient graphClient =
    	  GraphServiceClient
    	    .builder()
    	    .authenticationProvider(tokenCredentialAuthProvider)
    	    .buildClient();
    	
    	return graphClient;
	}
	
	/**Creates a dummy text file to use during testing
	    * @param fileName The name of the text file to create
	     * @param text Text to input in the file**/
	     private void createDummyTextFile(String fileName, String text) throws IOException {
	        FileOutputStream dummyFile = new FileOutputStream(fileName);
	        byte[] buffer = text.getBytes();
	        dummyFile.write(buffer);
	        dummyFile.close();
	    }
	    /**Deletes the dummy text file created during testing
	     * @param fileName The name of the file to delete**/
	      private boolean deleteDummyTextFile(String fileName) throws IOException {
	        java.io.File fileToDelete = new java.io.File(fileName);
	        return fileToDelete.delete();
	    }
	    @org.junit.jupiter.api.Test
		void renameFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "TestFile.txt";
		        String renamedFileName = "RenamedFile.txt";
		        GraphServiceClient graphClient = authenticate();
	      
		        createDummyTextFile(fileName, "Testing rename file function.");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());
	      
		        FutureTask<Result> renameFileFutureTask = cloudIO.renameFile(graphClient, receivedUploadFileResult.getId(), renamedFileName);
		        new Thread(renameFileFutureTask).start();
		        Result receivedResult = renameFileFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
	      
		        FutureTask<Result> deleteFileFutureTask = cloudIO.deleteFile(graphClient, receivedUploadFileResult.getId());
		        new Thread(deleteFileFutureTask).start();
		        Result result = deleteFileFutureTask.get();
		        assertEquals(0, result.getErrorCode(), result.getErrorMsg());
		        System.out.println("Rename Complete");
		        
		    }
	      
	      	@org.junit.jupiter.api.Test
		    void uploadFile() throws ExecutionException, InterruptedException, IOException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "UploadFile.txt";
		        GraphServiceClient graphClient = authenticate();

		        createDummyTextFile(fileName, "Testing upload file function, successful.");
		        FutureTask<FileUploadResult> uploadFileFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(uploadFileFutureTask).start();
		        FileUploadResult receivedResult = uploadFileFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        FutureTask<Result> deleteFileFutureTask = cloudIO.deleteFile(graphClient, receivedResult.getId());
		        new Thread(deleteFileFutureTask).start();
		        Result result = deleteFileFutureTask.get();
		        assertEquals(0, result.getErrorCode(), result.getErrorMsg());
		        System.out.println("Upload Success");
		    }
	    
	      @org.junit.jupiter.api.Test
		    void shareFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "ShareFile.txt";
		        GraphServiceClient graphClient = authenticate();

		        createDummyTextFile(fileName, "Testing share file function.");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());

		        FutureTask<Result> shareFileFutureTask = cloudIO.shareFile(graphClient, receivedUploadFileResult.getId());
		        new Thread(shareFileFutureTask).start();
		        Result receivedResult = shareFileFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        FutureTask<Result> deleteFileFutureTask = cloudIO.deleteFile(graphClient, receivedUploadFileResult.getId());
		        new Thread(deleteFileFutureTask).start();
		        Result result = deleteFileFutureTask.get();
		        assertEquals(0, result.getErrorCode(), result.getErrorMsg());
		        System.out.println("Sharing Complete");		    
	      }
	      
	      @org.junit.jupiter.api.Test
		    void downloadFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "DownloadFile.txt";
		        String DownloadFile = "DownloadedFileHere.txt";
		        GraphServiceClient graphClient = authenticate();
		        
		        createDummyTextFile(fileName, "Testing download file function, successful.");
		        createDummyTextFile(DownloadFile, "");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());
		        		       
		        FutureTask<Result> downloadFileFutureTask = cloudIO.downloadFile(graphClient, receivedUploadFileResult.getId(),DownloadFile);		    
		        new Thread(downloadFileFutureTask).start();
		        Result receivedResult = downloadFileFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
	            deleteDummyTextFile(fileName);
	            deleteDummyTextFile(DownloadFile);
	        	FutureTask<Result> deleteFutureTask = cloudIO.deleteFile(graphClient,receivedUploadFileResult.getId());
		        new Thread(deleteFutureTask).start();
		        Result deleteResult = deleteFutureTask.get();
		        assertEquals(0, deleteResult.getErrorCode(), deleteResult.getErrorMsg());
		        System.out.println("Download Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
			void aboutInformation() throws IOException, ExecutionException, InterruptedException {
			        ICloudIO cloudIO = new Onedrive();			        
			        GraphServiceClient graphClient = authenticate();
		      
			        FutureTask<Result> renameFileFutureTask = cloudIO.aboutInformation(graphClient);
			        new Thread(renameFileFutureTask).start();
			        Result receivedResult = renameFileFutureTask.get();
			        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());	
			        System.out.println("Storage Info Sucess");
			    }
	      
	      @org.junit.jupiter.api.Test
		    void UpdateFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "UpdateFileTest.txt";
		        String fileName2 = "UpdatedContent.txt";
		        GraphServiceClient graphClient = authenticate();
		        
		        createDummyTextFile(fileName, "Testing Update File function");
		        createDummyTextFile(fileName2, "Content of file has been Updated");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());
		        
		        FutureTask<Result> downloadFileFutureTask = cloudIO.updateFile(graphClient, receivedUploadFileResult.getId(), fileName2);		    
		        new Thread(downloadFileFutureTask).start();
		        Result receivedResult = downloadFileFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
	            deleteDummyTextFile(fileName);
	            deleteDummyTextFile(fileName2);
	        	FutureTask<Result> deleteFutureTask = cloudIO.deleteFile(graphClient,receivedUploadFileResult.getId());
		        new Thread(deleteFutureTask).start();
		        Result deleteResult = deleteFutureTask.get();
		        assertEquals(0, deleteResult.getErrorCode(), deleteResult.getErrorMsg());
		        System.out.println("Update Complete");
		  }
	      	      
	      
	      @org.junit.jupiter.api.Test
		    void copyFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "FileToBeCopied.txt";
		        GraphServiceClient graphClient = authenticate();
		        
		        createDummyTextFile(fileName, "Testing Copying file");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());
		        
		        FutureTask<CreateDirectoryResult> CreateDirFuturetask = cloudIO.createDirectory(graphClient, "Create Dir", "root");		    
		        new Thread(CreateDirFuturetask).start();
		        CreateDirectoryResult createDirResult = CreateDirFuturetask.get();
		        assertEquals(0, createDirResult.getErrorCode());
		        
		        FutureTask<Result> CopyFileFutureTask = cloudIO.copyFile(graphClient, receivedUploadFileResult.getId(), createDirResult.getId(),"NewNameOFFile.txt");		    
		        new Thread(CopyFileFutureTask).start();
		        Result receivedResult = CopyFileFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
	            deleteDummyTextFile(fileName);
	        	FutureTask<Result> deleteFutureTask = cloudIO.deleteFile(graphClient,receivedUploadFileResult.getId());
		        new Thread(deleteFutureTask).start();
		        Result deleteResult = deleteFutureTask.get();
		        assertEquals(0, deleteResult.getErrorCode(), deleteResult.getErrorMsg());
		        		        
		        FutureTask<Result> DeleteDirFuturetask = cloudIO.deleteDirectory(graphClient, createDirResult.getId());		    
		        new Thread(DeleteDirFuturetask).start();
		        Result DeleteDirResult = DeleteDirFuturetask.get();
		        assertEquals(0, DeleteDirResult.getErrorCode(), DeleteDirResult.getErrorMsg());
		        System.out.println("Copy File Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
		    void DeleteFile() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        String fileName = "DeleteFileTest.txt";
		        GraphServiceClient graphClient = authenticate();
		        
		        createDummyTextFile(fileName, "Testing Delete File function");
		        FutureTask<FileUploadResult> fileUploadResultFutureTask = cloudIO.uploadFile(graphClient,fileName,fileName);
		        new Thread(fileUploadResultFutureTask).start();
		        FileUploadResult receivedUploadFileResult = fileUploadResultFutureTask.get();
		        deleteDummyTextFile(fileName);
		        assertEquals(0, receivedUploadFileResult.getErrorCode(), receivedUploadFileResult.getErrorMsg());
		        
	            deleteDummyTextFile(fileName);
	        	FutureTask<Result> deleteFutureTask = cloudIO.deleteFile(graphClient,receivedUploadFileResult.getId());
		        new Thread(deleteFutureTask).start();
		        Result deleteResult = deleteFutureTask.get();
		        assertEquals(0, deleteResult.getErrorCode(), deleteResult.getErrorMsg());
		        System.out.println("Delete File Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
		    void createDirectory() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        GraphServiceClient graphClient = authenticate();
		        
		        FutureTask<CreateDirectoryResult> CreateDirFuturetask = cloudIO.createDirectory(graphClient, "Create Dir", "root");		    
		        new Thread(CreateDirFuturetask).start();
		        CreateDirectoryResult receivedResult = CreateDirFuturetask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        FutureTask<Result> DeleteDirFuturetask = cloudIO.deleteDirectory(graphClient, receivedResult.getId());		    
		        new Thread(DeleteDirFuturetask).start();
		        Result DeleteDirResult = DeleteDirFuturetask.get();
		        assertEquals(0, DeleteDirResult.getErrorCode(), DeleteDirResult.getErrorMsg());
		        
		        System.out.println("Create Directory Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
		    void deleteDirectory() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        GraphServiceClient graphClient = authenticate();
		        
		        FutureTask<CreateDirectoryResult> CreateDirFuturetask = cloudIO.createDirectory(graphClient, "Delete Dir test", "root");		    
		        new Thread(CreateDirFuturetask).start();
		        CreateDirectoryResult CreateDirResult = CreateDirFuturetask.get();
		        assertEquals(0, CreateDirResult.getErrorCode(), CreateDirResult.getErrorMsg());
		        
		        FutureTask<Result> DeleteDirFuturetask = cloudIO.deleteDirectory(graphClient, CreateDirResult.getId());		    
		        new Thread(DeleteDirFuturetask).start();
		        Result receivedResult = DeleteDirFuturetask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        System.out.println("Delete Directory Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
		    void copyDirectory() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        GraphServiceClient graphClient = authenticate();
		        
		        FutureTask<CreateDirectoryResult> CreateChildDirFuturetask = cloudIO.createDirectory(graphClient, "Child Dir", "root");		    
		        new Thread(CreateChildDirFuturetask).start();
		        CreateDirectoryResult createChildDirResult = CreateChildDirFuturetask.get();
		        assertEquals(0, createChildDirResult.getErrorCode());
		        
		        FutureTask<CreateDirectoryResult> CreateParentDirFuturetask = cloudIO.createDirectory(graphClient, "Parent Dir", "root");		    
		        new Thread(CreateParentDirFuturetask).start();
		        CreateDirectoryResult createParentDirResult = CreateParentDirFuturetask.get();
		        assertEquals(0, createParentDirResult.getErrorCode());
		        
		        FutureTask<Result> CopyDirFutureTask = cloudIO.copyDirectory(graphClient, createChildDirResult.getId(), createParentDirResult.getId(),"New Child Name");		    
		        new Thread(CopyDirFutureTask).start();
		        Result receivedResult = CopyDirFutureTask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        FutureTask<Result> DeleteDir1Futuretask = cloudIO.deleteDirectory(graphClient, createChildDirResult.getId());		    
		        new Thread(DeleteDir1Futuretask).start();
		        Result receivedResult1 = DeleteDir1Futuretask.get();
		        assertEquals(0, receivedResult1.getErrorCode(), receivedResult1.getErrorMsg());
		        
		        FutureTask<Result> DeleteDir2Futuretask2 = cloudIO.deleteDirectory(graphClient, createParentDirResult.getId());		    
		        new Thread(DeleteDir2Futuretask2).start();
		        Result receivedResult2 = DeleteDir2Futuretask2.get();
		        assertEquals(0, receivedResult2.getErrorCode(), receivedResult2.getErrorMsg());
		        System.out.println("Copy Directory Complete");
		  }
	      
	      @org.junit.jupiter.api.Test
		    void GetCloudDirectory() throws IOException, ExecutionException, InterruptedException {
		        ICloudIO cloudIO = new Onedrive();
		        GraphServiceClient graphClient = authenticate();
		        
		        FutureTask<CreateDirectoryResult> CreateDirFuturetask = cloudIO.createDirectory(graphClient, "GetCloud Test", "root");		    
		        new Thread(CreateDirFuturetask).start();
		        CreateDirectoryResult CreateDirResult = CreateDirFuturetask.get();
		        assertEquals(0, CreateDirResult.getErrorCode(), CreateDirResult.getErrorMsg());	
		        
		        FutureTask<CreateDirectoryResult> CreateSubDirFuturetask = cloudIO.createDirectory(graphClient, "Sub Dir", CreateDirResult.getId());		    
		        new Thread(CreateSubDirFuturetask).start();
		        CreateDirectoryResult CreateSubDirResult = CreateSubDirFuturetask.get();
		        assertEquals(0, CreateSubDirResult.getErrorCode(), CreateSubDirResult.getErrorMsg());
		        
		        FutureTask<DirectoryRetrievalResult> GetCLoudFuturetask = cloudIO.GetCloudDirectory(graphClient, CreateDirResult.getId(), "0", "2");		    
		        new Thread(GetCLoudFuturetask).start();
		        DirectoryRetrievalResult receivedResult = GetCLoudFuturetask.get();
		        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
		        
		        FutureTask<Result> DeleteDir2Futuretask2 = cloudIO.deleteDirectory(graphClient, CreateSubDirResult.getId());		    
		        new Thread(DeleteDir2Futuretask2).start();
		        Result receivedResult2 = DeleteDir2Futuretask2.get();
		        assertEquals(0, receivedResult2.getErrorCode(), receivedResult2.getErrorMsg());
		        
		        FutureTask<Result> DeleteDir1Futuretask = cloudIO.deleteDirectory(graphClient, CreateDirResult.getId());		    
		        new Thread(DeleteDir1Futuretask).start();
		        Result receivedResult1 = DeleteDir1Futuretask.get();
		        assertEquals(0, receivedResult1.getErrorCode(), receivedResult1.getErrorMsg());		        		        
		        
		        System.out.println("GetCloudDirectory Complete");
		  }
}


