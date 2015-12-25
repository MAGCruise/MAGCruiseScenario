(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
      (def:loader "org.magcruise.gaming.tutorial.scenario.trans.TranslationGameLoader"
        ;;(def:src "system-properties.scm")
        (def:src "game-definition.scm"))))
