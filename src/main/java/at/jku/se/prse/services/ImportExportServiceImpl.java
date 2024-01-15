package at.jku.se.prse.services;

import at.jku.se.prse.model.Fahrt;
import at.jku.se.prse.model.Fahrzeug;
import at.jku.se.prse.model.Kategorie;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ImportExportServiceImpl implements ImportExportService{

    private static final Logger logger = LoggerFactory.getLogger(ImportExportServiceImpl.class);

    @Autowired
    FahrtService fahrtService;

    @Autowired
    KategorieService katService;

    @Autowired
    FahrzeugService fahrzeugService;

    @Autowired
    DbxClientV2 dropboxClient;

    Map<String, Method> mapFahrt = new LinkedHashMap(){{
        for(Field field : Fahrt.class.getDeclaredFields())
            for(Method method : Fahrt.class.getDeclaredMethods())
                if(method.getName().equalsIgnoreCase("get" + field.getName())) put(field.getName(), method);
    }};

    Map<String, Method> mapKategorie = new LinkedHashMap(){{
        for(Field field : Kategorie.class.getDeclaredFields())
            for(Method method : Kategorie.class.getDeclaredMethods())
                if(method.getName().equalsIgnoreCase("get" + field.getName())) put(field.getName(), method);
    }};

    Map<String, Method> mapFahrzeug = new LinkedHashMap<>(){{
        for(Field field : Fahrzeug.class.getDeclaredFields())
            for(Method method : Fahrzeug.class.getDeclaredMethods())
                if(method.getName().equalsIgnoreCase("get" + field.getName())) put(field.getName(), method);
    }};

    Map<String, Map<String, Method>> mapOfMaps =  new HashMap<>(){{
        put("Fahrten", mapFahrt);
        put("Kategorien", mapKategorie);
        put("Fahrzeuge", mapFahrzeug);
    }};

    @Override
    public void exportDataToCloud() throws IOException, DbxException {
        uploadFile(createBosFromData(), "/FahrtenbuchApp/output_" + LocalDateTime.now() + ".xlsx");
    }

    @Override
    public StreamedContent exportDataAsStreamedContent() {
        ByteArrayOutputStream bos = createBosFromData();

        return DefaultStreamedContent.builder()
                .stream(() -> new ByteArrayInputStream(bos.toByteArray()))
                .contentEncoding("application/xlsx")
                .name("output.xlsx")
                .build();
    }

    private ByteArrayOutputStream createBosFromData(){
        XSSFWorkbook wb = new XSSFWorkbook();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        for(Map.Entry<String, Map<String, Method>> entry : mapOfMaps.entrySet()) {
            int rowNum = 0; int colIndex = 0;
            XSSFSheet sheet = wb.createSheet(entry.getKey());
            if(entry.getKey().equals("Fahrten")) {
                XSSFRow header = sheet.createRow(rowNum++);
                for(String s : entry.getValue().keySet()){
                    header.createCell(colIndex++).setCellValue(s);
                }
                for (Fahrt fahrt : fahrtService.findAll()) {
                    XSSFRow row = sheet.createRow(rowNum++);
                    colIndex = 0;

                    for (Method m : entry.getValue().values()) {
                        String val = "";
                        try {
                            if (m.invoke(fahrt, null) != null) val = m.invoke(fahrt, null).toString();
                        } catch (Exception e) {
                            throw new RuntimeException("Something went wrong - please try again or contact our Tech-Support");
                        }
                        row.createCell(colIndex++).setCellValue(val);
                    }
                }
            }
            else if(entry.getKey().equals("Kategorien")) {
                XSSFRow header = sheet.createRow(rowNum++);
                for(String s : entry.getValue().keySet()) {
                    header.createCell(colIndex++).setCellValue(s);
                }
                for (Kategorie kat : katService.findAll()) {
                    XSSFRow row = sheet.createRow(rowNum++);
                    colIndex = 0;

                    for (Method m : entry.getValue().values()) {
                        String val = "";
                        try {
                            if (m.invoke(kat, null) != null) val = m.invoke(kat, null).toString();
                        } catch (Exception e) {
                            throw new RuntimeException("Something went wrong - please try again or contact our Tech-Support");
                        }
                        row.createCell(colIndex++).setCellValue(val);
                    }
                }
            }
            else if(entry.getKey().equals("Fahrzeuge")) {
                XSSFRow header = sheet.createRow(rowNum++);
                for(String s : entry.getValue().keySet()) {
                    header.createCell(colIndex++).setCellValue(s);
                }
                for (Fahrzeug fz : fahrzeugService.findAll()) {
                    XSSFRow row = sheet.createRow(rowNum++);
                    colIndex = 0;

                    for (Method m : entry.getValue().values()) {
                        String val = "";
                        try {
                            if (m.invoke(fz, null) != null) val = m.invoke(fz, null).toString();
                        } catch (Exception e) {
                            throw new RuntimeException("Something went wrong - please try again or contact our Tech-Support");
                        }
                        row.createCell(colIndex++).setCellValue(val);
                    }
                }
            }
        }

        try {
            wb.write(bos);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong - please try again or contact our Tech-Support");
        }
        return bos;
    }

    private void uploadFile(ByteArrayOutputStream bao, String filePath) throws IOException, DbxException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bao.toByteArray());
        Metadata uploadMetaData = dropboxClient.files().uploadBuilder(filePath).uploadAndFinish(inputStream);
        logger.info("upload meta data =====> {}", uploadMetaData.toString());
        inputStream.close();
    }
}