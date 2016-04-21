(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
  (builder:addDefBootstrap
   (def:loader "org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader"
        (def:src "game-definition.scm")
        (def:src "def-players.scm"))))

