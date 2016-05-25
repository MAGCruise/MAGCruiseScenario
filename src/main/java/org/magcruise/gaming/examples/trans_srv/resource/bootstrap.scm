(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:addDefBootstrap
     (def:loader "org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader"
        (def:src "game-definition.scm"))))

