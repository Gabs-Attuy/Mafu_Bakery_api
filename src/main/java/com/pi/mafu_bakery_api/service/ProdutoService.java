package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.BlobsAzure.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {
    private final BlobStorageService blobStorageService;

    public ProdutoService(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }

    public String uploadImage(MultipartFile image) throws Exception {
        // Upload da imagem para o Azure Blob Storage
        return blobStorageService.uploadImage(image);
    }
}
