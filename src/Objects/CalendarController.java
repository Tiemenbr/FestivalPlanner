package Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    static List<ScheduleItem> calendarActivities = null;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 2;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // List of activities for a given month
        Map<Integer, List<ScheduleItem>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        // Drawing agenda
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<ScheduleItem> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<ScheduleItem> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            // Amount of activities per day
            if(k >= 4) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // On ... click print all activities for that day
                    System.out.println("All Activities: ");
                    for (ScheduleItem calendarActivity : calendarActivities){
                        System.out.println(calendarActivity.toString());
                    }
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getAttraction() + ", " + calendarActivities.get(k).getStartTime().toLocalTime()); //.getDate().toLocalTime());
            // Cut off the text if the activity name is too long
            if (calendarActivities.get(k).getAttraction().toString().length() > 15){ //.getClientName().length() > 15){
                text = new Text(calendarActivities.get(k).getAttraction().toString().substring(0, 15).trim() + "..., " + calendarActivities.get(k).getStartTime());//.getClientName().substring(0, 15).trim() + "..., " + calendarActivities.get(k).getDate().toLocalTime());
            }
            calendarActivityBox.getChildren().add(text);

            Text finalText = text;
            text.setOnMouseClicked(mouseEvent -> {
                // On text clicked
                System.out.println(finalText.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);

        calendarActivityBox.setMaxWidth(rectangleWidth * 0.98);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.76);
        // Background property (optional)
        calendarActivityBox.setStyle("-fx-background-color:LIGHTGRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<ScheduleItem>> createCalendarMap(List<ScheduleItem> calendarActivities) {
        Map<Integer, List<ScheduleItem>> calendarActivityMap = new HashMap<>();

        for (ScheduleItem activity: calendarActivities) {
            int activityDate = activity.getStartTime().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, calendarActivities); //List.of(activity)
            } else {
                List<ScheduleItem> OldListByDate = calendarActivityMap.get(activityDate);

                List<ScheduleItem> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<ScheduleItem>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        ArrayList<ScheduleItem> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        for (ScheduleItem calendarActivity : this.calendarActivities){
            ZonedDateTime time = ZonedDateTime.of(calendarActivity.getStartTime().getYear(), calendarActivity.getStartTime().getMonth().getValue(), calendarActivity.getStartTime().getDayOfMonth(), calendarActivity.getStartTime().getHour(), calendarActivity.getStartTime().getMinute(), 0, 0, dateFocus.getZone());
            if (time.getYear() == year && time.getMonth().getValue() == month){
                calendarActivities.add(new ScheduleItem(calendarActivity.getId(), calendarActivity.getLocation(), calendarActivity.getAttraction(), calendarActivity.getStartTime(), calendarActivity.getEndTime()));
            }
        }
        return createCalendarMap(calendarActivities);
    }

    // Self-made methods: Adding an activity to the calendar
    public Map<Integer, List<ScheduleItem>> addCalendarActivity(int idNumber, Location location, Attraction attraction, ZonedDateTime startTime, ZonedDateTime endTime){
        try{
            this.calendarActivities.isEmpty();
        } catch (NullPointerException e){
            this.calendarActivities = new ArrayList<>();
        } finally {
            this.calendarActivities.add(new ScheduleItem(idNumber, location, attraction, startTime, endTime));
        }

        return createCalendarMap(calendarActivities);
    }
}
