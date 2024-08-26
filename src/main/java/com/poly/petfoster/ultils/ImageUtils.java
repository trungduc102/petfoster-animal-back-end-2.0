package com.poly.petfoster.ultils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.ultils.partent.OptionsCreateAndSaveFile;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] tmp = new byte[4 * 1024];

        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);

        }

        try {
            outputStream.close();
        } catch (Exception e) {
            System.out.println("in images compressImage untils" + e.getMessage());
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();

        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int conut = inflater.inflate(tmp);
                outputStream.write(tmp, 0, conut);
            }
            outputStream.close();
        } catch (Exception e) {
            System.out.println("in images decompressImage untils" + e.getMessage());
        }

        return outputStream.toByteArray();
    }

    public static File createFileImage() {
        UUID uuid = UUID.randomUUID();
        return new File("images\\" + uuid.toString() + ".jpg");
    }

    public static File createFileImage(String path) {
        UUID uuid = UUID.randomUUID();
        return new File("images\\" + path + uuid.toString() + ".jpg");
    }

    public static File createFile(String path, String contentType) {
        UUID uuid = UUID.randomUUID();
        return new File("images\\" + path + uuid.toString() + "." + contentType);
    }

    public static String getExtentionFile(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');

        return fileName.substring(index + 1);
    }

    public static String getExtentionFile(MultipartFile file) {
        String fileName = file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename();

        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            return fileName.substring(index + 1);

        }
        throw new Error("Something wen't wrong when get name of file");

    }

    public static File createFileAndSave(String path, MultipartFile originalFile,
            OptionsCreateAndSaveFile optionsCreateAndSaveFile) {

        try {
            // create a dynamic name for file
            UUID uuid = UUID.randomUUID();

            // handle options of funtion
            OptionsCreateAndSaveFile unWrapOptionsCreateAndSaveFile = optionsCreateAndSaveFile == null
                    ? new OptionsCreateAndSaveFile()
                    : optionsCreateAndSaveFile;

            // get extention from option
            String extension = unWrapOptionsCreateAndSaveFile.getDefaultExtention();

            // handle original file of funtion

            // check extention from original file could accept by options of user
            if (originalFile != null) {
                String nonValidateExtention = getExtentionFile(originalFile);

                if (ListUltils.includes(unWrapOptionsCreateAndSaveFile.getAcceptExtentions(), nonValidateExtention)) {
                    extension = nonValidateExtention;
                }

            }

            // all good
            File resultFile = new File("images\\" + path + uuid.toString() + "." +
                    extension);

            if (originalFile != null) {
                originalFile.transferTo(new File(resultFile.getAbsolutePath()));
            }

            return resultFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static void deleteImg(String fileName) {
        File imgFile = new File("images/" + fileName);
        if (imgFile.exists())
            imgFile.delete();
    }

    // public static String getURLImage(String nameImage, TypeFileImage
    // typeFileImage){
    // HttpServletRequest request =
    // ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
    // .getRequest();

    // String domain = request.getHeader("Host").contains("http://") ?
    // request.getHeader("Host") : "http://" + request.getHeader("Host");
    // String prefixImageUrl = domain + "/images/" + typeFileImage.value() + "/";

    // return prefixImageUrl + nameImage;
    // }
}
