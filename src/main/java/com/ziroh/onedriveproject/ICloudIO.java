package com.ziroh.onedriveproject;

import java.util.concurrent.FutureTask;
import com.microsoft.graph.requests.GraphServiceClient;

public interface ICloudIO {	

	FutureTask<Result> renameFile(GraphServiceClient api, String fileId, String newName);
	FutureTask<FileUploadResult> uploadFile(GraphServiceClient graphClient, String filePath, String onedrivepath);
}