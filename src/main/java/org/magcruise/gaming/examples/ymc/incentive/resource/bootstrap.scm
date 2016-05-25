(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:addDefBootstrap
     (def:loader "org.magcruise.gaming.examples.ymc.incentive.resource.YmcGameResourceLoader"
        (def:src "game-definition.scm"))))
