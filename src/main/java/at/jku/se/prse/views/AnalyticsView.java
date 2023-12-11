package at.jku.se.prse.views;

import at.jku.se.prse.model.Fahrt;
import at.jku.se.prse.model.Kategorie;
import at.jku.se.prse.services.FahrtService;
import at.jku.se.prse.services.KategorieService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.dashboard.DashboardModel;
import org.primefaces.model.dashboard.DefaultDashboardModel;
import org.primefaces.model.dashboard.DefaultDashboardWidget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Component
@View
public class AnalyticsView {

    @Autowired
    FahrtService fahrtService;
    List<Fahrt> fahrten;

    @Autowired
    KategorieService katService;

    @Getter
    @Setter
    List<Kategorie> kategorien;

    @Getter
    LineChartModel lineModel;

    @Getter
    @Setter
    LocalDate end;

    @Getter
    @Setter
    LocalDate beginning;

    @Getter
    @Setter
    String choice;

    @Getter
    @Setter
    Set<Kategorie> choiceKats;

    private static final String RESPONSIVE_CLASS = "col-12 lg:col-6 xl:col-6";

    @Getter
    private DashboardModel responsiveModel;

    @PostConstruct
    public void init() {
        initKategorien();
        initSelections();
        initFahrten();
        initChart();
        responsiveModel = new DefaultDashboardModel();
        responsiveModel.addWidget(new DefaultDashboardWidget("kmPerYear", RESPONSIVE_CLASS));
    }

    private void initSelections() {
        beginning = LocalDate.now().minusMonths(6);
        end = LocalDate.now().plusMonths(6);
        choice = "Monat";
        choiceKats = new HashSet<>();
    }

    public void reload() {
        initFahrten();
        initKategorien();
        initChart();

        addMessage(new FacesMessage("Grafik neu geladen"));
    }

    private void initFahrten() {
        fahrten = fahrtService.findAll();
    }

    private void initKategorien() {
        kategorien = katService.findAll();
    }
    public void initChart() {
        lineModel = new LineChartModel();
        lineModel.setOptions(initOptions());
        lineModel.setData(initData());
    }

    private LineChartOptions initOptions() {

        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);

        //Title
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Kilometer pro " + choice);
        options.setTitle(title);

        //Subtitle
        Title subtitle = new Title();
        subtitle.setDisplay(true);
        if(choice.equals("Monat")) {
            subtitle.setText(
                    beginning.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)
                            + " " + beginning.getYear() + " bis " +
                            end.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)
                            + " " + end.getYear()
            );
        }
        else if(choice.equals("Jahr")) {
            subtitle.setText(beginning.getYear() + " bis " + end.getYear());
        }
        else throw new IllegalStateException("Choice not known - neitehr Monat nor Jahr");

        options.setSubtitle(subtitle);

        return options;
    }

    private ChartData initData() {
        //Data
        LineChartDataSet dataSet = new LineChartDataSet();

        List<String> labels = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        LocalDate start = beginning;
        if(choice.equals("Monat")) {
            while (!start.isAfter(end)) {
                Month month = start.getMonth();
                labels.add(start.getYear() + " " + month.getDisplayName(TextStyle.FULL, Locale.GERMAN));
                values.add(
                        fahrten.stream()
                                .filter(f -> f.getDate().getMonth().compareTo(month) == 0)
                                .filter(f ->  {
                                    if(choiceKats.isEmpty()) return true;
                                    return true;
                                })
                                .filter(f -> {
                                    for (Kategorie k : choiceKats) {
                                        if(f.getCategories().contains(k)) return true;
                                    }
                                    return false;
                                })
                                .mapToInt(Fahrt::getRiddenKM).sum()
                );
                start = start.plusMonths(1);
            }
        }

        else if(choice.equals("Jahr")) {
            while (start.getYear() <= end.getYear()) {
                int year = start.getYear();
                labels.add(String.valueOf(year));
                values.add(
                        fahrten.stream()
                                .filter(f -> f.getDate().getYear() == year)
                                .filter(f -> {
                                    for (Kategorie k : choiceKats) {
                                        if(f.getCategories().contains(k)) return true;
                                    }
                                    return false;
                                })
                                .mapToInt(Fahrt::getRiddenKM).sum()
                );
                start = start.plusYears(1);
            }
        }

        else throw new IllegalStateException("Choice not known - neitehr Monat nor Jahr");

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Kilometer");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);

        ChartData data = new ChartData();
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        return data;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
