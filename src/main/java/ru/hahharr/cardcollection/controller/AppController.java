package ru.hahharr.cardcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.repository.services.UserCoinStateRepoService;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetails;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserCoinStateRepoService userCoinStateRepoService;

    @GetMapping("/userState")
    public ResponseEntity<UserCoinState> userState(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userCoinStateRepoService.getById(userDetails.getUser().getId());
    }

    @PutMapping("/actions/get_free_coins")
    public ResponseEntity<HttpStatus> getFreeCoins(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userCoinStateRepoService.addFreeCoins(userDetails.getUser().getId());
    }
}
