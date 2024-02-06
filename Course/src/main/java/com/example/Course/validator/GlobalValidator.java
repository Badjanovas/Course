package com.example.Course.validator;

import com.example.Course.exeption.NotValidIdException;
import com.example.Course.exeption.SameInformationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GlobalValidator {
    public void exceptionNotValidId(final Long id) throws NotValidIdException {
        if (id <= 0) {
            log.error("Id number can't be less than 1!");
            throw new NotValidIdException("Id number can't be less than 1!");
        }
    }

    public void exceptionSameInformationProvided() throws SameInformationException {
        log.info("Same information provided.");
        throw new SameInformationException("Same information provided.");
    }
}
