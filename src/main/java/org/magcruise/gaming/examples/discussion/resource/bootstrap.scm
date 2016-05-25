(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
  (builder:addDefBootstrap
   (def:loader "org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader"
        (def:src "game-definition.scm"))))

