(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service-and-register-session
      "http://toho.magcruise.org/world/BackendAPIService"
      "admin"
      "http://phoenix.toho.magcruise.org:8080/MAGCruiseBroker/json/GameProcessService"
      "http://phoenix.toho.magcruise.org:8080/MAGCruiseBroker/json/GameInteractionService"
      "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker/json/GameProcessService"
      "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker/json/GameInteractionService")
    (def:ui-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")))

