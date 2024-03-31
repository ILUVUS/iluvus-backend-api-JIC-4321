package iluvus.backend.api.service;

import iluvus.backend.api.resources.NotificationType;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.CommunityRepository;
import iluvus.backend.api.repository.InterestRepository;
import iluvus.backend.api.repository.PostRepository;
import iluvus.backend.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static UserRepository userRepository;

    public static void init(UserRepository userRepository) {
        NotificationService.userRepository = userRepository;
    }

    public static void addNotification(String senderId, String receiverId, NotificationType type, String message, String dateTime) {
        try {
            boolean notificationValid = checkNotification(senderId, receiverId, type, dateTime, message);
            User receiver = userRepository.findById(receiverId).orElse(null);
            if (receiver != null && notificationValid) {
                receiver.createNotification(senderId, type, message, dateTime);
                userRepository.save(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkNotification(String senderId, String receiverId, NotificationType type, String message, String dateTime) {
        if (senderId == null) {
            return false;
        }
        if (receiverId == null) {
            return false;
        }
        if (type == null) {
            return false;
        }
        if (message == null) {
            return false;
        }
        if (dateTime == null) {
            return false;
        }
        return true;
    }


}
