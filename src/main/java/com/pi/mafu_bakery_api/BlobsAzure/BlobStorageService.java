package com.pi.mafu_bakery_api.BlobsAzure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;

@Service
public class BlobStorageService {
    @Value("${azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${azure.storage.blob.container-name}")
    private String containerName;

    public String uploadImage(MultipartFile file) throws Exception {
        String blobName = file.getOriginalFilename();
        BlobClient blobClient = new BlobClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .blobName(blobName)
                .buildClient();

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();  // Retorna a URL da imagem no Blob Storage
    }
}
