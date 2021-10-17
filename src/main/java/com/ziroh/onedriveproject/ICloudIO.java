package com.ziroh.onedriveproject;

import java.util.concurrent.FutureTask;
import com.microsoft.graph.requests.GraphServiceClient;

public interface ICloudIO {	

	FutureTask<Result> renameFile(GraphServiceClient api, String fileId, String newName);
	FutureTask<FileUploadResult> uploadFile(GraphServiceClient graphClient, String filePath, String onedrivepath);
	FutureTask<Result> shareFile(GraphServiceClient graphClient, String fileId);
	FutureTask<Result> aboutInformation(GraphServiceClient graphClient);
	FutureTask<Result> updateFile(GraphServiceClient graphClient, String fileId, String filePath);
	FutureTask<Result> downloadFile(GraphServiceClient graphClient, String fileId, String filePath);
	FutureTask<Result> copyFile(GraphServiceClient graphClient, String fileId, String directoryId,String fileName);
	FutureTask<Result> deleteFile(GraphServiceClient graphClient, String fileId);
	FutureTask<Result> deleteDirectory(GraphServiceClient graphClient, String directoryId);
	FutureTask<Result> copyDirectory(GraphServiceClient graphClient, String sourceId, String destinationId, String fileName);
	FutureTask<CreateDirectoryResult> createDirectory(GraphServiceClient graphClient, String directoryName,
			String parentId);
}
