package com.superior.gbm.models.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UploadFileService {

    private static String upload_folder = ".//src//main//resources//files//";

    public static void saveFile(MultipartFile file1) throws IOException {
        if(!file1.isEmpty()){
            byte[] bytes = file1.getBytes();
            Path path = Paths.get(upload_folder + file1.getOriginalFilename());
            Files.write(path,bytes);
        }
    }
    
    
    //NO USAMSK ESTE SERVICO POR QUE SE GENRA UNA COPIA DEL ARCHIVO EN EL CONTROLADOR Y TODOS LOS PARAMETROS DE REGULACIN
    public static void saveMultipleFiles(List<MultipartFile> files) throws IOException {
        for(MultipartFile file: files){
            if(file.isEmpty()) continue;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload_folder + file.getOriginalFilename());
            
           // Files.write(path,bytes);
        }
    }
}