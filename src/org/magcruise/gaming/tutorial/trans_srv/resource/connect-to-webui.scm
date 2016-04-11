(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service
      "http://toho.magcruise.org/world/BackendAPIService"
      "admin"
      "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker/json/GameProcessService"
      "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker/json/GameInteractionService")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")))

