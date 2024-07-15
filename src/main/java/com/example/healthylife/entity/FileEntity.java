package com.example.healthylife.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@ToString
@Entity
@Getter
@Table(name = "todo_exercise")
@NoArgsConstructor

public class FileEntity implements Serializable  {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "fileId_sq", unique = true,nullable = false)
        //파일 아이디
        private long fileIdSq;

        //원본파일이름
        @Column(name = "origin_file_name",nullable = false,length = 100)
        private String originFileNmae;

        //오운완 게시물 내용
        @Column(name = "file_name",length = 500)
        private String fileName;

    //오운완 게시물 내용
    @Column(name = "file_path",length = 500)
    private String filePath;

       //builder
        @Builder(toBuilder = true)
        public FileEntity(long fileIdSq, String originFileNmae, String fileName,
                          String filePath){
            this.fileIdSq = fileIdSq;
            this.originFileNmae = originFileNmae;
            this.fileName = fileName;
            this.filePath = filePath;
        }
    }


