(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
   (def:ui
     "http://localhost:8080/MAGCruiseBroker/json/MAGCruiseBrokerService"
     "nkjm"
     "http://localhost:8080/MAGCruiseBroker/json/MAGCruiseBrokerService")
   (def:request-to-game-executor-publisher
     "http://localhost:8080/MAGCruiseBroker/json/MAGCruiseBrokerService")))
