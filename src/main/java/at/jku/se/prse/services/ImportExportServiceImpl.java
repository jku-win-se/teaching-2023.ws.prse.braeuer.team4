package at.jku.se.prse.services;

import at.jku.se.prse.customadapters.LocalDateTypeAdapter;
import at.jku.se.prse.customadapters.LocalTimeTypeAdapter;
import at.jku.se.prse.model.Fahrt;
import at.jku.se.prse.model.Fahrzeug;
import at.jku.se.prse.model.Kategorie;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    public void exportDataToCloudXLSX() throws IOException, DbxException {
        uploadFile(createBosFromDataXLSX(), "/FahrtenbuchApp/output_" + LocalDateTime.now() + ".xlsx");
    }

    @Override
    public void exportDataToCloudJSON() throws IOException, DbxException {
        uploadFile(createBosFromDataJSON(), "/FahrtenbuchApp/output_" + LocalDateTime.now() + ".json");
    }

    @Override
    public StreamedContent exportDataAsStreamedContentXLSX() {
        ByteArrayOutputStream bos = createBosFromDataXLSX();

        return DefaultStreamedContent.builder()
                .stream(() -> new ByteArrayInputStream(bos.toByteArray()))
                .contentEncoding("application/xlsx")
                .name("output.xlsx")
                .build();
    }

    @Override
    public StreamedContent exportDataAsStreamedContentJSON() throws IOException {
        ByteArrayOutputStream bos = createBosFromDataJSON();

        return DefaultStreamedContent.builder()
                .stream(() -> {
                    assert bos != null;
                    return new ByteArrayInputStream(bos.toByteArray());
                })
                .contentEncoding(String.valueOf(MediaType.APPLICATION_JSON))
                .name("output.json")
                .build();
    }

    @Override
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(event.getFile().getInputStream()));
        Type listOfMyClassObject = new TypeToken<ArrayList<Fahrt>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .serializeNulls()
                .setPrettyPrinting().create();

        List<Fahrt> outputList = gson.fromJson(reader, listOfMyClassObject);

        for(Fahrt f : outputList) {
            //Set Fahrzeug correctly
            if(!fahrzeugService.findAll().stream().anyMatch(fz -> fz.getCarPlate().equalsIgnoreCase(f.getFahrzeug().getCarPlate()))) f.setFahrzeug(fahrzeugService.save(f.getFahrzeug()));
            else f.setFahrzeug(fahrzeugService.findByCarPlate(f.getFahrzeug().getCarPlate()));

            //Set Cats correctly
            Set<Kategorie> cats = new HashSet<>();
            for(Kategorie k : f.getCategories()) {
                if(!katService.findAll().stream().anyMatch(kat -> kat.getName().equalsIgnoreCase(k.getName()))) cats.add(katService.save(k));
                else cats.add(katService.findByName(k.getName()));
            }

            f.setCategories(cats);

            //save Fahrt - contains is not 100% correct yet
            if(!fahrtService.findAll().contains(f)) {
                fahrtService.save(f);
            }
        }
    }

    private ByteArrayOutputStream createBosFromDataJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .serializeNulls()
                .setPrettyPrinting().create();
        gson.toJson(fahrtService.findAll(), List.class, writer);

        writer.flush();
        writer.close();
        return out;
    }

    private ByteArrayOutputStream createBosFromDataXLSX(){
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