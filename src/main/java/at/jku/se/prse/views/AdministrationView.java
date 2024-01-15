package at.jku.se.prse.views;


import at.jku.se.prse.enums.Wiederholung;
import at.jku.se.prse.model.Fahrt;
import at.jku.se.prse.model.Fahrzeug;
import at.jku.se.prse.model.Kategorie;
import at.jku.se.prse.services.FahrtService;
import at.jku.se.prse.services.FahrzeugService;
import at.jku.se.prse.services.ImportExportService;
import at.jku.se.prse.services.KategorieService;
import com.dropbox.core.DbxException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
@View
public class AdministrationView {

    @Autowired
    FahrtService fahrtService;

    @Autowired
    KategorieService kategorieService;

    @Autowired
    ImportExportService impExpService;

    @Autowired
    FahrzeugService fahrzeugService;

    @Getter
    @Setter
    private List<Fahrt> fahrten;

    @Getter
    @Setter
    private List<Kategorie> kategorien;

    @Getter
    @Setter
    private List<Fahrzeug> fahrzeuge;

    @Getter
    @Setter
    private Fahrt newFahrt;

    @PostConstruct
    public void initAll() {
        initKategorien();
        initFahrten();
        initFahrzeuge();
    }
    public void initFahrten() {
        fahrten = fahrtService.findAll();
        newFahrt = new Fahrt();
    }
    public void initKategorien() {
        kategorien = kategorieService.findAll();
    }

    public void initFahrzeuge() {
        fahrzeuge = fahrzeugService.findAll();
    }

    public void saveNewFahrt() {
        //Issue #23
        if (newFahrt.getArrTime() != null && newFahrt.getDepTime() != null && newFahrt.getTimeStood() != null && newFahrt.getRiddenKM() != null) {
            newFahrt.setAverageSpeed(newFahrt.getRiddenKM() / aktiveFahrzeit(newFahrt));
        }
        else newFahrt.setAverageSpeed(0.0);

        if((newFahrt.getMileage() == null || newFahrt.getMileage() == 0) && newFahrt.getRiddenKM() != null && newFahrt.getRiddenKM() != 0) {
            int mileageAfterRide = newFahrt.getFahrzeug().getMileage() + newFahrt.getRiddenKM();
            newFahrt.setMileage(mileageAfterRide);
            newFahrt.getFahrzeug().setMileage(mileageAfterRide);
        }

        else if(newFahrt.getMileage() != null && newFahrt.getMileage() != 0 && (newFahrt.getRiddenKM() == null || newFahrt.getRiddenKM() == 0)) {
            int riddenKM = newFahrt.getMileage() - newFahrt.getFahrzeug().getMileage();
            newFahrt.setRiddenKM(riddenKM);
            newFahrt.getFahrzeug().setMileage(newFahrt.getMileage());
        }
        //End Issue #23

        fahrtService.save(newFahrt);

        //Issue #6
        if(newFahrt.getNumberOfRepetitions() > 1 && newFahrt.getRepetition() != Wiederholung.NICHT_DEFINIERT) {
            Fahrt fahrt = new Fahrt();
            fahrt = setAdditionalFahrt(fahrt);
            if(fahrt.getRepetition() == Wiederholung.WOECHENTLICH) repetitionWeekly(fahrt);
            else if(fahrt.getRepetition() == Wiederholung.MONATLICH) repetitionMonthly(fahrt);
            else if(fahrt.getRepetition() == Wiederholung.JAEHRLICH) repetitionYearly(fahrt);
        }
        //End Issue #6

        initFahrten();
    }

    //Issue #6
    public Fahrt setAdditionalFahrt(Fahrt fahrt){
        fahrt.setFahrzeug(newFahrt.getFahrzeug());
        fahrt.setDate(newFahrt.getDate());
        fahrt.setDepTime(newFahrt.getDepTime());
        fahrt.setArrTime(newFahrt.getArrTime());
        fahrt.setRiddenKM(newFahrt.getRiddenKM());
        fahrt.setTimeStood(newFahrt.getTimeStood());
        fahrt.setAverageSpeed(newFahrt.getAverageSpeed());
        //fahrt.setCategories(newFahrt.getCategories());
        fahrt.setRepetition(newFahrt.getRepetition());
        fahrt.setNumberOfRepetitions(newFahrt.getNumberOfRepetitions());
        return fahrt;
    }

