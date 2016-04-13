(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     ;;(def:cp "https://www.dropbox.com/s/jjmu7cdr4w7k8xu/orangesignal-csv-2.2.1.jar?dl=1")
     (def:loader "org.magcruise.gaming.tutorial.ele.resource.EleGameResourceLoader"
        (def:src "game-definition.scm"))))
