package Main.Config.WebSocket;

import Main.Config.Security.UserDetailConfig;
import Main.Config.Security.UserDetailServiceConfig;
import Main.Utility.jwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.Jar;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    private final jwtUtil jwtUtility;
    private final UserDetailServiceConfig userDetailServiceConfig;;

    // Listen for WebSocket connection events, called when a user connects
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Wrap the event message to access STOMP headers(session that hold connection info)
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract the Authorization header
        String authHeader = headerAccessor.getFirstNativeHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String accessSecretKey = jwtUtility.getAccessSecretKey();
            String username = jwtUtility.extractUsername(token,accessSecretKey);

            // Validate the token and retrieve user details
            if(username != null ) {
                UserDetailConfig user = new
                        UserDetailConfig(userDetailServiceConfig.loadUserByUsername(username));
                boolean validToken = jwtUtility.validateToken(token, user, accessSecretKey);

                // If the token is invalid, log a warning and return
                if (!validToken) {
                    log.warn("Invalid JWT token for user: " + username);
                    return;
                }

                log.info("User Connected : " + username);
                messagingTemplate.convertAndSend("/topic/user_state/" + username,"online");
                headerAccessor.getSessionAttributes().put("username", username); // Store username in session attributes
            }else{ // username is null
                log.warn("Invalid JWT token, unable to extract username.");
            }
        }
    }

    // Listen for WebSocket disconnection events, called when a user disconnects
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);
            messagingTemplate.convertAndSend("/topic/user_state/" + username, "offline");
        }else{
            log.warn("Username not found in session attributes during disconnect.");
        }
    }
}
