package com.pado.c3editions.app.editions.prospect.helper;

import com.pado.c3editions.app.editions.prospect.exception.FileException;
import com.pado.c3editions.app.editions.prospect.pojos.Contact;
import jakarta.servlet.ServletOutputStream;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
public class FileUploadHelper {

    String uploadDir;

    public FileUploadHelper(Environment env) {
        super();
        this.uploadDir = env.getProperty("files.uploads");
    }

    public boolean saveImage(String tosave,String fileName,
                            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir,tosave);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String extension= StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        if(!List.of("image/jpeg","image/png","image/gif").contains(multipartFile.getContentType())){
            throw new FileException("Le fichier doit etre de type image",HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName+"."+extension);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileException("Could not save image file: " + fileName, ioe, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
//
//    public File get(Versions resource, Projets projet){
//        Path uploadPath = Paths.get(uploadDir,"projets",projet.getDossier(),resource.getResource().getName(),resource.getNumero()+"");
//        if (!Files.exists(uploadPath)) {
//
//        }
//        return null;
//    }

    public boolean downloadFile(InputStream in, ServletOutputStream out) throws IOException {

        byte[] buffer=new byte[8192];
//        in.
        int bytesRead=-1;
        while((bytesRead= in.read(buffer))!=-1){
            out.write(buffer,0,bytesRead);
        }
        in.close();
        out.close();
        return true;
    }

    public Path saveResourceFile(Contact contact, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir, contact.getPrenom());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (IOException ioe) {
            throw new IOException("Could not save the file: " + fileName, ioe);
        }
    }
//
//    public Resources renomeResource(Resources resource, String name, Projets projet) throws IOException {
//        Set<Versions> versions=resource.getVersions();
//        for (Versions version:versions) {
//            Path target=Paths.get(uploadDir,"projets",projet.getCode(),
//                    resource.getName(),version.getNumero()+"", name+"."+version.getFormat());
//            File source=new File("/"+version.getPath());
//            if(!source.renameTo(target.toFile())) {
//                System.out.println("not rename");
//                return null;
//            }
//            version.setPath(Paths.get(uploadDir,"projets",projet.getCode(),
//                    name,version.getNumero()+"", name+"."+version.getFormat()).toString());
//        }
//        Path target=Paths.get(uploadDir,"projets",projet.getCode(),name);
//        Path src=Paths.get(uploadDir,"projets",projet.getCode(),resource.getName());
//
//        if(!src.toFile().renameTo(target.toFile()))
//            return null;
//        resource.setName(name);
//
//        return resource;
//    }


    public boolean removefile(String path){
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
