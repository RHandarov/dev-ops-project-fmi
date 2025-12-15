package fmi.sports.tournament.organizer.backend.controllers;

import fmi.sports.tournament.organizer.backend.dtos.NewNotificationDTO;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.responses.NotificationResponse;
import fmi.sports.tournament.organizer.backend.responses.ResponseResult;
import fmi.sports.tournament.organizer.backend.services.JWTService;
import fmi.sports.tournament.organizer.backend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final JWTService jwtService;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(JWTService jwtService,
                                  NotificationService notificationService) {
        this.jwtService = jwtService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getLoggedUserId(authorizationHeader);
        return notificationService.getAllNotificationsByUserId(userId)
                .stream()
                .map(notificationDTO -> NotificationResponse
                        .fromDTO(notificationDTO)
                        .responseResult(ResponseResult.SUCCESSFULLY_FOUND)
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/new")
    public List<NotificationResponse> getAllUnreadNotifications(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getLoggedUserId(authorizationHeader);
        return notificationService.getAllUnreadNotificationsByUserId(userId)
                .stream()
                .map(notificationDTO -> NotificationResponse
                        .fromDTO(notificationDTO)
                        .responseResult(ResponseResult.SUCCESSFULLY_FOUND)
                        .build())
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public void deleteAllNotifications(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getLoggedUserId(authorizationHeader);
        notificationService.deleteAllNotificationsByUserId(userId);
    }

    @PostMapping
    public void sendNotification(@RequestBody NewNotificationDTO newNotificationDTO) {
        notificationService.sendNotification(newNotificationDTO);
    }

    private Long getLoggedUserId(String authorizationHeader) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;

        User user = jwtService.getUser(token);
        return user.getId();
    }
}
