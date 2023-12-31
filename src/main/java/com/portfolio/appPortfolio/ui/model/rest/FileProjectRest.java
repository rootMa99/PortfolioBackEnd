package com.portfolio.appPortfolio.ui.model.rest;

import javax.persistence.Lob;

public class FileProjectRest {

    private String fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    @Lob
    private byte[] data;


    public FileProjectRest(String fileID,String fileName, String fileDownloadUri, String fileType, long size,
                           byte[] data) {
        this.fileId =fileID;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.data = data;
    }

    public FileProjectRest() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
