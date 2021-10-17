package com.ziroh.onedriveproject;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;

import java.util.Scanner;
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
//    	uploadFile();
<<<<<<< HEAD
//    	createDirectory();
//   	copyFile();
		copyDirectory();    	
=======
    	downloadFile();
>>>>>>> f4e345b8d6889663f826f9bddd948dd8a23e4342
    	
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
		Result downloadresult = getResultWhenDone(onedrive.downloadFile(graphClient, fileId));
		printResult(downloadresult);
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
    
    private static void createDirectory() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the name of the directory to be created: ");
    	String directoryName = sc.nextLine();
    	System.out.print("Enter the id of the parent folder(Enter 'root' for the root folder): ");
    	String parentId = sc.nextLine();
     	CreateDirectoryResult result = getResultWhenDone(onedrive.createDirectory(graphClient, directoryName, parentId), 0);
    	printResult(result);
    }
    
    private static void copyFile() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the id of the file to be moved: ");
    	String fileId = sc.nextLine();
    	System.out.print("Enter the id of the directory to which the file is to be moved: ");
    	String directoryId = sc.nextLine();
		Result result = getResultWhenDone(onedrive.copyFile(graphClient, fileId, directoryId));
    	printResult(result);
    }
    private static void copyDirectory() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the id of the directory to be copied: ");
    	String sourceId = sc.nextLine();
    	System.out.print("Enter the id of the directory to which this directory is to be copied: ");
    	String destinationId = sc.nextLine();
		Result result = getResultWhenDone(onedrive.copyDirectory(graphClient, sourceId, destinationId));
    	printResult(result);
    }
    
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
    private static CreateDirectoryResult getResultWhenDone(FutureTask<CreateDirectoryResult> createDirectoryFutureTask, int a) throws InterruptedException, ExecutionException {
        new Thread(createDirectoryFutureTask).start();
        new Thread(() -> {
          while(!createDirectoryFutureTask.isDone()){
            try {
              Thread.sleep(1000);
              if(!createDirectoryFutureTask.isDone())
              System.err.print("# ");
            }catch (InterruptedException ignore){}
          }
        }).start();
        Thread.sleep(500);
        System.out.print("\nProcessing and waiting for the result...");
        return createDirectoryFutureTask.get();
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
