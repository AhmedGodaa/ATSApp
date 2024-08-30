package com.example.usermanagementservice.services.user;

import com.example.usermanagementservice.common.Constants;
import com.example.usermanagementservice.models.PasswordResetToken;
import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.models.VerificationToken;
import com.example.usermanagementservice.models.dto.request.CreateUserRequest;
import com.example.usermanagementservice.models.dto.request.ResetPasswordRequest;
import com.example.usermanagementservice.models.dto.request.UserSignInRequest;
import com.example.usermanagementservice.models.dto.response.MessageResponse;
import com.example.usermanagementservice.models.dto.response.RefreshTokenResponse;
import com.example.usermanagementservice.models.dto.response.StringMessageResponse;
import com.example.usermanagementservice.models.dto.response.UserSignInResponse;
import com.example.usermanagementservice.repositories.EmailRepository;
import com.example.usermanagementservice.repositories.PasswordResetTokenRepository;
import com.example.usermanagementservice.repositories.UserRepository;
import com.example.usermanagementservice.repositories.VerificationTokenRepository;
import com.example.usermanagementservice.services.vertification.EmailVerificationService;
import com.example.usermanagementservice.userInstance.UserDetailsImpl;
import com.example.usermanagementservice.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    VerificationTokenRepository tokenRepository;
    EmailVerificationService emailVerificationService;
    EmailRepository emailRepository;
    PasswordResetTokenRepository passwordResetTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom(); // For generating random numbers
    final UserAuthenticator userAuthenticator;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, VerificationTokenRepository tokenRepository, EmailVerificationService emailVerificationService, EmailRepository emailRepository, PasswordResetTokenRepository passwordResetTokenRepository, UserAuthenticator userAuthenticator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.tokenRepository = tokenRepository;
        this.emailVerificationService = emailVerificationService;
        this.emailRepository = emailRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userAuthenticator = userAuthenticator;
    }


    //    Register User


    public User createUser(CreateUserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setNationID(userRequest.getNationID());
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail().toLowerCase());
        }
        user.setCity("City");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setAuthorities(
                new ArrayList<>(List.of(Constants.USER_ROLE))

        );
        return userRepository.save(user);

    }


    public StringMessageResponse signup(CreateUserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail().toLowerCase()).isPresent()) {
            throw new RuntimeException("Email Already In Use");
        } else {
            User user = createUser(userRequest);
            emailVerificationService.sendEmailVerificationLink(user.getEmail(), user.getFullName());
            return new StringMessageResponse("User Signup Successfully, Please Check Your Mail For Verification");
        }
    }

    @Transactional
    public MessageResponse<UserSignInResponse> signIn(UserSignInRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
            logger.info(authentication.toString());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new MessageResponse<>("User SignIn Successfully", new UserSignInResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                jwt,
                refreshToken));

    }

    // Method to refresh tokens
    public MessageResponse<RefreshTokenResponse> refreshToken(String refreshToken) {
        jwtUtils.validateRefreshToken(refreshToken);
        String email = jwtUtils.getEmailFromRefreshToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));


        String newJwt = jwtUtils.generateJwtToken(user.getEmail());
        String newRefreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return new MessageResponse<>("Token Refreshed Successfully",
                new RefreshTokenResponse(
                        newJwt,
                        newRefreshToken));


    }


    // Method to initiate password reset
    public void initiatePasswordReset(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String oobCode = String.format("%06d", secureRandom.nextInt(1000000));
        PasswordResetToken resetToken = new PasswordResetToken(oobCode, email, LocalDateTime.now().plusHours(1));
        passwordResetTokenRepository.save(resetToken);
        emailVerificationService.sendResetPasswordVerificationLink(email, oobCode, user.getFullName());
    }


    // Step 2: Verify OOB code and reset password
    public void resetPasswordWithOobCode(ResetPasswordRequest resetPasswordRequest) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(resetPasswordRequest.getOobCode()).orElseThrow(() -> new RuntimeException("Invalid OOB code"));
        if (resetToken.isExpired() || !resetToken.getUserEmail().equals(resetPasswordRequest.getEmail())) {
            throw new RuntimeException("Invalid or expired OOB code.");
        }
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
    }

    public MessageResponse<List<User>> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new MessageResponse<>("Get All Users Successfully", userRepository.findAll(pageable).getContent());
    }


    public String verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        if (verificationToken.isExpired()) {
            throw new RuntimeException("Verification token has expired");
        }
        User user = userRepository.findByEmail(verificationToken.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmailVerified(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return emailRepository.findByName("email-verification-success").isPresent() ? emailRepository.findByName("email-verification-success").get().getContent() : "Email Verification Success";

    }

    public StringMessageResponse makeUserAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getAuthorities().contains(Constants.ADMIN_ROLE)) {
            throw new RuntimeException("User is already an admin");
        }
        user.getAuthorities().add(Constants.ADMIN_ROLE);
        userRepository.save(user);
        return new StringMessageResponse("User is now an admin");
    }

    public StringMessageResponse removeUserAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getAuthorities().contains(Constants.ADMIN_ROLE)) {
            throw new RuntimeException("User is not an admin");
        }
        user.getAuthorities().remove(Constants.ADMIN_ROLE);
        userRepository.save(user);
        return new StringMessageResponse("User is no longer an admin");
    }

    public StringMessageResponse deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return new StringMessageResponse("User deleted successfully");
    }

    public MessageResponse<User> getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new MessageResponse<>("User found", user);

    }

    public UserInformation getMyInfo() {
        User user = userAuthenticator.authenticate();

        return new UserInformation(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getAuthorities(),
                user.getNationID(),
                user.getAddress(),
                user.getCity(),
                user.getPhone(),
                user.isEmailVerified(),
                user.isPhoneVerified()
        );
    }
}
