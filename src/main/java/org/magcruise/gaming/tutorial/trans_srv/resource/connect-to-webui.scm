(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service-and-register-session
      "http://toho.magcruise.org/world/BackendAPIService"
      "admin"
      "http://robin.toho.magcruise.org:8080/MAGCruiseBroker/json/GameProcessService"
      "http://robin.toho.magcruise.org:8080/MAGCruiseBroker/json/GameInteractionService"
      "http://proxy.robin.toho.magcruise.org/MAGCruiseBroker/json/GameProcessService"
      "http://proxy.robin.toho.magcruise.org/MAGCruiseBroker/json/GameInteractionService")
    (def:ui-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")))

