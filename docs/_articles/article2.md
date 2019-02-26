---
# Specify the layout for this section
include: sections/article.html
---

### Execution:

1.	I started the execution phase with creating FXML code for all the GUI using Scene Builder.<br>

    > ***Challenge:***<br>
        How to design an algorithm to display appointments in a calendar by month?<br><br>
    > ***Solution:***
    * Use VBox anchored to an AnchorPane added to each grid inside the GridPane to properly align ComboBox used for appointment lists and label used for displaying each date in the selected month.
    * Download all appointments from the database and map them by months then by dates in a month (2-layer map). Iterate over the map by dates to add lists of appointments to ComboBoxes.

    ---

    > ***Challenge:***<br>
        How to design an algorithm to display appointments in a calendar by week?<br><br>
    > ***Solution:***
    * Use VBox anchored to an AnchorPane added to each grid inside the GridPane to properly align ComboBox used for appointment lists and label used for displaying each date in the selected month.
    * Download all appointments from the database and map them by months then by dates in a month (2-layer map). Iterate over the map by dates to add lists of appointments to ComboBoxes.

    <!-- ```js
    // Pseudocode
    Calendar view by month
        Display appointments in a calendar viewed by month
    Input: integers month, year

    for i = 0 to totalDatesInMonth
        add VBox to grid inside GridPane
        add Label with dateInMonth[i] to 
    ``` -->

