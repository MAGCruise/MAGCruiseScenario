(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
  (builder:setDefBootstrap
   (def:loader "org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader"
        (def:src "game-definition.scm")
        (def:src "def-players.scm"))))

