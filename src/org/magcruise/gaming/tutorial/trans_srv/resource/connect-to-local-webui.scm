(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService"
      "admin")
    (def:game-interaction-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")
    (def:fetch-request-to-game-executor #t)))
