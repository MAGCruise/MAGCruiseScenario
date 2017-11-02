(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service-and-register-session
      "http://webui.magcruise.org/world/BackendAPIService"
      "admin"
      "http://proxy.phoenix.toho.magcruise.org/magcruise-broker")
    (def:ui-service
      "http://localhost:8080/magcruise-broker/json/GameInteractionService")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/magcruise-broker/json/GameInteractionService")))

