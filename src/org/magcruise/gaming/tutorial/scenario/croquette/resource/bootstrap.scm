(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.scenario.croquette.resource.ResourceLoader"
        (def:src "game-definition.scm"))))
