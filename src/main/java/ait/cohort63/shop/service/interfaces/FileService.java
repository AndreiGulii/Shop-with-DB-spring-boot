package ait.cohort63.shop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file, String productFile);
}
