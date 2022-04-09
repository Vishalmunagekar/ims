package com.app.ims.service;

import com.app.ims.model.RefreshToken;
import com.app.ims.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenService.class);
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        RefreshToken token = refreshTokenRepository.save(refreshToken);
        LOGGER.debug("generateRefreshToken method RefreshToken : {}", refreshToken.toString());
        return token;
    }

    public void validateRefreshToken(String refreshToken){
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new EntityNotFoundException("Invalid Refresh Token!"));
        LOGGER.debug("validateRefreshToken method validated refreshToken : {}", refreshToken);
    }

    public void deleteRefreshToken(String refreshToken){
        refreshTokenRepository.deleteByToken(refreshToken);
        LOGGER.debug("deleteRefreshToken method deleted refreshToken : {}", refreshToken);
    }

}
