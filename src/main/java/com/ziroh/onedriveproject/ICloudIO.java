package com.ziroh.onedriveproject;

import java.util.concurrent.FutureTask;
import com.microsoft.graph.requests.GraphServiceClient;

public interface ICloudIO {	

	FutureTask<Result> renameFile(GraphServiceClient api, String fileId, String newName);
	FutureTask<FileUploadResult> uploadFile(GraphServiceClient graphClient, String filePath, String onedrivepath);
<<<<<<< HEAD
	FutureTask<CreateDirectoryResult> createDirectory(GraphServiceClient graphClient, String directoryName, String parentId);
	FutureTask<Result> copyFile(GraphServiceClient graphClient, String fileId, String directoryId);
	FutureTask<Result> copyDirectory(GraphServiceClient graphClient, String sourceId, String destinationId);
}
=======
	FutureTask<Result> downloadFile(GraphServiceClient graphClient, String fileId);
}
>>>>>>> f4e345b8d6889663f826f9bddd948dd8a23e4342
