package ait.cohort63.shop.service;

import ait.cohort63.shop.service.interfaces.FileService;
import ait.cohort63.shop.service.interfaces.ProductService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 client;
    private final ProductService productService;

    private static final String BUCKET_NAME= "cohort-63-bucket";

    public FileServiceImpl(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }


    @Override
    public String upload(MultipartFile file, String productTitle) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentType(file.getContentType());

            String uniqueFileName= generateUniqueFileName(file);

            // Objekt get zaprosa zagruzki faila
            PutObjectRequest request = new PutObjectRequest(
                    BUCKET_NAME,
                    uniqueFileName,
                    file.getInputStream(),
                    metadata
            );

            request.withCannedAcl(CannedAccessControlList.PublicRead);

            //prozess zagruzki faila v oblako
            client.putObject(request);

            String url = client.getUrl(BUCKET_NAME, uniqueFileName).toString();

            // Priveazati ssilku k produktu
            productService.attachImage(url, productTitle);

            return url;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {

    String sourceFileName = file.getOriginalFilename();
    int lastDotIndex = sourceFileName.lastIndexOf('.');
    String fileName = sourceFileName.substring(0, lastDotIndex);
    String extension = fileName.substring(lastDotIndex); // ".jpeg"

        return String.format("%s-%s%s", fileName, UUID.randomUUID().toString(),extension);

    }

}
















