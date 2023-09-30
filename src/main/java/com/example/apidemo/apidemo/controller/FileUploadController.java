package com.example.apidemo.apidemo.controller;

import com.example.apidemo.apidemo.model.ResponseObject;
import com.example.apidemo.apidemo.services.UpLoadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {

    @Autowired
    private UpLoadFileService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> FileUpload(@RequestParam("file") MultipartFile file) {
        try {
            //save files to a folder => use a service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Store successful ", "ok", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ResponseObject("Store failed " + e, "false", ""));
        }

    }
}
