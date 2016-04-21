(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:addDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.trans_srv.resource.TranslationServiceGameResourceLoader"
        (def:src "game-definition.scm"))))

