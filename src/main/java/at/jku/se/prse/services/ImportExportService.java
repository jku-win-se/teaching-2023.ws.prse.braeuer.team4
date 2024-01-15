package at.jku.se.prse.services;

import org.primefaces.model.StreamedContent;

import java.lang.reflect.InvocationTargetException;

public interface ImportExportService {

    StreamedContent exportDataAsStreamedContent() throws InvocationTargetException, IllegalAccessException;
}