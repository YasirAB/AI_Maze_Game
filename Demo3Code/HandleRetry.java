import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

public class HandleRetry implements EventHandler<ActionEvent> {
    LogicRunner myLogic;
    Stage primaryStage;
    Stage myStage;

    public HandleRetry(LogicRunner logic, Stage stage, Stage primaryStage){
      this.myLogic = logic;
      this.myStage = stage;
      this.primaryStage = primaryStage;
    }

    public void handle(ActionEvent event){
      myStage.close();
      Display newDisplay = new Display();
      newDisplay.start(primaryStage);
    }
}
