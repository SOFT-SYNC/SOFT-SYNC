//package com.softsync.zerock.controller;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Optional;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping("/images")
//public class ImageController {
//
//    @GetMapping("/{filename:.+}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
//        try {
//            Path file = Paths.get("/home/mit305/back/softsync/image/").resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                // MIME 타입 설정
//                String contentType = Optional.ofNullable(request.getServletContext().getMimeType(resource.getFile().getAbsolutePath()))
//                                             .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//
//                return ResponseEntity.ok()
//                        .contentType(MediaType.parseMediaType(contentType))
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
