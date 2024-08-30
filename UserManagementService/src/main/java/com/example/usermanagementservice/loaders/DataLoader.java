package com.example.usermanagementservice.loaders;

import com.example.usermanagementservice.models.EmailTemplate;
import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.repositories.EmailRepository;
import com.example.usermanagementservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public DataLoader(EmailRepository emailRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        createDefaultAdminUser();
        ensureEmailTemplatesExist();
    }

    private void createDefaultAdminUser() {
        // Define the authorities for the default admin user
        List<String> authorities = List.of("ROLE_ADMIN", "ROLE_USER");
        String defaultAdminPassword = passwordEncoder.encode("Ats@1234");

        // Define the default admin user
        User defaultAdminUser = new User(
                0L,
                "admin@atsapp.com",
                "Admin",
                authorities,
                // this password is Ats@1234
                defaultAdminPassword,
                "30104210207165",
                "Cairo",
                "Egypt",
                "01208056263",
                true,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Check if a user with the given email exists, if not, save the user
        userRepository.findByEmail(defaultAdminUser.getEmail()).ifPresentOrElse(
                user -> System.out.println("Default Admin already exists."),
                () -> {
                    userRepository.save(defaultAdminUser);
                    System.out.println("Default admin user created.");
                }
        );
    }


    private void ensureEmailTemplatesExist() {
        // Check for and create email templates as needed
        createTemplateIfAbsent("verification_template", this::createEmailTemplates);
        createTemplateIfAbsent("email-verification-success", this::createVerificationSuccessTemplate);
        createTemplateIfAbsent("password_reset_template", this::createPasswordResetTemplate);
    }


    private void createTemplateIfAbsent(String templateName, Runnable createTemplateAction) {
        emailRepository.findByName(templateName).ifPresentOrElse(
                template -> {
                    // Template exists, do nothing
                },
                createTemplateAction
        );
    }

    private void createPasswordResetTemplate() {
        emailRepository.save(new EmailTemplate("password_reset_template", "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Password Reset</title>\n" +
                "    <style>\n" +
                "        .button:hover {background-color: #0056b3; }\n" +
                "    </style>\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #93c2f61a;  margin: 0; padding: 0;\">\n" +
                "<table  width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <table width=\"600\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"background-color: #fff; border-radius: 8px; box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);margin:30px;padding:20px\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        \n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        <p>Hey #USERNAME !</p>\n" +
                "                        <h2 style=\"font-weight: 600; margin:0; padding:0; font-size:26px;\">Password Reset</h2>\n" +
                "                        <p style=\"font-size:15px;\">You've requested to reset your password. Please use the following code to reset your password:</p>\n" +
                "                        <p style=\"font-size:18px; font-weight:bold;\">#RESET_CODE</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        <p style=\"font-size:12px; color:gray;\">If you did not request this password reset, you can ignore this email.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>"));
    }

    private void createVerificationSuccessTemplate() {


        emailRepository.save(new EmailTemplate("email-verification-success", "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Verified Successfully</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            text-align: center;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .container img {\n" +
                "            width: 100px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .container h1 {\n" +
                "            color: #4CAF50;\n" +
                "            font-size: 24px;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "        .container p {\n" +
                "            font-size: 18px;\n" +
                "            color: #333;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .container button {\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #fff;\n" +
                "            border: none;\n" +
                "            padding: 10px 20px;\n" +
                "            border-radius: 5px;\n" +
                "            font-size: 16px;\n" +
                "            cursor: pointer;\n" +
                "            transition: background-color 0.3s;\n" +
                "        }\n" +
                "        .container button:hover {\n" +
                "            background-color: #45a049;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        \n" +
                "        <h1>Email Verified Successfully!</h1>\n" +
                "        <p>Thank you for verifying your email address.</p>\n" +
                "\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>.ftl"));
    }

    private void createEmailTemplates() {

        // Create some users
        emailRepository.save(new EmailTemplate("verification_template", "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Confirmation</title>\n" +
                "    <style>\n" +
                "        .button:hover {background-color: #0056b3; }\n" +
                "    </style>\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #93c2f61a;  margin: 0; padding: 0;\">\n" +
                "<table  width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <table width=\"600\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"background-color: #fff; border-radius: 8px; box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);margin:30px;padding:20px\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        <img src=\"\" alt=\"\" width=\"100\" style=\"border-radius: 20px\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        <p>Hey #USERNAME !</p>\n" +
                "                        <h2 style=\"font-weight: 600; margin:0; padding:0; font-size:26px;\">Welcome To AtsApp</h2>\n" +
                "                        <p style=\"font-size:15px;\">Thank you for using atsapp, before we get started, we'll need to verify your email.</p>\n" +
                "                        <a href=\"#ACTIONLINK\" style=\"display: inline-block; padding: 10px 20px; background-color: #0077f9; color: #fff; text-decoration: none; border-radius: 5px; font-size:15px; transition: background-color 0.3s ease;\" class=\"button\">VERIFY EMAIL</a>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                        <p style=\"font-size:12px; color:gray;\">If you did not request this confirmation, you can ignore this email.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>"));
    }
}