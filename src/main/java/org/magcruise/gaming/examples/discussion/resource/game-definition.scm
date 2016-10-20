(define-alias DiscussionRoom org.magcruise.gaming.examples.discussion.actor.DiscussionRoom)

(define (def:setup-game-builder builder ::GameBuilder)
    (builder:addDefContext (def:context DiscussionRoom))

  (setup-players builder)

  (builder:addDefRounds
    (def:round repeat: 2
      (def:stage type: 'parallel name: 'discussion-stage
        (def:scenario-task 'JapaneseDiscussant 'talker-scenario)
        (def:scenario-task 'ChineseDiscussant 'talker-scenario)
        (def:task 'start-stage))
      (def:stage
         (def:task 'show-msg)))))

(define (show-msg ctx ::DiscussionRoom)
  (ctx:showMessageToAll (to-string "roundnum. " ctx:roundnum " is finished.")))

(define (start-stage ctx ::DiscussionRoom)
  (ctx:sendStart))

(define (talker-scenario)
  (def:player-scenario 'talker-scenario
    (def:scene 'negotiation-scene
      (def:behavior 'isStartNegotiation 'negotiation 'negotiation-scene)
      (def:behavior 'isFinish '!change-scene 'exit)
      (def:default-behavior 'negotiation 'negotiation-scene))))

(define (!change-scene self ::Player ctx ::DiscussionRoom event ::GameEvent)
    #t)

