package com.ziroh.onedriveproject;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCopyParameterSet;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.FileSystemInfo;
import com.microsoft.graph.models.ItemReference;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private static Path path = null;
	//Methods	  
    public static void main( String[] args ) throws ExecutionException, InterruptedException, IOException
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
   	
//     	renameFile();
//    	uploadFile();
//    	shareFile();
//    	aboutInformation();
//    	downloadFile();
//    	copyFile();
//      deleteFile();   
//    	deleteDirectory();
//    	updateFile();
    	DriveItemCollectionPage children = graphClient.me().drive().root().children()
    			.buildRequest()
    			.get();
     for(int i=0; i<children.getCount()-1;i++)
     {
    	 System.out.println(children.getCurrentPage().get(i).name+"  "+children.getCurrentPage().get(i).id);
     }
    	//copyDirectory();
     
    } 	
    private static void uploadFile() throws ExecutionException, InterruptedException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the path of the file to be uploaded: ");
		String filePath = sc.nextLine();
		char ch;
		String extention ="";
		for(int i=filePath.length()-1;i>=0;i--)
		{
			ch = filePath.charAt(i);
			if(ch=='.')
				{extention = filePath.substring(i);
			    break;}
		}
		
		System.out.print("Enter the path of the parent folder in onedrive: ");
		String onedrivepath = sc.nextLine();
		System.out.println("Enter the filename: ");
		String fileName = sc.nextLine();
		onedrivepath = onedrivepath + "/" + fileName + extention;
		System.out.println("inside upload func: "+onedrive);
		FileUploadResult result = getResultWhenDone(onedrive.uploadFile(graphClient, filePath, onedrivepath), NULL);
		printResult(result);
    }
	
        private static void downloadFile() throws ExecutionException, InterruptedException 
     {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter id of file to be downloaded: ");
		String fileId = sc.nextLine();
		System.out.print("Enter the path to download the file: ");
		String path = sc.nextLine();
		System.out.println("Starting download");
		Result result = getResultWhenDone(onedrive.downloadFile(graphClient, fileId,path));
		printResult(result);
    }
	
        private static void deleteFile() throws ExecutionException, InterruptedException {
        	@SuppressWarnings("resource")
    		Scanner sc = new Scanner(System.in);
    		System.out.print("Enter the id of file to be deleted: ");
    		String fileId = sc.nextLine();
    		Result result = getResultWhenDone(onedrive.deleteFile(graphClient, fileId));
    		printResult(result);
        }
        
        private static void shareFile() throws ExecutionException, InterruptedException {
        	@SuppressWarnings("resource")
    		Scanner sc = new Scanner(System.in);
    		System.out.print("Enter id of file to be shared: ");
    		String fileId = sc.nextLine();
    		Result result = getResultWhenDone(onedrive.shareFile(graphClient,fileId));
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
    
    private static void updateFile() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter id of file to be updated: ");
		String fileId = sc.nextLine();
		System.out.print("Path of the file to update the id entered: ");
		String filePath = sc.nextLine();
		Result result = getResultWhenDone(onedrive.updateFile(graphClient, fileId, filePath));
		printResult(result);
}
    
    private static void copyFile() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the id of the file to be moved: ");
    	String fileId = sc.nextLine();
    	System.out.print("Enter the id of the directory to which the file is to be moved: ");
    	String directoryId = sc.nextLine();
    	System.out.print("Enter the new name of the file with extention: ");
    	String fileName = sc.nextLine();
		Result result = getResultWhenDone(onedrive.copyFile(graphClient, fileId, directoryId,fileName));
    	printResult(result);
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
    
    private static void deleteDirectory() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the id of the directory to be deleted: ");
    	String directoryId = sc.nextLine();
		Result result = getResultWhenDone(onedrive.deleteDirectory(graphClient, directoryId));
    	printResult(result);
    }
    
    private static void copyDirectory() throws ExecutionException, InterruptedException {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter the id of the directory to be copied: ");
    	String sourceId = sc.nextLine();
    	System.out.print("Enter the id of the directory to which this directory is to be copied: ");
    	String destinationId = sc.nextLine();
    	System.out.println("Enter the name of the new directory");
    	String DirectoryName = sc.nextLine();
		Result result = getResultWhenDone(onedrive.copyDirectory(graphClient, sourceId, destinationId,DirectoryName));
    	printResult(result);
    }
    
    private static void aboutInformation() throws ExecutionException, InterruptedException {
    	Result result = getResultWhenDone(onedrive.aboutInformation(graphClient));
        printResult(result);
      }
    
    private static FileUploadResult getResultWhenDone(final FutureTask<FileUploadResult> uploadResultFutureTask, String NULL) throws InterruptedException, ExecutionException {
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
