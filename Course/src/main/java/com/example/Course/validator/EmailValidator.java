package com.example.Course.validator;

import com.example.Course.exeption.IncorrectEmailFormatException;
import com.example.Course.exeption.NoPdfFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

@Service
@Slf4j
public class EmailValidator {
    public void exceptionNoPdfFile(final Path attachmenPath) throws NoPdfFileException, IOException {
        if (!Files.exists(attachmenPath) || Files.size(attachmenPath) == 0){
            log.error("No PDF file was found or the file is empty.");
            throw new NoPdfFileException("No PDF file was found or the file is empty.");
        }
    }

    public void exceptionIncorrectEmailFormat(final String email) throws IncorrectEmailFormatException {
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final Pattern emailPattern = Pattern.compile(emailRegex);

        if (email == null || !emailPattern.matcher(email).matches()){
            log.error("Invalid email address: " + email);
            throw new IncorrectEmailFormatException("Invalid email address: " + email);
        }
    }
}
