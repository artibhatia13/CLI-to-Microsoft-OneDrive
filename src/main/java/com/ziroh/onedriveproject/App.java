package com.ziroh.onedriveproject;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.FileSystemInfo;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.DriveItemContentStreamRequest;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.tasks.*;

import okhttp3.Request;

import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App 
{	
	//Variables
	@SuppressWarnings("rawtypes")
	static GraphServiceClient graphClient = null;	
	private static final String NULL = "";
	private static ICloudIO onedrive;	
	
	//Methods	  
    public static void main( String[] args ) throws ExecutionException, InterruptedException
    {    	    	
    	//Authentication
    	final InteractiveBrowserCredential interactiveBrowserCredential = new InteractiveBrowserCredentialBuilder()
    			.tenantId("consumers")
                .clientId("010e4508-08fb-46d4-b21f-63922bb0aa9b")               
                .redirectUrl("http://localhost:8080")
                .build();
    	final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider( interactiveBrowserCredential);
    	
    	graphClient =
    	  GraphServiceClient
    	    .builder()
    	    .authenticationProvider(tokenCredentialAuthProvider)
    	    .buildClient();
    	
    	final User me = graphClient.me().buildRequest().get();
    	
    	onedrive = new Onedrive();
    	
//    	renameFile();
    	uploadFile();
    	
    	//update
//    	File file = new File("C:\\Users\\ADMIN\\Videos\\Captures\\testing.png");    	
//    	byte[] fileContent = null;
//		try {
//			fileContent = Files.readAllBytes(file.toPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}    	
//		
//		graphClient.me().drive().items("DF40F1033C268359!125").content()
//		.buildRequest()
//		.put(fileContent);
    }
    
    private static void uploadFile() throws ExecutionException, InterruptedException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the path of the file to be uploaded: ");
		String filePath = sc.nextLine();
		System.out.print("Enter the path of the parent folder in onedrive: ");
		String onedrivepath = sc.nextLine();
		System.out.println("inside upload func: "+onedrive);
		FileUploadResult result = getResultWhenDone(onedrive.uploadFile(graphClient, filePath, onedrivepath), NULL);
		printResult(result);
    }
	
    private static void downloadFile() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter id of file to be downloaded: ");
		String fileId = sc.nextLine();
		System.out.println("Starting download");
		Result result = getResultWhenDone(onedrive.downloadFile(graphClient, fileId));
		printResult(result);
    }
	
	
    
    private static void renameFile() throws ExecutionException, InterruptedException {
      Scanner sc = new Scanner(System.in);      
      System.out.println("Id of the file to rename: ");
      String fileId = sc.nextLine();      
      System.out.println("New name of the file: ");
      String newName = sc.nextLine();           
      Result renameResult = getResultWhenDone(onedrive.renameFile(graphClient, fileId, newName));
      printResult(renameResult);
    }
    
//    private static void createDirectory() throws ExecutionException, InterruptedException {
//		Scanner sc = new Scanner(System.in);
//		
//    	System.out.print("Enter the name of the directory to be created: ");
//    	directoryName = sc.nextLine();
//    	
//    	System.out.print("Enter the id of the parent folder(Enter \'0\' for the root folder): ");
//    	parentId = sc.nextLine();
//    	
//    	CreateDirectoryResult result = getResultWhenDone(onedrive.createDirectory(graphClient, directoryName, parentId), 0);
//    	printResult(result);
//    }
    
    private static FileUploadResult getResultWhenDone(FutureTask<FileUploadResult> uploadResultFutureTask, String NULL) throws InterruptedException, ExecutionException {
        new Thread(uploadResultFutureTask).start();
        new Thread(() -> {
          while(!uploadResultFutureTask.isDone()){
            try {
              Thread.sleep(1000);
              if(!uploadResultFutureTask.isDone())
              System.err.print("# ");
            }catch (InterruptedException ignore){}
          }
        }).start();
        Thread.sleep(500);
        System.out.print("\nProcessing the upload and waiting for the result...");
        return uploadResultFutureTask.get();
      }
    
    private static Result getResultWhenDone(FutureTask<Result> resultFutureTask) throws InterruptedException, ExecutionException {
        new Thread(resultFutureTask).start();
        new Thread(() -> {
          while(!resultFutureTask.isDone()){
            try {
              Thread.sleep(1000);
              if(!resultFutureTask.isDone())
              System.err.print("# ");
            }catch (InterruptedException ignore){}
          }
        }).start();
        Thread.sleep(800);
        System.out.print("\nWaiting for the Result...");
        return resultFutureTask.get();
     }
    
    private static void printResult(Result result){        
    	if(result.getClass() == FileUploadResult.class)
    	{          
    		FileUploadResult uploadResult = (FileUploadResult) result;
    		if (uploadResult.getErrorCode() != 0) 
    		{
	            System.err.println(uploadResult.getShortMsg());
	            System.err.println(uploadResult.getErrorMsg());
	            System.err.println(uploadResult.getLongMsg());
    		} 
    		else {
        	  System.out.println("\nUpload successful");
        	  System.out.println("\nId of the Uploaded File:  " + uploadResult.getId());
    		}
        }    	
        else{
            if (result.getErrorCode() != 0) 
            {
              System.err.println(result.getShortMsg());
              System.err.println(result.getErrorMsg());
              System.err.println(result.getLongMsg());
            } 
            else {
              System.out.println("\nIt was a success!\n");
            }
          }
    }

}
