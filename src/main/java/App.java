// https://firebase.google.com/docs/database/admin/retrieve-data
// https://stackoverflow.com/questions/35454652/how-to-bypass-the-firebase-cache-to-refresh-data-in-android-app

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
            App app = new App();
            app.greeting();
        } catch (Exception e) { 
            e.printStackTrace(System.out);
        }
    }

    public void greeting() throws Exception {
        String pathToServiceAccountKey = "/Users/menggen/code/sandbox/firebase-demo/ServiceAccountKey.json";
        FileInputStream serviceAccount = new FileInputStream(pathToServiceAccountKey);
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://menggen-demo.firebaseio.com")
            .build();

        FirebaseApp.initializeApp(options);

        DatabaseReference livesRef = FirebaseDatabase.getInstance().getReference().child("lives");
        System.out.println("livesRef: " + livesRef.getKey());

        livesRef.addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Get map of users in datasnapshot
                    processStreamId((Map<String, Object>) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println(databaseError.getMessage());
                }
            }
        );
    }

    private void processStreamId(Map<String,Object> streamId) {
        System.out.println("I am here");

        for (Map.Entry<String, Object> entry : streamId.entrySet()){
            System.out.println("entry key :" + entry.getKey());
            System.out.println("entry value :" + entry.getValue().toString());
        }
    }

}