    //Issue #6
    public void repetitionWeekly(Fahrt fahrt){
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusDays(7));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    //Issue #6
    public void repetitionMonthly(Fahrt fahrt){                 //Monthly is equivalent to 4 weeks, because otherwise it would not be the same weekday
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusWeeks(4));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    //Issue #6
    public void repetitionYearly(Fahrt fahrt){
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusYears(1));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    public void rowEditFahrt(RowEditEvent<Fahrt> event) {
        Fahrt f = event.getObject();
        if (f.getArrTime() != null && f.getDepTime() != null && f.getTimeStood() != null && f.getRiddenKM() != null) {
            f.setAverageSpeed(f.getRiddenKM() / aktiveFahrzeit(f));
        }
        else f.setAverageSpeed(0.0);

        if(f.getMileage() != f.getFahrzeug().getMileage()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tachostand unterschiedlich", "Tachostand unterscheidet sich zwischen Fahrzeug und Fahrt. Bitte überprüfen!"));
        }
        fahrtService.save(f);
        FacesMessage msg = new FacesMessage("Edited", "Fahrt " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initFahrten();
    }
    public void rowCancelFahrt(RowEditEvent<Fahrt> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Fahrt " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onRowEditKat(RowEditEvent<Kategorie> event) {
        kategorieService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Edited", "Kategorie " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initKategorien();
    }
    public void onRowCancelKat(RowEditEvent<Kategorie> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Kategorie " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void addNewKategorie() {
        Kategorie newKat = new Kategorie();
        kategorieService.save(newKat);
        initKategorien();
    }

    public void deleteKategorie(Kategorie kat) {
        if (kat.getFahrten().isEmpty()) {
            kategorieService.delete(kat);
            initKategorien();
            FacesMessage msg = new FacesMessage("Kategorie gelöscht", "Kategorie " + kat.getName() + " gelöscht.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Löschen fehlgeschlagen", "Kategorie wird verwendet.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //Issue #5
    public void deleteFahrt(Fahrt fahrt) {
        fahrtService.delete(fahrt);
        initFahrten();
    }

    //Issue #23
    public static double aktiveFahrzeit(Fahrt newFahrt){
        int arrMinutes = newFahrt.getArrTime().getHour()*60 + newFahrt.getArrTime().getMinute();
        int depMinutes = newFahrt.getDepTime().getHour()*60 + newFahrt.getDepTime().getMinute();
        int stoodMinutes = newFahrt.getTimeStood().getHour()*60 + newFahrt.getTimeStood().getMinute();
        double arrTime = (double) arrMinutes;
        double depTime = (double) depMinutes;
        double stoodTime = (double) stoodMinutes;
        double time = (arrTime - depTime - stoodTime) / 60.0;
        return time;
    }

    public static Double averageSpeed(Fahrt newFahrt){
        if (newFahrt.getArrTime() != null && newFahrt.getDepTime() != null && newFahrt.getTimeStood() != null && newFahrt.getRiddenKM() != null) {
            newFahrt.setAverageSpeed(newFahrt.getRiddenKM() / aktiveFahrzeit(newFahrt));
        }
        else newFahrt.setAverageSpeed(0.0);
        return newFahrt.getAverageSpeed();
    }

    public StreamedContent exportData() throws InvocationTargetException, IllegalAccessException {
        return impExpService.exportDataAsStreamedContent();
    }

    public void exportDataToCloud() throws IOException, DbxException {
        impExpService.exportDataToCloud();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Backup erfolgreich", "Daten wurden auf Dropbox hochgeladen"));
    }

    public void onRowEditFz(RowEditEvent<Fahrzeug> event) {
        if(fahrzeugService.findAll().stream().anyMatch(f -> f.getCarPlate().equalsIgnoreCase(event.getObject().getCarPlate()))) {
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bearbeiten fehlgeschlagen", "Fahrzeug " + event.getObject().getCarPlate() + " existiert bereits"));
        } else {
            fahrzeugService.save(event.getObject());
            FacesMessage msg = new FacesMessage("Bearbeitet", "Fahrzeug " + event.getObject().getCarPlate());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            initFahrzeuge();
        }
    }
    public void onRowCancelFz(RowEditEvent<Fahrzeug> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Bearbeiten abgebrochen", "Fahrzeug " + event.getObject().getCarPlate());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void addNewFahrzeug() {
        Fahrzeug newFz = new Fahrzeug();
        fahrzeugService.save(newFz);
        initFahrzeuge();
    }

    public void deleteFahrzeug(Fahrzeug fz) {
        if (fahrten.stream().anyMatch(f -> f.getFahrzeug() != null && f.getFahrzeug().getCarPlate() != null && f.getFahrzeug().getCarPlate().equals(fz.getCarPlate()))) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Löschen fehlgeschlagen", "Fahrzeug wird verwendet.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            fahrzeugService.delete(fz);
            initFahrzeuge();
            FacesMessage msg = new FacesMessage("Fahrzeug gelöscht", "Fahrzeug " + fz.getCarPlate() + " gelöscht.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}