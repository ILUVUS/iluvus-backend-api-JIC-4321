package iluvus.backend.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iluvus.backend.api.model.User;
import iluvus.backend.api.repository.UserRepository;

@Service
public class UserDataCheck {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> newUserCheck(Map<String, String> data,
            UserRepository userRepository) {

        this.userRepository = userRepository;

        Map<String, String> result = new HashMap<>();
        ArrayList<String> errorList = new ArrayList<>();

        if (!checkName(data.get("fname"))) {
            errorList.add("First Name");
        }
        if (!checkName(data.get("lname"))) {
            errorList.add("Last Name");
        }
        if (!checkDOB(data.get("dob"))) {
            errorList.add("Date of Birth");
        }
        if (isUserExists(data.get("username"))) {
            errorList.add("Username");
        }
        if (!checkPassword(data.get("password"))) {
            errorList.add("Password");
        }

        if (data.get("proEmail").length() == 0) {
            if (!checkEmail(data.get("email"))) {
                errorList.add("Email");
            }
        }

        String errorString = String.join("\n ", errorList);
        result.put("error", errorString);

        return result;
    }

    public boolean checkName(String name) {
        if (name.strip().length() < 2) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkDOB(String dob) {

        // check if dob in yyyy-MM-dd
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(dob.strip());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isUserExists(String username) {
        try {
            User users = this.userRepository.findUserbyUsername(username.strip());
            if (users != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmail(String email) {
        EmailValidator emailvalidator = EmailValidator.getInstance();
        if (emailvalidator.isValid(email.strip())) {
            return true;
        } else {
            return false;
        }
    }

}
