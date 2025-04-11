import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.faiza1.intent.NotificationAdapter;
import com.faiza1.intent.R;
import com.faiza1.intent.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView tvNotification = view.findViewById(R.id.tv_notification);
        tvNotification.setLayoutManager(new LinearLayoutManager(getContext())); // FIXED

        List<Notification> notificationList = new ArrayList<>();

        Notification notification = new Notification();
        notification.title = "Dangerous";
        notification.body = "I am not safe!";
        notification.date = "10/02/2025 09:43 PM";

        notificationList.add(notification);

        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        tvNotification.setAdapter(adapter);

        ImageView btnDelete = view.findViewById(R.id.tv_delete);

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Notification")
                    .setMessage("Are you sure you want to delete this notification?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Optional: Delete from Firebase
                        // FirebaseDatabase.getInstance().getReference("Notifications")
                        //     .child(FirebaseAuth.getInstance().getUid()).setValue(null);
                        Toast.makeText(getContext(), "Notification Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return view;
    }
}
