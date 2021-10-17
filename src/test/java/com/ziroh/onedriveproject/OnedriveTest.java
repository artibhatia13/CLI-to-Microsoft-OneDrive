/*package com.ziroh.onedriveproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
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
	}*/
	
	/**Creates a dummy text file to use during testing
	    * @param fileName The name of the text file to create
	     * @param text Text to input in the file**/
	    /*private void createDummyTextFile(String fileName, String text) throws IOException {
	        FileOutputStream dummyFile = new FileOutputStream(fileName);
	        byte[] buffer = text.getBytes();
	        dummyFile.write(buffer);
	        dummyFile.close();*/
	    //}
	    /**Deletes the dummy text file created during testing
	     * @param fileName The name of the file to delete**/
	   /* private boolean deleteDummyTextFile(String fileName) throws IOException {
	        java.io.File fileToDelete = new java.io.File(fileName);
	        return fileToDelete.delete();
	    }
	    @org.junit.jupiter.api.Test
	    void uploadFile() throws ExecutionException, InterruptedException, IOException {
	        ICloudIO cloudIO = new Onedrive();
	        String fileName = "UploadFile.txt";
	        String fileId = "148010546070";
	        GraphServiceClient graphClient = authenticate();

	        createDummyTextFile(fileName, "Testing upload file function, successful.");
	        FutureTask<FileUploadResult> uploadFileFutureTask = cloudIO.uploadFile(graphClient,fileId, path);
	        new Thread(uploadFileFutureTask).start();
	        FileUploadResult receivedResult = uploadFileFutureTask.get();
	        deleteDummyTextFile(fileName);
	        assertEquals(0, receivedResult.getErrorCode(), receivedResult.getErrorMsg());
	        
	        FutureTask<Result> deleteFileFutureTask = cloudIO.deleteFile(graphClient, receivedResult.getId());
	        new Thread(deleteFileFutureTask).start();
	        Result result = deleteFileFutureTask.get();
	        assertEquals(0, result.getErrorCode(), result.getErrorMsg());
	    }*/
	    
	    

//}