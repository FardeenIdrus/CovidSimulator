import java.io.IOException;

public class WelcomeController extends AbstractController{
    private static String filename = "welcomePane.fxml";

    public WelcomeController() throws IOException {
        super(filename);
    }
}
