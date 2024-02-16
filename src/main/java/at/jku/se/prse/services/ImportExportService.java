package at.jku.se.prse.services;

import com.dropbox.core.DbxException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface ImportExportService {

    void exportDataToCloudXLSX() throws IOException, DbxException;
    void exportDataToCloudJSON() throws IOException, DbxException;
    StreamedContent exportDataAsStreamedContentXLSX() throws InvocationTargetException, IllegalAccessException;
    StreamedContent exportDataAsStreamedContentJSON() throws IOException;
    void handleFileUpload(FileUploadEvent event) throws IOException;
}