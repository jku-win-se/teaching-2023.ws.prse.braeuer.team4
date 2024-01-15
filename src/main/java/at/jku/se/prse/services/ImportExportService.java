package at.jku.se.prse.services;

import com.dropbox.core.DbxException;
import org.primefaces.model.StreamedContent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface ImportExportService {

    void exportDataToCloud() throws IOException, DbxException;
    StreamedContent exportDataAsStreamedContent() throws InvocationTargetException, IllegalAccessException;
}