package com.gymstarter.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\LabDefender\\GymStarter\\Admin\\src\\main\\resources\\static\\img\\image-workout";

    public boolean uploadImage(MultipartFile imageWorkout) {
        boolean isUpload = false;
        try {
            Files.copy(imageWorkout.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator, imageWorkout.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile imageWorkout) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + "\\" + imageWorkout.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
}